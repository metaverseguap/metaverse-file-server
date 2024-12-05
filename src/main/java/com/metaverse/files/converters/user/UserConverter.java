package com.metaverse.files.converters.user;

import com.metaverse.files.converters.Converter;
import com.metaverse.files.converters.role.RoleConverter;
import com.metaverse.files.ro.user.UserRO;
import com.metaverse.files.security.models.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Конвертер UserModel в UserRO.
 *
 * @author Mikhail.Kataranov
 * @since 02.11.2024
 */
@Component
public class UserConverter extends Converter<UserModel, UserRO> {

    @Autowired
    private RoleConverter roleConverter;

    /**
     * Конструктор.
     */
    public UserConverter() {
        super();
        this.from = null;
        this.to = this::convert;
    }

    private UserRO convert(final UserModel model) {
        UserRO ro = new UserRO();
        ro.setLogin(model.getLogin());
        ro.setRole(roleConverter.to(model.getRole()));
        ro.setName(model.getName());

        return ro;
    }
}
