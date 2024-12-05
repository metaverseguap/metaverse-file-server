package com.metaverse.files.ro.request;


import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Rest Object запроса удаления объектов по их имени.
 *
 * @author Mikhail.Kataranov
 * @since 23.11.2024
 */
@Schema(description = "Запрос удаления объектов по их имени")
public class DeleteByNamesRequestRO {

    @Schema(description = "Имена объектов")
    private List<String> names;

    /**
     * @return имена объектов
     */
    public List<String> getNames() {
        return names;
    }

    /**
     * @param names имена объектов
     */
    public void setNames(List<String> names) {
        this.names = names;
    }
}
