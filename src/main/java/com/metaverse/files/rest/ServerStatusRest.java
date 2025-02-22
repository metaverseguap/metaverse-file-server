package com.metaverse.files.rest;


import com.metaverse.files.ro.response.ResultDetailsRO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST возвращающий различные статусы сервера.
 *
 * @author Mikhail.Kataranov
 * @since 22.02.2025
 */
@RestController
@Tag(name = "Сервис статусов сервера", description = "Данный сервис используется для получения статусов сервера")
@RequestMapping(ServerStatusRest.PATH)
public class ServerStatusRest {

    public static final String PATH = "/api/status";

    @GetMapping
    @Operation(summary = "Статус активности сервера",
            description = "Данный endpoint используется для проверки клиентом подключения к серверу. " +
                    "Если клиент смог достичь этого endpoint-а, значит сервер активен и доступен для клиента")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> ping() {
        return ResponseEntity.ok(ResultDetailsRO.success());
    }
}
