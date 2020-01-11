package service;

import model.FileUploadPOJO;
import org.springframework.web.multipart.MultipartFile;
import utils.ConstantsUtils;
import utils.ServerContext;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class FilesManager {
    private static Logger log = Logger.getLogger(FilesManager.class.getName());


    public String storeFile(FileUploadPOJO info, MultipartFile file) {
        String realPathtoUploads = ServerContext.getProperties().getValue(ConstantsUtils.PROP_PATH_TO_IMAGES);
        if(!new File(realPathtoUploads).exists()) {
            log.info(String.format("Folder %s doesn't exist. New folder will be created.", realPathtoUploads));
            new File(realPathtoUploads).mkdir();
        }

        final String filePath = String.format("%s%s.%s", realPathtoUploads, String.valueOf(System.currentTimeMillis()), info.getExtensionName());
        try {
            file.transferTo(new File(filePath));
            return filePath;
        } catch (IOException e) {
            log.warning(String.format("Error while saving file: %s", filePath));
            return null;
        }

    }
}
