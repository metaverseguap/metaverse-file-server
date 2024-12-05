package com.metaverse.files.services.role;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.metaverse.files.converters.role.PermissionConverter;
import com.metaverse.files.converters.role.RoleConverter;
import com.metaverse.files.models.PermissionModel;
import com.metaverse.files.models.RoleModel;
import com.metaverse.files.repositories.PermissionRepository;
import com.metaverse.files.repositories.RoleRepository;
import com.metaverse.files.ro.role.PermissionRO;
import com.metaverse.files.ro.role.RoleRO;
import com.metaverse.files.security.models.UserModel;
import com.metaverse.files.security.repositories.UsersRepository;
import com.metaverse.files.utils.exceptions.UselessOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса ролей.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Service
@Transactional(readOnly = true)
public class RoleServiceImp implements RoleService {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private RoleConverter roleConverter;
    @Autowired
    private PermissionConverter permissionConverter;
    @Autowired
    private UsersRepository usersRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RoleRO> getAll() {
        List<RoleModel> rolesFromDB = roleRepository.findAll();
        return roleConverter.to(rolesFromDB);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoleRO getCurrentUserRole() {
        UserModel currentUser = getCurrentUser();
        RoleModel role = currentUser.getRole();

        return roleConverter.to(role);
    }

    private UserModel getCurrentUser() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String login = (String) securityContext.getAuthentication().getPrincipal();

        return usersRepository.findByLogin(login).get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(RoleRO roleRO) {
        RoleModel role = getRoleModel(roleRO);

        roleRepository.save(role);
    }

    private RoleModel getRoleModel(RoleRO roleRO) {
        RoleModel role = getRoleFromDB(roleRO);
        role = updatePermissions(role, roleRO);

        return role;
    }

    private RoleModel updatePermissions(RoleModel role, RoleRO roleRO) {
        if(role.getPermissions() == null) {
            role.addPermission();
        }
        role.getPermissions().clear();

        List<PermissionModel> existingPermissions = getExistingPermissions(roleRO);
        Set<PermissionModel> newPermissions = createNewPermissions(roleRO, existingPermissions);

        role.getPermissions().addAll(existingPermissions);
        role.getPermissions().addAll(newPermissions);

        return role;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(List<RoleRO> roles) {
        List<RoleModel> list = roles.stream()
                .map(this::getRoleModel)
                .toList();

        roleRepository.saveAll(list);
    }

    private RoleModel getRoleFromDB(RoleRO roleRO) {
        Optional<RoleModel> roleFromDB = roleRepository.findByName(roleRO.getName());
        if(roleFromDB.isPresent()) {
            return roleFromDB.get();
        }

        RoleModel newRoleModel = new RoleModel();
        newRoleModel.setName(roleRO.getName());
        newRoleModel.addPermission(permissionConverter.from(roleRO.getPermissions()).toArray(PermissionModel[]::new));

        return newRoleModel;
    }

    private List<PermissionModel> getExistingPermissions(RoleRO roleRO) {
        Set<String> permissionNames = getPermissionNames(roleRO);
        return permissionRepository.findAllByNameIn(permissionNames);
    }

    private static Set<String> getPermissionNames(RoleRO roleRO) {
        if(roleRO.getPermissions() == null) {
            return Collections.emptySet();
        }

        return roleRO.getPermissions()
                .stream()
                .map(PermissionRO::getName)
                .collect(Collectors.toSet());
    }

    private Set<PermissionModel> createNewPermissions(RoleRO roleRO, List<PermissionModel> existingPermission) {
        Set<String> existingPermissionsNames = getPermissionNames(existingPermission);
        if (roleRO.getPermissions() == null) {
            roleRO.setPermissions(new HashSet<>());
        }

        return roleRO.getPermissions().stream()
                .filter(p -> !existingPermissionsNames.contains(p.getName()))
                .map(p -> permissionConverter.from(p))
                .collect(Collectors.toSet());
    }

    private static Set<String> getPermissionNames(List<PermissionModel> existsPermissions) {
        return existsPermissions.stream()
                .map(PermissionModel::getName)
                .collect(Collectors.toSet());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(int id) {
        RoleModel roleFromDB = getKeyFromDB(id);
        roleRepository.delete(roleFromDB);
    }

    private RoleModel getKeyFromDB(int id) {
        Optional<RoleModel> roleFromDB = roleRepository.findById(id);
        if (roleFromDB.isEmpty()) {
            final String message = String.format("The role with id [%d] does not exist", id);
            throw new UselessOperationException(message);
        }

        return roleFromDB.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(List<String> roleNames) {
        roleRepository.deleteAllByNameIn(roleNames);
    }
}
