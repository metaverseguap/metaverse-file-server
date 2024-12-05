package com.metaverse.files.rest;

import java.util.List;

import com.metaverse.files.ro.regkey.RegistrationKeyRO;
import com.metaverse.files.ro.regkey.requests.CreateRegistrationKeyRequestRO;
import com.metaverse.files.ro.regkey.responses.RegistrationKeysResultRO;
import com.metaverse.files.ro.request.DeleteByNamesRequestRO;
import com.metaverse.files.ro.response.ResultDetailsRO;
import com.metaverse.files.services.regkey.RegistrationKeyService;
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
 * REST ключей регистрации.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@RestController
@Tag(name = "Сервис ключей регистрации", description = "Данный сервис используется для работы с ключами регистрации")
@RequestMapping(RegistrationKeyRest.PATH)
public class RegistrationKeyRest {

    public static final String PATH = "/api/registration-key";

    @Autowired
    private RegistrationKeyService regKeyService;

    @PostMapping("/create")
    @Operation(summary = "Создать ключ регистрации", description = "Позволяет создавать ключи регистрации")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> create(@RequestBody CreateRegistrationKeyRequestRO request) {
        regKeyService.create(request.getRegistrationKey());

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @GetMapping("/all")
    @Operation(summary = "Получить все ключи регистрации", description = "Позволяет получить все ключи регистрации")
    @ApiResponse(responseCode = "200", description = "Список всех ключей регистрации", content = @Content(schema = @Schema(implementation = RegistrationKeysResultRO.class)))
    public ResponseEntity<RegistrationKeysResultRO> getAll() {
        List<RegistrationKeyRO> regKeys = regKeyService.getAll();

        RegistrationKeysResultRO result = new RegistrationKeysResultRO();
        result.setKeys(regKeys);
        result.setSuccess(true);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить ключ регистрации", description = "Позволяет удалить ключ регистрации с указанным id")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> delete(@PathVariable("id") @Parameter(description = "Id удаляемого ключа", required = true) int id) {
        regKeyService.delete(id);

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @PostMapping("/delete-by-names")
    @Operation(summary = "Удалить ключи регистрации по имени", description = "Позволяет удалить несколько ключей регистрации имеющих указанные имена")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> deleteByNames(@RequestBody DeleteByNamesRequestRO deleteRequestRO) {
        regKeyService.delete(deleteRequestRO.getNames());

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
