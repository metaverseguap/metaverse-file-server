package com.metaverse.files.utils;


import java.util.Collections;

import com.metaverse.files.ro.response.ResultDetailsRO;
import com.metaverse.files.ro.response.details.ErrorDetailsRO;
import com.metaverse.files.ro.response.details.InfoDetailsRO;

/**
 * Часто используемые методы в работе с ответами на запросы в REST.
 *
 * @author Mikhail.Kataranov
 * @since 05.12.2024
 */
public class ResponseUtils {

    private ResponseUtils() {
    }

    /**
     * Создает ответ клиенту об успешном выполнении запроса, но записывает сообщение об ошибке в предупреждения.
     *
     * @param exception ошибка, произошедшая при выполнении запроса
     * @return ответ об успешном выполнении запроса с предупреждением об ошибке
     */
    public static ResultDetailsRO exceptionWarningResponse(final RuntimeException exception) {
        InfoDetailsRO warning = new InfoDetailsRO();
        warning.setContent(exception.getMessage());

        ResultDetailsRO resultRO = new ResultDetailsRO();
        resultRO.setSuccess(true);
        resultRO.setWarning(Collections.singletonList(warning));

        return resultRO;
    }

    /**
     * Создает ответ клиенту об ошибке выполнения запроса.
     *
     * @param exception ошибка, произошедшая при выполнении запроса
     * @param code код ошибки в строковом представлении
     * @return ответ об ошибке выполнения запроса
     */
    public static ResultDetailsRO errorResponse(RuntimeException exception, String code) {
        ErrorDetailsRO error = new ErrorDetailsRO();
        error.setExceptionMessage(exception.getMessage());
        error.setCode(code);

        ResultDetailsRO resultRO = new ResultDetailsRO();
        resultRO.setSuccess(false);
        resultRO.setError(Collections.singletonList(error));

        return resultRO;
    }
}
