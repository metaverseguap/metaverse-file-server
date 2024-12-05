package com.metaverse.files.rest;

import java.util.List;

import com.metaverse.files.ro.request.DeleteByNamesRequestRO;
import com.metaverse.files.ro.response.ResultDetailsRO;
import com.metaverse.files.ro.role.PermissionRO;
import com.metaverse.files.ro.role.RoleRO;
import com.metaverse.files.ro.role.SecurityRoleRO;
import com.metaverse.files.ro.role.requests.CreatePermissionsRequestRO;
import com.metaverse.files.ro.role.requests.CreateRoleRequestRO;
import com.metaverse.files.ro.role.requests.CreateRolesRequestRO;
import com.metaverse.files.ro.role.responses.PermissionsResultRO;
import com.metaverse.files.ro.role.responses.RoleInfoResultRO;
import com.metaverse.files.ro.role.responses.RolesResultRO;
import com.metaverse.files.ro.role.responses.SecurityRolesResultRO;
import com.metaverse.files.security.service.role.SecurityRoleService;
import com.metaverse.files.services.permission.PermissionService;
import com.metaverse.files.services.role.RoleService;
import com.metaverse.files.utils.ResponseUtils;
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
 * REST ролей.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@RestController
@Tag(name = "Сервис ролей", description = "Данный сервис используется для работы с ролями пользователей в приложении")
@RequestMapping(RoleRest.PATH)
public class RoleRest {

    public static final String PATH = "/api/role";

    @Autowired
    private RoleService roleService;
    @Autowired
    private SecurityRoleService securityRoleService;
    @Autowired
    private PermissionService permissionService;

    @GetMapping("/all")
    @Operation(summary = "Получить все роли", description = "Позволяет получить все роли пользователей в приложении")
    @ApiResponse(responseCode = "200", description = "Список всех ролей в приложении", content = @Content(schema = @Schema(implementation = RolesResultRO.class)))
    public ResponseEntity<RolesResultRO> getAll() {
        List<RoleRO> roles = roleService.getAll();

        RolesResultRO result = new RolesResultRO();
        result.setRoles(roles);
        result.setSuccess(true);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/server/all")
    @Operation(summary = "Получить все роли файлового сервера", description = "Позволяет получить все роли пользователей на файловом сервере")
    @ApiResponse(responseCode = "200", description = "Список всех ролей на файловом сервере", content = @Content(schema = @Schema(implementation = SecurityRolesResultRO.class)))
    public ResponseEntity<SecurityRolesResultRO> getAllServer() {
        List<SecurityRoleRO> roles = securityRoleService.getAll();

        SecurityRolesResultRO result = new SecurityRolesResultRO();
        result.setRoles(roles);
        result.setSuccess(true);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/me")
    @Operation(summary = "Получить свою роль", description = "Позволяет получить роль авторизованного пользователя делающего запрос")
    @ApiResponse(responseCode = "200", description = "Список всех ролей в приложении", content = @Content(schema = @Schema(implementation = RoleInfoResultRO.class)))
    public ResponseEntity<RoleInfoResultRO> getMyRole() {
        RoleRO role = roleService.getCurrentUserRole();

        RoleInfoResultRO result = new RoleInfoResultRO();
        result.setRoleInfo(role);
        result.setSuccess(true);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/update")
    @Operation(summary = "Создать или изменить роль", description = "Позволяет создавать роли или изменять права уже существующих ролей пользователей в приложении")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> create(@RequestBody CreateRoleRequestRO createRoleRequestRO) {
        roleService.update(createRoleRequestRO.getRole());

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @PostMapping("/update-all")
    @Operation(summary = "Создать или изменить множество ролей", description = "Позволяет создавать множество ролей или изменять права уже существующих ролей пользователей в приложении")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> createMany(@RequestBody CreateRolesRequestRO createRolesRequestRO) {
        roleService.update(createRolesRequestRO.getRoles());

        return ResponseEntity.ok(ResultDetailsRO.success());
    }


    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить роль", description = "Позволяет удалить роль с указанным id")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> delete(@PathVariable("id") @Parameter(description = "Id удаляемой роли", required = true) int id) {
        roleService.delete(id);

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @PostMapping("/delete-by-names")
    @Operation(summary = "Удалить роли по имени", description = "Позволяет удалить несколько ролей имеющих указанные имена")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> deleteByNames(@RequestBody DeleteByNamesRequestRO deleteRequestRO) {
        roleService.delete(deleteRequestRO.getNames());

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @GetMapping("/permissions/all")
    @Operation(summary = "Получить все права (разрешения)", description = "Позволяет получить все права (разрешения) пользователей в приложении")
    @ApiResponse(responseCode = "200", description = "Список всех прав (разрешений) в приложении", content = @Content(schema = @Schema(implementation = PermissionsResultRO.class)))
    public ResponseEntity<PermissionsResultRO> getAllPermissions() {
        List<PermissionRO> permissions = permissionService.getAll();

        PermissionsResultRO result = new PermissionsResultRO();
        result.setPermissions(permissions);
        result.setSuccess(true);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/permissions/update-all")
    @Operation(summary = "Создать или изменить множество прав (разрешений)", description = "Позволяет создавать множество прав (разрешений)")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> createManyPermissions(@RequestBody CreatePermissionsRequestRO createPermissionsRequestRO) {
        permissionService.update(createPermissionsRequestRO.getPermissions());

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @PostMapping("/permissions/delete-by-names")
    @Operation(summary = "Удалить права (разрешения) по имени", description = "Позволяет удалить несколько прав (разрешений) имеющих указанные имена")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> deletePermissionsByNames(@RequestBody DeleteByNamesRequestRO deleteRequestRO) {
        permissionService.delete(deleteRequestRO.getNames());

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @ExceptionHandler
    private ResponseEntity<ResultDetailsRO> handleUselessOperationException(UselessOperationException exception) {
        ResultDetailsRO resultRO = ResponseUtils.exceptionWarningResponse(exception);
        return ResponseEntity.ok(resultRO);
    }
}
