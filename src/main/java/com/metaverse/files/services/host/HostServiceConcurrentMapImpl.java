package com.metaverse.files.services.host;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.metaverse.files.contexts.host.CreateHostContext;
import com.metaverse.files.models.SceneModel;
import com.metaverse.files.repositories.SceneRepository;
import com.metaverse.files.ro.host.HostAddressRO;
import com.metaverse.files.ro.host.HostRO;
import com.metaverse.files.security.models.UserModel;
import com.metaverse.files.security.repositories.UsersRepository;
import com.metaverse.files.security.utils.SecurityUtils;
import com.metaverse.files.services.user.UserStatusService;
import com.metaverse.files.utils.exceptions.DataNotFoundException;
import com.metaverse.files.utils.exceptions.UselessOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса хостов.
 *
 * Данная реализация сервиса использует ConcurrentHashMap.
 * В дальнейшем при количестве хостов более 100к или чтению более 1000к раз/с,
 * стоит написать реализацию сервиса, использующую Redis или Hazelcast
 *
 * @author Mikhail.Kataranov
 * @since 17.05.2025
 */
@Service
public class HostServiceConcurrentMapImpl implements HostsService {

    @Autowired
    private UserStatusService userStatusService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private SceneRepository sceneRepository;

    @Value("${client.default.port}")
    private int defaultPort = 7777;

    /**
     * {@literal ConcurrentHashMap<userLogin, HostRO>}
     */
    private final ConcurrentHashMap<String, HostRO> hostByLogin = new ConcurrentHashMap<>();
    /**
     * {@literal ConcurrentHashMap<sceneName, List<hostLogin>>}
     */
    private final ConcurrentHashMap<String, List<String>> hostLoginsByScene = new ConcurrentHashMap<>();
    /**
     * {@literal ConcurrentHashMap<IP, lastReservedPort>}
     */
    private final ConcurrentHashMap<String, Integer> reservedIpPorts = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<String, List<HostRO>> hostsGroupedByScene() {
        Set<String> activeUsers = userStatusService.activeUsersLogins();
        Map<String, List<HostRO>> hostsGroupedByScene = new HashMap<>();
        for (String sceneWithHosts : hostLoginsByScene.keySet()) {
            List<HostRO> hosts = getSceneActiveHosts(sceneWithHosts, activeUsers);
            hostsGroupedByScene.put(sceneWithHosts, hosts);
        }

        return hostsGroupedByScene;
    }

    private List<HostRO> getSceneActiveHosts(String sceneWithHosts, Set<String> activeUsers) {
        List<String> hostLogins = hostLoginsByScene.getOrDefault(sceneWithHosts, new ArrayList<>());
        List<HostRO> hosts = new ArrayList<>();
        for (int i = hostLogins.size() - 1; i >= 0; i--) {
            String login = hostLogins.get(i);
            if (activeUsers.contains(login)) {
                hosts.add(hostByLogin.get(login));
            } else {
                hostLogins.remove(i);
                hostByLogin.remove(login);
            }
        }
        hostLoginsByScene.put(sceneWithHosts, hostLogins);

        return hosts;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HostRO> hostsByScene(String sceneName) {
        Set<String> activeUsers = userStatusService.activeUsersLogins();
        return getSceneActiveHosts(sceneName, activeUsers);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public HostRO hostByLogin(String login) {
        Set<String> activeUsers = userStatusService.activeUsersLogins();
        if (activeUsers.contains(login)) {
            return hostByLogin.get(login);
        }

        removeHost(login);

        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HostAddressRO create(CreateHostContext ctx) {
        UserModel currentUser = getCurrentUser();
        String login = currentUser.getLogin();
        SceneModel sceneFromDB = getSceneFromDB(ctx);

        ensureHostNotExists(login);

        saveHost(ctx, sceneFromDB, currentUser);

        HostAddressRO result = new HostAddressRO();
        HostRO hostRO = hostByLogin.get(login);
        result.setHostIP(hostRO.getHostIP());
        result.setPort(hostRO.getPort());

        return result;
    }

    private UserModel getCurrentUser() {
        String login = SecurityUtils.getAuthenticatedUserLogin();

        return usersRepository.findByLogin(login).get();
    }

    private SceneModel getSceneFromDB(CreateHostContext ctx) {
        Optional<SceneModel> sceneFromDB = sceneRepository.findByName(ctx.getSceneName());
        if (sceneFromDB.isEmpty()) {
            throw new DataNotFoundException(String.format("Scene [%s] was not found", ctx.getSceneName()));
        }

        return sceneFromDB.get();
    }

    private void ensureHostNotExists(String hostLogin) {
        if (hostByLogin.containsKey(hostLogin)) {
            throw new UselessOperationException("This user is already a host");
        }
    }

    private void saveHost(CreateHostContext ctx, SceneModel sceneFromDB, UserModel userModel) {
        HostRO host = new HostRO();
        host.setLogin(userModel.getLogin());
        host.setName(userModel.getName());
        host.setSceneName(sceneFromDB.getName());
        host.setHostIP(ctx.getHostIP());
        List<String> hostsLogins = hostLoginsByScene.getOrDefault(host.getSceneName(), new ArrayList<>());
        int port = reservedIpPorts.getOrDefault(host.getHostIP(), defaultPort - 1) + 1;

        host.setPort(port);

        reservedIpPorts.put(host.getHostIP(), port);
        hostByLogin.put(host.getLogin(), host);
        hostsLogins.add(host.getLogin());
        hostLoginsByScene.put(host.getSceneName(), hostsLogins);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete() {
        UserModel userModel = getCurrentUser();
        String hostLogin = userModel.getLogin();

        removeHost(hostLogin);
    }

    private void removeHost(String hostLogin) {
        for (String sceneWithHosts : hostLoginsByScene.keySet()) {
            List<String> sceneLogins = hostLoginsByScene.get(sceneWithHosts);
            int originalSize = sceneLogins.size();
            sceneLogins.removeIf(login -> login.equals(hostLogin));
            if (originalSize != sceneLogins.size()) {
                hostLoginsByScene.put(sceneWithHosts, sceneLogins);
            }
        }

        hostByLogin.remove(hostLogin);
    }
}
