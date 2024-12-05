package com.metaverse.files.rest;

import java.nio.file.Path;
import java.util.List;

import com.metaverse.files.contexts.scene.UploadSceneContext;
import com.metaverse.files.ro.request.DeleteByNamesRequestRO;
import com.metaverse.files.ro.response.ResultDetailsRO;
import com.metaverse.files.ro.scene.SceneFilePathRO;
import com.metaverse.files.ro.scene.SceneInfoRO;
import com.metaverse.files.ro.scene.response.SceneInfosResultRO;
import com.metaverse.files.services.scene.SceneService;
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
 * REST сцен.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@RestController
@Tag(name = "Сервис сцен", description = "Данный сервис используется для работы с файлами сцен")
@RequestMapping(ScenesRest.PATH)
public class ScenesRest {

    public static final String PATH = "/api/scenes";

    @Autowired
    private SceneService sceneService;

    @GetMapping("/file/{name}")
    @Operation(summary = "Получить сцену по имени", description = "Позволяет получить сцену по имени")
    public void uploadScene(@PathVariable("name") @Parameter(description = "Название файла сцены", required = true) String name, HttpServletResponse response) {
        SceneFilePathRO scene = sceneService.getSceneByName(name);

        Path filePath = Path.of(scene.getFilePath());
        FIleUtils.writeFileToResponse(response, filePath, scene.getName());
    }

    @GetMapping("/all-info")
    @Operation(summary = "Получить список с информацией о сценах", description = "Позволяет список содержащий информацию о всех сценах файлового сервера")
    @ApiResponse(responseCode = "200", description = "Список список с информацией о всех сценах", content = @Content(schema = @Schema(implementation = SceneInfosResultRO.class)))
    public ResponseEntity<SceneInfosResultRO> getAllInfo() {
        List<SceneInfoRO> scenes = sceneService.getAllInfo();

        SceneInfosResultRO result = new SceneInfosResultRO();
        result.setInfoList(scenes);
        result.setSuccess(true);

        return ResponseEntity.ok(result);
    }

    @PostMapping(
            path = "/upload-scene",
            consumes = {
                    "multipart/form-data"
            }
    )
    @Operation(summary = "Загрузить сцену на сервер", description = "Позволяет загрузить файл сцены на файловый сервер")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> uploadSceneFile(@RequestParam("scene") @Parameter(description = "Файл ассета сцены", required = true) MultipartFile scene,
                                                           @RequestParam("img") @Parameter(description = "Файл изображения сцены", required = true) MultipartFile img,
                                                           @RequestParam("displayName") @Parameter(description = "Отображаемое имя сцены", required = true) String displayName,
                                                           @RequestParam("device") @Parameter(description = "Устройство, для которого создан ассет сцены", required = true) String device,
                                                           @RequestParam("sortIndex") @Parameter(description = "Индекс сортировки сцены", required = true) int sortIndex) {
        UploadSceneContext ctx = UploadSceneContext.builder()
                .image(img)
                .scene(scene)
                .displayName(displayName)
                .device(device)
                .sortIndex(sortIndex)
                .build();

        sceneService.saveScene(ctx);

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить сцену", description = "Позволяет удалить файл сцены с указанным id с файлового сервера")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> delete(@PathVariable("id") @Parameter(description = "Id удаляемой сцены", required = true) int id) {
        sceneService.delete(id);

        return ResponseEntity.ok(ResultDetailsRO.success());
    }

    @PostMapping("/delete-by-names")
    @Operation(summary = "Удалить сцены по имени", description = "Позволяет удалить несколько сцен имеющих указанные имена")
    @ApiResponse(responseCode = "200", description = "Статус выполнения запроса", content = @Content(schema = @Schema(implementation = ResultDetailsRO.class)))
    public ResponseEntity<ResultDetailsRO> deleteByNames(@RequestBody DeleteByNamesRequestRO deleteRequestRO) {
        sceneService.delete(deleteRequestRO.getNames());

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
    private ResponseEntity<ResultDetailsRO> handleDataNotFoundException(DataNotFoundException exception) {
        ResultDetailsRO resultRO = ResponseUtils.errorResponse(exception, ExceptionCode.NOT_FOUND.getCode());
        return ResponseEntity.ok(resultRO);
    }

    @ExceptionHandler
    private ResponseEntity<ResultDetailsRO> handleServerFileException(ServerFileException exception) {
        ResultDetailsRO resultRO = ResponseUtils.errorResponse(exception, ExceptionCode.INTERNAL_SERVER_ERROR.getCode());
        return ResponseEntity.ok(resultRO);
    }
}
