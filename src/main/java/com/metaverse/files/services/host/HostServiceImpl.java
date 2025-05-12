package com.metaverse.files.services.host;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.metaverse.files.contexts.host.CreateHostContext;
import com.metaverse.files.converters.host.HostConverter;
import com.metaverse.files.models.HostModel;
import com.metaverse.files.models.SceneModel;
import com.metaverse.files.repositories.HostsRepository;
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
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса хостов.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Service
@Transactional(readOnly = true)
public class HostServiceImpl implements HostsService {

    @Autowired
    private UserStatusService userStatusService;
    @Autowired
    private HostsRepository hostsRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private SceneRepository sceneRepository;
    @Autowired
    private HostConverter hostConverter;

    @Value("${client.default.port}")
    private int defaultPort = 7777;

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public Map<String, List<HostRO>> hostsGroupedByScene() {
        Set<String> activeUsers = userStatusService.activeUsersLogins();
        List<SceneModel> scenes = sceneRepository.findAll();

        Map<String, List<HostRO>> hostsGroupedByScene = new HashMap<>();

        for (SceneModel scene : scenes) {
            List<HostRO> hosts = getActiveHosts(scene.getHosts(), activeUsers);
            hostsGroupedByScene.put(scene.getName(), hosts);
        }

        return hostsGroupedByScene;
    }

    private List<HostRO> getActiveHosts(List<HostModel> hosts, Set<String> activeUsers) {
        List<HostRO> result = new ArrayList<>();
        for (var host : hosts) {
            UserModel user = host.getUser().get(0);
            if (activeUsers.contains(user.getLogin())) {
                result.add(hostConverter.to(host));
            } else {
                hostsRepository.delete(host);
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public List<HostRO> hostsByScene(String sceneName) {
        Set<String> activeUsers = userStatusService.activeUsersLogins();
        List<HostModel> hosts = hostsRepository.findAllBySceneModelName(sceneName);
        return getActiveHosts(hosts, activeUsers);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Transactional
    @Override
    public HostRO hostByLogin(String login) {
        Optional<HostModel> host = hostsRepository.findByUserModelLogin(login);
        if (host.isEmpty()) {
            return null;
        }

        HostModel hostModel = host.get();
        UserModel user = hostModel.getUser().get(0);
        boolean isUserActive = userStatusService.activeUsersLogins().contains(user.getLogin());
        if (!isUserActive) {
            hostsRepository.delete(hostModel);
            return null;
        }

        return hostConverter.to(hostModel);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public HostAddressRO create(CreateHostContext ctx) {
        UserModel userModel = getCurrentUser();
        SceneModel sceneFromDB = getSceneFromDB(ctx);

        ensureHostNotExists(userModel);

        saveHost(ctx, sceneFromDB, userModel);

        HostAddressRO result = new HostAddressRO();
        result.setHostIP(ctx.getHostIP());

        List<HostModel> hosts = sceneFromDB.getHosts();
        int port = defaultPort + hosts.size();
        result.setPort(port);

        return result;
    }

    private SceneModel getSceneFromDB(CreateHostContext ctx) {
        Optional<SceneModel> sceneFromDB = sceneRepository.findByName(ctx.getSceneName());
        if (sceneFromDB.isEmpty()) {
            throw new DataNotFoundException(String.format("Scene [%s] was not found", ctx.getSceneName()));
        }

        return sceneFromDB.get();
    }

    private void ensureHostNotExists(UserModel userModel) {
        Optional<HostModel> currentHost = hostsRepository.findByUserModelLogin(userModel.getLogin());
        if (currentHost.isPresent()) {
            throw new UselessOperationException("This user is already a host");
        }
    }

    private void saveHost(CreateHostContext ctx, SceneModel sceneFromDB, UserModel userModel) {
        HostModel hostModel = new HostModel();
        hostModel.setHostIP(ctx.getHostIP());
        hostModel.setScene(sceneFromDB);
        hostModel.setUser(userModel);

        List<HostModel> hosts = sceneFromDB.getHosts();
        int port = defaultPort + hosts.size();
        hostModel.setPort(port);

        hostsRepository.save(hostModel);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete() {
        HostModel currentHost = getHostFromDB();

        hostsRepository.delete(currentHost);
    }

    private HostModel getHostFromDB() {
        UserModel userModel = getCurrentUser();
        Optional<HostModel> currentHost = hostsRepository.findByUserModelLogin(userModel.getLogin());
        if (currentHost.isEmpty()) {
            throw new UselessOperationException("This user is not the host");
        }

        return currentHost.get();
    }

    private UserModel getCurrentUser() {
        String login = SecurityUtils.getAuthenticatedUserLogin();

        return usersRepository.findByLogin(login).get();
    }
}
