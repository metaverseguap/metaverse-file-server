package com.metaverse.files.rest;

import java.nio.file.Path;
import java.util.List;

import com.metaverse.files.contexts.avatar.UploadAvatarContext;
import com.metaverse.files.ro.avatar.AvatarFilePathRO;
import com.metaverse.files.ro.avatar.AvatarInfoRO;
import com.metaverse.files.ro.avatar.response.AvatarInfosResultRO;
import com.metaverse.files.ro.request.DeleteByNamesRequestRO;
import com.metaverse.files.ro.response.ResultDetailsRO;
import com.metaverse.files.services.avatar.AvatarService;
import com.metaverse.files.utils.FIleUtils;
import com.metaverse.files.utils.ResponseUtils;
import com.metaverse.files.utils.exceptions.DataNotFoundException;
import com.metaverse.files.utils.exceptions.ExceptionCode;
import com.metaverse.files.utils.exceptions.InvalidRequestStateException;
import com.metaverse.files.utils.exceptions.ServerFileException;
import com.metaverse.files.utils.exceptions.UselessOperationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST аватаров.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@RestController
@Tag(name = "Сервис аватаров", description = "Данный сервис используется работы с файлами аватаров")
@RequestMapping(AvatarRest.PATH)
public class AvatarRest {

    public static final String PATH = "/api/avatar";

    @Autowired
    private AvatarService avatarService;

    @GetMapping("/file/{name}")
    @Operation(summary = "Получить аватар по имени", description = "Позволяет получить аватар по его имени")
    public void uploadAvatar(@PathVariable("name") @Parameter(description = "Название файла аватара", required = true) String name, HttpServletResponse response) {
        AvatarFilePathRO avatar = avatarService.getAvatarByName(name);

        Path filePath = Path.of(avatar.getFilePath());
        FIleUtils.writeFileToResponse(response, filePath, avatar.getName());
    }

    @GetMapping("/all-info")
    @Operation(summary = "Получить список с информацией об аватарах", description = "Позволяет получить список содержащий информацию о всех аватарах файлового сервера")
    @ApiResponse(responseCode = "200", description = "Список список с информацией о всех аватарах", content = @Content(schema = @Schema(implementation = AvatarInfosResultRO.class)))
    public ResponseEntity<AvatarInfosResultRO> getAllInfo() {
        List<AvatarInfoRO> avatars = avatarService.getAllInfo();

        AvatarInfosResultRO result = new AvatarInfosResultRO();
        result.setInfoList(avatars);
        result.setSuccess(true);

        return ResponseEntity.ok(result);
    }

    @PostMapping(
            path = "/upload-avatar",
            consumes = {
                    "multipart/form-data"
            }
    )
    @Operation(summary = "Загрузить аватар на сервер", description = "Позволяет загрузить файл аватара на файловый сервер")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> uploadSceneFile(@RequestParam("avatar") @Parameter(description = "Файл ассета аватара", required = true) MultipartFile avatar,
                                                           @RequestParam("img") @Parameter(description = "Файл изображения аватара", required = true) MultipartFile img,
                                                           @RequestParam("displayName") @Parameter(description = "Отображаемое имя аватара", required = true) String displayName,
                                                           @RequestParam("gender") @Parameter(description = "Пол аватара", required = true) String gender) {
        UploadAvatarContext ctx = UploadAvatarContext.builder()
                .image(img)
                .avatar(avatar)
                .displayName(displayName)
                .gender(gender)
                .build();

        avatarService.saveAvatar(ctx);

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить аватар", description = "Позволяет удалить файл аватара с указанным id с файлового сервера")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> delete(@PathVariable("id") @Parameter(description = "Id удаляемого аватара", required = true) int id) {
        avatarService.delete(id);

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @PostMapping("/delete-by-names")
    @Operation(summary = "Удалить аватары по имени", description = "Позволяет удалить несколько аватаров имеющих указанные имена")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> deleteByNames(@RequestBody DeleteByNamesRequestRO deleteRequestRO) {
        avatarService.delete(deleteRequestRO.getNames());

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

    @ExceptionHandler
    private ResponseEntity<ResultDetailsRO> handleServerFileException(ServerFileException exception) {
        ResultDetailsRO resultRO = ResponseUtils.errorResponse(exception, ExceptionCode.INTERNAL_SERVER_ERROR.getCode());
        return ResponseEntity.ok(resultRO);
    }

    @ExceptionHandler
    private ResponseEntity<ResultDetailsRO> handleDataNotFoundException(DataNotFoundException exception) {
        ResultDetailsRO resultRO = ResponseUtils.errorResponse(exception, ExceptionCode.BAD_REQUEST.getCode());
        return ResponseEntity.ok(resultRO);
    }
}
