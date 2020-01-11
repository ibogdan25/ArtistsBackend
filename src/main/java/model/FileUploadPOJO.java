package model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileUploadPOJO {
    private String extensionName;
    private Long id;
    private FileUploadEntity fileUploadEntity;
}
