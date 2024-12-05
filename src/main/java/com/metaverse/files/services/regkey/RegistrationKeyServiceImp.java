package com.metaverse.files.services.regkey;

import java.util.List;
import java.util.Optional;

import com.metaverse.files.converters.regkey.RegistrationKeyConverter;
import com.metaverse.files.models.RoleModel;
import com.metaverse.files.repositories.RegistrationKeyRepository;
import com.metaverse.files.repositories.RoleRepository;
import com.metaverse.files.ro.regkey.RegistrationKeyRO;
import com.metaverse.files.security.models.RegistrationKeyModel;
import com.metaverse.files.security.models.SecurityRoleModel;
import com.metaverse.files.security.repositories.SecurityRoleRepository;
import com.metaverse.files.security.types.UserServerRole;
import com.metaverse.files.utils.StringUtils;
import com.metaverse.files.utils.exceptions.InvalidRequestStateException;
import com.metaverse.files.utils.exceptions.UselessOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса ключей регистрации.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Service
@Transactional(readOnly = true)
public class RegistrationKeyServiceImp implements RegistrationKeyService {

    @Autowired
    private RegistrationKeyRepository regKeyRepository;
    @Autowired
    private SecurityRoleRepository securityRoleRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private RegistrationKeyConverter regKeyConverter;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<RegistrationKeyRO> getAll() {
        List<RegistrationKeyModel> keysFromDB = regKeyRepository.findAll();
        return regKeyConverter.to(keysFromDB);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void create(RegistrationKeyRO regKey) {
        ensureKey(regKey);

        SecurityRoleModel securityRoleFromDB = getSecurityRoleFromDB(regKey);
        RoleModel roleFromDB = getRoleFromDB(regKey);

        saveKey(regKey, securityRoleFromDB, roleFromDB);
    }


    private void ensureKey(RegistrationKeyRO regKey) {
        if (StringUtils.isEmpty(regKey.getKey())) {
            throw new InvalidRequestStateException("Key is empty");
        }

        Optional<RegistrationKeyModel> keyFromDB = regKeyRepository.findByKey(regKey.getKey());
        if (keyFromDB.isPresent()) {
            throw new UselessOperationException("The key already exists");
        }
    }

    private SecurityRoleModel getSecurityRoleFromDB(RegistrationKeyRO regKey) {
        try {
            UserServerRole role = UserServerRole.valueOf(regKey.getSecurityRole().getName());
            Optional<SecurityRoleModel> securityRoleFromDB = securityRoleRepository.findByRole(role);
            if (securityRoleFromDB.isEmpty()) {
                throw new InvalidRequestStateException("Invalid security role name");
            }

            return securityRoleFromDB.get();
        } catch (IllegalArgumentException e) {
            throw new InvalidRequestStateException("Invalid security role name");
        }
    }

    private RoleModel getRoleFromDB(RegistrationKeyRO regKey) {

        Optional<RoleModel> roleFromDB = roleRepository.findByName(regKey.getRole().getName());
        if (roleFromDB.isEmpty()) {
            throw new InvalidRequestStateException("Invalid application role name");
        }

        return roleFromDB.get();
    }

    private void saveKey(RegistrationKeyRO regKey, SecurityRoleModel roleFromDB, RoleModel applicationRole) {
        RegistrationKeyModel keyModel = new RegistrationKeyModel();
        keyModel.setKey(regKey.getKey());
        keyModel.setOrganization(regKey.getOrganization());
        keyModel.setDateFrom(regKey.getDateFrom());
        keyModel.setDateTo(regKey.getDateTo());
        keyModel.setServerRole(roleFromDB);
        keyModel.setApplicationRole(applicationRole);

        regKeyRepository.save(keyModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(int id) {
        RegistrationKeyModel keyFromDB = getKeyFromDB(id);

        regKeyRepository.delete(keyFromDB);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(List<String> keys) {
        regKeyRepository.deleteAllByKeyIn(keys);
    }

    private RegistrationKeyModel getKeyFromDB(int id) {
        Optional<RegistrationKeyModel> keyFromDB = regKeyRepository.findById(id);
        if (keyFromDB.isEmpty()) {
            String message = String.format("The key with id [%d] does not exist", id);
            throw new UselessOperationException(message);
        }

        return keyFromDB.get();
    }
}
