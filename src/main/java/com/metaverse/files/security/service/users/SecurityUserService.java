package com.metaverse.files.security.service.users;

import java.util.Optional;

import com.metaverse.files.security.models.UserModel;
import com.metaverse.files.security.repositories.UsersRepository;
import com.metaverse.files.security.types.SecurityUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис работы с пользователями spring security.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Service
@Transactional(readOnly = true)
public class SecurityUserService implements UserDetailsService {

    @Autowired
    private UsersRepository securityUserRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<UserModel> securityUserModel = securityUserRepository.findByLogin(login);
        if (securityUserModel.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }

        return new SecurityUserDetails(securityUserModel.get());
    }
}
