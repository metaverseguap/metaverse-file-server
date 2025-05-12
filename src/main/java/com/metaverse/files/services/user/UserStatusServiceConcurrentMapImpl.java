package com.metaverse.files.services.user;


import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import com.metaverse.files.security.utils.SecurityUtils;
import com.metaverse.files.utils.TimeUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Реализация сервиса статуса пользователей.
 *
 * Данная реализация сервиса использует ConcurrentHashMap.
 * В дальнейшем, стоит написать реализацию сервиса,
 * использующую Redis
 *
 * @author Mikhail.Kataranov
 * @since 12.05.2025
 */
@Service
public class UserStatusServiceConcurrentMapImpl implements UserStatusService {

    @Value("${application.user.status.expirationAfterSeconds}")
    private Integer statusExpirationAfterSeconds;

    private final ConcurrentHashMap<String, Long> onlineUsers = new ConcurrentHashMap<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getSecondsOfStatusExpiration() {
        return statusExpirationAfterSeconds;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUserStatus() {
        String authUserLogin = SecurityUtils.getAuthenticatedUserLogin();
        if (authUserLogin == null) {
            return;
        }

        onlineUsers.put(authUserLogin, TimeUtils.millisNow());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateUserStatus(String login) {
        onlineUsers.put(login, TimeUtils.millisNow());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<String> activeUsersLogins() {
        long now = TimeUtils.millisNow();
        onlineUsers.entrySet().removeIf(entry ->
                entry.getValue() + TimeUnit.SECONDS.toMillis(statusExpirationAfterSeconds) < now
        );

        return Set.copyOf(onlineUsers.keySet());
    }
}
