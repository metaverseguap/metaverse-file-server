package com.metaverse.files.services.avatar;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import com.metaverse.files.contexts.avatar.UploadAvatarContext;
import com.metaverse.files.converters.avatar.AvatarFilePathConverter;
import com.metaverse.files.converters.avatar.AvatarInfoConverter;
import com.metaverse.files.models.AvatarModel;
import com.metaverse.files.repositories.AvatarRepository;
import com.metaverse.files.ro.avatar.AvatarInfoRO;
import com.metaverse.files.ro.avatar.AvatarFilePathRO;
import com.metaverse.files.utils.FIleUtils;
import com.metaverse.files.utils.exceptions.DataNotFoundException;
import com.metaverse.files.utils.exceptions.InvalidRequestStateException;
import com.metaverse.files.utils.exceptions.UselessOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса аватаров.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Service
@Transactional(readOnly = true)
public class AvatarServiceImp implements AvatarService {

    @Autowired
    private AvatarRepository avatarRepository;
    @Autowired
    private AvatarInfoConverter infoConverter;
    @Autowired
    private AvatarFilePathConverter avatarConverter;

    @Value("${application.avatar.directory}")
    private String avatarDirectory;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AvatarInfoRO> getAllInfo() {
        List<AvatarModel> all = avatarRepository.findAll();
        return infoConverter.to(all);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AvatarFilePathRO getAvatarByName(String name) {
        Optional<AvatarModel> avatar = avatarRepository.findByName(name);
        if (avatar.isEmpty()) {
            String message = String.format("The avatar with name [%s] does not exist", name);
            throw new DataNotFoundException(message);
        }

        return avatarConverter.to(avatar.get());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveAvatar(UploadAvatarContext ctx) {
        ensureAvatar(ctx);
        ensureImage(ctx);

        createFilesOnDisk(ctx);

        saveAvatarInfoToDB(ctx);
    }

    private static void ensureAvatar(UploadAvatarContext ctx) {
        if (!ctx.hasScene()) {
            throw new InvalidRequestStateException("Avatar file is empty");
        }
    }

    private static void ensureImage(UploadAvatarContext ctx) {
        if (!ctx.hasImage()) {
            throw new InvalidRequestStateException("Image file is empty");
        }
    }

    private void createFilesOnDisk(UploadAvatarContext ctx) {
        Path currentRelativePath = Paths.get("");
        Path fileDirectoryPath = Paths.get(currentRelativePath.toAbsolutePath().toString(), avatarDirectory);

        Path imageFullName = Paths.get(fileDirectoryPath.toString(), ctx.getImage().getOriginalFilename());
        FIleUtils.saveFileByPath(ctx.getImage(), imageFullName);

        Path avatarFullName = Paths.get(fileDirectoryPath.toString(), ctx.getAvatar().getOriginalFilename());
        FIleUtils.saveFileThroughStream(ctx.getAvatar(), avatarFullName);
    }

    private void saveAvatarInfoToDB(UploadAvatarContext ctx) {
        Path avatarFullName = Paths.get(avatarDirectory, ctx.getAvatar().getOriginalFilename());
        Path imageFullName = Paths.get(avatarDirectory, ctx.getImage().getOriginalFilename());

        AvatarModel avatarModel = new AvatarModel();
        avatarModel.setName(ctx.getAvatar().getOriginalFilename());
        avatarModel.setDisplayName(ctx.getDisplayName());
        avatarModel.setGender(ctx.getGender());
        avatarModel.setFilePath(avatarFullName.toString());
        avatarModel.setImageFilePath(imageFullName.toString());

        avatarRepository.save(avatarModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(int id) {
        AvatarModel avatarFromDB = getAvatarFromDB(id);

        deleteFilesFromDisk(avatarFromDB);

        avatarRepository.delete(avatarFromDB);
    }

    private AvatarModel getAvatarFromDB(int id) {
        Optional<AvatarModel> avatarFromDB = avatarRepository.findById(id);
        if (avatarFromDB.isEmpty()) {
            String message = String.format("The avatar with id [%d] does not exist", id);
            throw new UselessOperationException(message);
        }

        return avatarFromDB.get();
    }

    private static void deleteFilesFromDisk(AvatarModel avatarFromDB) {
        Paths.get(avatarFromDB.getFilePath()).toFile().delete();
        Paths.get(avatarFromDB.getImageFilePath()).toFile().delete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(List<String> avatarNames) {
        List<AvatarModel> avatarsFromDB = avatarRepository.findAllByNameIn(avatarNames);
        for (AvatarModel avatarFromDB : avatarsFromDB) {
            deleteFilesFromDisk(avatarFromDB);
        }

        avatarRepository.deleteAll(avatarsFromDB);
    }
}
