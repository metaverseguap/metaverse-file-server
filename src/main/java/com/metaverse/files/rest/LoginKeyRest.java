package com.metaverse.files.rest;

import java.util.List;

import com.metaverse.files.ro.loginkey.LoginKeyRO;
import com.metaverse.files.ro.loginkey.requests.CreateLoginKeyRequestRO;
import com.metaverse.files.ro.loginkey.responses.LoginKeysResultRO;
import com.metaverse.files.ro.request.DeleteByNamesRequestRO;
import com.metaverse.files.ro.response.ResultDetailsRO;
import com.metaverse.files.services.loginkey.LoginKeyService;
import com.metaverse.files.utils.ResponseUtils;
import com.metaverse.files.utils.exceptions.ExceptionCode;
import com.metaverse.files.utils.exceptions.InvalidRequestStateException;
import com.metaverse.files.utils.exceptions.UselessOperationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST ключей авторизации.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@RestController
@Tag(name = "Сервис ключей авторизации", description = "Данный сервис используется для работы с ключами авторизации")
@RequestMapping(LoginKeyRest.PATH)
public class LoginKeyRest {

    public static final String PATH = "/api/login-key";

    @Autowired
    private LoginKeyService loginKeyService;

    @GetMapping("/all")
    @Operation(summary = "Получить все ключи авторизации", description = "Позволяет получить все ключи авторизации")
    @ApiResponse(responseCode = "200", description = "Список всех ключей авторизации", content = @Content(schema = @Schema(implementation = LoginKeysResultRO.class)))
    public ResponseEntity<LoginKeysResultRO> getAll() {
        List<LoginKeyRO> loginKeys = loginKeyService.getAll();

        LoginKeysResultRO result = new LoginKeysResultRO();
        result.setKeys(loginKeys);
        result.setSuccess(true);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/create")
    @Operation(summary = "Создать ключ авторизации", description = "Позволяет создавать ключи авторизации")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> create(@RequestBody CreateLoginKeyRequestRO createLoginKeyRequestRO) {
        loginKeyService.create(createLoginKeyRequestRO.getLoginKey());

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить ключ авторизации", description = "Позволяет удалить ключ авторизации с указанным id")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> delete(@PathVariable("id") @Parameter(description = "Id удаляемого ключа", required = true) int id) {
        loginKeyService.delete(id);

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @PostMapping("/delete-by-names")
    @Operation(summary = "Удалить ключи авторизации по имени", description = "Позволяет удалить несколько ключей авторизации имеющих указанные имена")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> deleteByNames(@RequestBody DeleteByNamesRequestRO deleteRequestRO) {
        loginKeyService.delete(deleteRequestRO.getNames());

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @ExceptionHandler
    private ResponseEntity<ResultDetailsRO> handleUselessOperationException(UselessOperationException exception) {
        ResultDetailsRO resultRO = ResponseUtils.exceptionWarningResponse(exception);
        return ResponseEntity.ok(resultRO);
    }

    @ExceptionHandler
    private ResponseEntity<ResultDetailsRO> handleInvalidRequestStateException(InvalidRequestStateException exception) {
        ResultDetailsRO resultRO = ResponseUtils.errorResponse(exception, ExceptionCode.BAD_REQUEST.getCode());
        return ResponseEntity.ok(resultRO);
    }
}
