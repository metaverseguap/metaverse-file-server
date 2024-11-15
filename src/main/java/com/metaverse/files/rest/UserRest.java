package com.metaverse.files.rest;

import com.metaverse.files.ro.response.ResultDetailsRO;
import com.metaverse.files.ro.user.UserRO;
import com.metaverse.files.ro.user.responses.UserInfoResultRO;
import com.metaverse.files.ro.user.responses.UserNameResultRO;
import com.metaverse.files.services.user.UserService;
import com.metaverse.files.utils.ResponseUtils;
import com.metaverse.files.utils.exceptions.DataNotFoundException;
import com.metaverse.files.utils.exceptions.ExceptionCode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST пользователей.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@RestController
@Tag(name = "Сервис пользователей", description = "Данный сервис используется для работы с информацией о пользователях")
@RequestMapping(UserRest.PATH)
public class UserRest {

    public static final String PATH = "/api/user";

    @Autowired
    private UserService userService;

    @GetMapping("/username-by-login")
    @Operation(summary = "Получить имя пользователя по логину", description = "Позволяет получить отображаемое имя пользователя по его логину")
    @ApiResponse(responseCode = "200", description = "Имя пользователя", content = @Content(schema = @Schema(implementation = UserNameResultRO.class)))
    public ResponseEntity<UserNameResultRO> getUserNickNameByLogin(@RequestParam("login") @Parameter(description = "Login пользователя", required = true) String login) {
        String username = userService.getNameByLogin(login);

        UserNameResultRO result = new UserNameResultRO();
        result.setName(username);
        result.setSuccess(true);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/user-info-by-login")
    @Operation(summary = "Получить информация о пользователе по логину", description = "Позволяет получить всю информацию о пользователе по его логину")
    @ApiResponse(responseCode = "200", description = "Информация о пользователе", content = @Content(schema = @Schema(implementation = UserInfoResultRO.class)))
    public ResponseEntity<UserInfoResultRO> getUserInfoByLogin(@RequestParam("login") @Parameter(description = "Login пользователя", required = true) String login) {
        UserRO user = userService.getInfoByLogin(login);

        UserInfoResultRO userInfo = new UserInfoResultRO();
        userInfo.setUserInfo(user);
        userInfo.setSuccess(true);

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping("/me")
    @Operation(summary = "Получить информация об авторизованном пользователе", description = "Позволяет получить всю информацию об авторизованном в данный момент пользователе")
    @ApiResponse(responseCode = "200", description = "Информация о пользователе", content = @Content(schema = @Schema(implementation = UserInfoResultRO.class)))
    public ResponseEntity<UserInfoResultRO> getUserInfoByLogin() {
        UserRO user = userService.getCurrentUser();

        UserInfoResultRO userInfo = new UserInfoResultRO();
        userInfo.setUserInfo(user);
        userInfo.setSuccess(true);

        return ResponseEntity.ok(userInfo);
    }

    @ExceptionHandler
    private ResponseEntity<ResultDetailsRO> handleDataNotFoundException(DataNotFoundException exception) {
        ResultDetailsRO resultRO = ResponseUtils.errorResponse(exception, ExceptionCode.NOT_FOUND.getCode());
        return ResponseEntity.ok(resultRO);
    }
}
