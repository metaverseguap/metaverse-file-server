package com.metaverse.files.services.scene;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.metaverse.files.contexts.scene.UploadSceneContext;
import com.metaverse.files.converters.scene.SceneFilePathConverter;
import com.metaverse.files.converters.scene.SceneInfoConverter;
import com.metaverse.files.models.SceneModel;
import com.metaverse.files.repositories.SceneRepository;
import com.metaverse.files.ro.scene.SceneFilePathRO;
import com.metaverse.files.ro.scene.SceneInfoRO;
import com.metaverse.files.ro.scene.SceneUpdateInfoRO;
import com.metaverse.files.utils.FIleUtils;
import com.metaverse.files.utils.TimeUtils;
import com.metaverse.files.utils.exceptions.DataNotFoundException;
import com.metaverse.files.utils.exceptions.InvalidRequestStateException;
import com.metaverse.files.utils.exceptions.UselessOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Реализация сервиса сцен.
 *
 * @author Mikhail.Kataranov
 * @since 01.11.2024
 */
@Service
@Transactional(readOnly = true)
public class SceneServiceImpl implements SceneService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SceneServiceImpl.class);

    @Autowired
    private SceneRepository sceneRepository;
    @Autowired
    private SceneInfoConverter sceneInfoConverter;
    @Autowired
    private SceneFilePathConverter sceneConverter;

    @Value("${app.scene.directory}")
    private String sceneDirectory;

    /**
     * {@literal ConcurrentHashMap<sceneName, updateDate>}
     */
    private final ConcurrentHashMap<String, Date> sceneUpdates = new ConcurrentHashMap<>();

    private boolean isSceneUpdatesInitialized = false;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SceneUpdateInfoRO> getUpdateInfos() {
        ensureSceneUpdatesInitialized();

        List<SceneUpdateInfoRO> updateInfos = new ArrayList<>();
        for (var sceneUpdate : sceneUpdates.entrySet()) {
            SceneUpdateInfoRO updateInfo = new SceneUpdateInfoRO();
            updateInfo.setName(sceneUpdate.getKey());
            updateInfo.setUpdateDate(sceneUpdate.getValue());
            updateInfos.add(updateInfo);
        }

        return updateInfos;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<SceneInfoRO> getAllInfo() {
        List<SceneModel> all = sceneRepository.findAll();
        return sceneInfoConverter.to(all);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SceneInfoRO getSceneInfoByName(String name) {
        SceneModel sceneModel = getSceneModel(name);
        return sceneInfoConverter.to(sceneModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SceneFilePathRO getSceneByName(String name) {
        SceneModel sceneModel = getSceneModel(name);
        return sceneConverter.to(sceneModel);
    }

    private SceneModel getSceneModel(String name) {
        Optional<SceneModel> scene = sceneRepository.findByName(name);
        if (scene.isEmpty()) {
            String message = String.format("The scene with name [%s] does not exist", name);
            throw new DataNotFoundException(message);
        }

        return scene.get();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void saveScene(UploadSceneContext ctx) {
        ensureScene(ctx);
        ensureImage(ctx);

        createFilesOnDisk(ctx);

        saveSceneInfoToDB(ctx);
    }

    private static void ensureScene(UploadSceneContext ctx) {
        if (!ctx.hasScene()) {
            throw new InvalidRequestStateException("Scene file is empty");
        }
    }

    private static void ensureImage(UploadSceneContext ctx) {
        if (!ctx.hasImage()) {
            throw new InvalidRequestStateException("Image file is empty");
        }
    }

    private void createFilesOnDisk(UploadSceneContext ctx) {
        Path currentRelativePath = Paths.get("");
        Path fileDirectoryPath = Paths.get(currentRelativePath.toAbsolutePath().toString(), sceneDirectory);

        Path imageFullName = Paths.get(fileDirectoryPath.toString(), ctx.getImage().getOriginalFilename());
        FIleUtils.saveFileByPath(ctx.getImage(), imageFullName);

        Path sceneFullName = Paths.get(fileDirectoryPath.toString(), ctx.getScene().getOriginalFilename());
        FIleUtils.saveFileThroughStream(ctx.getScene(), sceneFullName);
    }

    private void saveSceneInfoToDB(UploadSceneContext ctx) {
        Path sceneFullName = Paths.get(sceneDirectory, ctx.getScene().getOriginalFilename());
        Path imageFullName = Paths.get(sceneDirectory, ctx.getImage().getOriginalFilename());

        SceneModel sceneModel = new SceneModel();
        sceneModel.setDevice(ctx.getDevice());
        sceneModel.setName(ctx.getScene().getOriginalFilename());
        sceneModel.setDisplayName(ctx.getDisplayName());
        sceneModel.setFilePath(sceneFullName.toString());
        sceneModel.setImageFilePath(imageFullName.toString());
        sceneModel.setSortIndex(ctx.getSortIndex());
        sceneModel.setUpdateDate(TimeUtils.dateNow());
        ensureSceneUpdatesInitialized();
        sceneUpdates.put(sceneModel.getName(), sceneModel.getUpdateDate());

        sceneRepository.save(sceneModel);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(int id) {

        SceneModel sceneFromDB = getSceneFromDB(id);

        deleteFilesFromDisk(sceneFromDB);

        ensureSceneUpdatesInitialized();
        sceneUpdates.remove(sceneFromDB.getName());

        sceneRepository.delete(sceneFromDB);
    }

    private SceneModel getSceneFromDB(int id) {
        Optional<SceneModel> sceneFromDB = sceneRepository.findById(id);
        if (sceneFromDB.isEmpty()) {
            String message = String.format("The scene with id [%d] does not exist", id);
            throw new UselessOperationException(message);
        }

        return sceneFromDB.get();
    }

    private static void deleteFilesFromDisk(SceneModel sceneFromDB) {
        Paths.get(sceneFromDB.getFilePath()).toFile().delete();
        Paths.get(sceneFromDB.getImageFilePath()).toFile().delete();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void delete(List<String> sceneNames) {
        ensureSceneUpdatesInitialized();
        List<SceneModel> scenesFromDB = sceneRepository.findAllByNameIn(sceneNames);
        for (SceneModel sceneFromDB : scenesFromDB) {
            deleteFilesFromDisk(sceneFromDB);
            sceneUpdates.remove(sceneFromDB.getName());
        }

        sceneRepository.deleteAll(scenesFromDB);
    }

    private void ensureSceneUpdatesInitialized() {
        if (isSceneUpdatesInitialized) {
            return;
        }

        LOGGER.info("Initializing scene updates...");

        List<SceneInfoRO> allInfo = getAllInfo();
        for (SceneInfoRO scene : allInfo) {
            sceneUpdates.put(scene.getName(), scene.getUpdateDate());
        }

        isSceneUpdatesInitialized = true;
    }
}
