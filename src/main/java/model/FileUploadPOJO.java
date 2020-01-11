package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileUploadPOJO {
    private String extensionName;
    private Long id;
    private FileUploadEntity fileUploadEntity;
}
