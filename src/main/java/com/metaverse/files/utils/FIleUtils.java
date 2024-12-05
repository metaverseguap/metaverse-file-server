package com.metaverse.files.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import com.metaverse.files.utils.exceptions.ServerFileException;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Часто используемые методы в работе с файлами.
 *
 * @author Mikhail.Kataranov
 * @since 28.11.2024
 */
public class FIleUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FIleUtils.class);

    private FIleUtils() {}

    /**
     * Сохранить MultipartFile по указанному пути.
     *
     * @param file MultipartFile
     * @param path путь сохранения. Данный путь обязан существовать
     * @throws ServerFileException если во время сохранения файла произошла ошибка
     */
    public static void saveFileByPath(MultipartFile file, Path path) {
        try {
            file.transferTo(path.toFile());
        } catch (IOException e) {
            String message = String.format("File save exception: %s", e.getMessage());
            LOGGER.error(message);
            throw new ServerFileException(message);
        }
    }

    /**
     * Сохранить MultipartFile по указанному пути потоковым методом.
     *
     * @param file MultipartFile
     * @param path путь сохранения. Данный путь обязан существовать
     * @throws ServerFileException если во время сохранения файла произошла ошибка
     */
    public static void saveFileThroughStream(MultipartFile file, Path path) {
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            String message = String.format("File save exception: %s", e.getMessage());
            LOGGER.error(message);
            throw new ServerFileException(message);
        }
    }

    /**
     * Преобразовать файл в массив байт.
     *
     * @param file файл
     * @return массив байт или null, если не удалось выполнить преобразование
     */
    @Nullable
    public static byte[] getBytesFromFile(File file) {
        if (!file.exists()) {
            LOGGER.error(String.format("File %s not found", file.getPath()));
            return null;
        }

        try {
            return Files.readAllBytes(Path.of(file.getPath()));
        } catch (IOException e) {
            LOGGER.error(String.format("File %s can not read", file.getPath()));
            return null;
        }
    }

    /**
     * Передать потоковым методом файл в HttpServletResponse.
     *
     * @param response HttpServletResponse
     * @param filePath путь к файлу
     * @param fileName имя файла
     */
    public static void writeFileToResponse(HttpServletResponse response, Path filePath, String fileName) {
        int bufferSize = 8192;

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(filePath));
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(response.getOutputStream())) {

            // Определяем MIME-тип
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            response.setContentType(contentType);
            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            response.setBufferSize(bufferSize); // Ограничение буфера
            response.setHeader("Connection", "close"); // Закрыть соединение после передачи

            // Передаем файл через поток
            byte[] buffer = new byte[bufferSize];
            int bytesRead;
            while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, bytesRead);
            }
            bufferedOutputStream.flush();
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            String message = String.format("File %s can not write to response case: %s", fileName,e.getMessage());
            LOGGER.error(message);
        }
    }
}
