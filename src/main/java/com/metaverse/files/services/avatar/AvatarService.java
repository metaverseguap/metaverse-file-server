package com.metaverse.files.services.avatar;

import java.util.List;

import com.metaverse.files.contexts.avatar.UploadAvatarContext;
import com.metaverse.files.ro.avatar.AvatarInfoRO;
import com.metaverse.files.ro.avatar.AvatarFilePathRO;
import com.metaverse.files.utils.exceptions.DataNotFoundException;
import com.metaverse.files.utils.exceptions.InvalidRequestStateException;
import com.metaverse.files.utils.exceptions.ServerFileException;
import com.metaverse.files.utils.exceptions.UselessOperationException;

/**
 * Сервис аватаров.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
public interface AvatarService {

    /**
     * Получить список с информацией об аватарах.
     *
     * @return список с информацией об аватарах
     */
    List<AvatarInfoRO> getAllInfo();

    /**
     * Получить аватар по имени.
     *
     * @param name имя аватара
     * @return путь до файла аватара
     * @throws DataNotFoundException если аватар с таким именем не был найден в БД
     */
    AvatarFilePathRO getAvatarByName(String name);

    /**
     * Сохранить файл аватара.
     *
     * @param ctx {@link UploadAvatarContext контекст сохранения файла аватара}
     * @throws InvalidRequestStateException если {@link UploadAvatarContext контекст сохранения файла аватара}
     * содержит невалидные данные
     * @throws ServerFileException          если при работе с файлами на диске произошли ошибки
     */
    void saveAvatar(UploadAvatarContext ctx);

    /**
     * Удалить аватар по id.
     *
     * @param id id аватара
     * @throws UselessOperationException если удаляемого аватара нет в базе данных
     */
    void delete(int id);

    /**
     * Удалить аватары.
     *
     * @param avatarNames имена удаляемых аватаров
     */
    void delete(List<String> avatarNames);
}
