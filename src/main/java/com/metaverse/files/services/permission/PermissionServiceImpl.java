package com.metaverse.files.services.permission;


import java.util.List;

import com.metaverse.files.converters.role.PermissionConverter;
import com.metaverse.files.models.PermissionModel;
import com.metaverse.files.repositories.PermissionRepository;
import com.metaverse.files.ro.role.PermissionRO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса прав (разрешений).
 *
 * @author Mikhail.Kataranov
 * @since 24.11.2024
 */
@Service
@Transactional(readOnly = true)
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private PermissionConverter permissionConverter;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<PermissionRO> getAll() {
        List<PermissionModel> permissions = permissionRepository.findAll();
        return permissionConverter.to(permissions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void update(List<PermissionRO> permissions) {
        List<PermissionModel> permissionModels = permissionConverter.from(permissions);

        permissionRepository.saveAll(permissionModels);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(List<String> permissions) {
        permissionRepository.deleteAllByNameIn(permissions);
    }
}
