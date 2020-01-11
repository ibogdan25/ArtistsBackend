package controller;

import model.FileUploadEntity;
import model.FileUploadPOJO;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.FilesManager;
import service.SessionServiceImpl;
import service.UserServiceImpl;

import javax.security.auth.callback.Callback;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.*;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
public class ImageController {
    private static Logger log = Logger.getLogger(FilesManager.class.getName());
    private final Integer MAX_THREADS = 8;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private SessionServiceImpl sessionService;
    private final ExecutorService pool = Executors.newFixedThreadPool(MAX_THREADS);

    private final FilesManager filesManager = new FilesManager();

    @PostMapping("/static/files/upload")
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file,
                                           @RequestParam("info") FileUploadPOJO info,
                                           @RequestHeader(name="Authorization") String token) {
        Callable<String> task = () -> filesManager.storeFile(info, file);
        Future<String> future = pool.submit(task);
        try {
            final String pathName = future.get();
            if (pathName != null) {
                if (info.getFileUploadEntity().equals(FileUploadEntity.USER_PROFILE)) {
                    User user = userService.getUserById(info.getId());
                    User userSession = sessionService.getSessionByToken(token);
                    if (user != null && userSession != null && user.getUserId() == userSession.getUserId()) {
                        user.setProfileImgSrc(pathName);
                        userService.updateUserInfo(user.getUserId(), user);
                        return new ResponseEntity<>(HttpStatus.OK);
                    } else {
                        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                    }
                }
            }
        } catch (InterruptedException e) {
            log.warning("Error while uploading file.");
        } catch (ExecutionException e) {
            log.warning("Error while uploading file.");
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/static/files/{id}", method=RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody byte[] showImageOnId(@PathVariable("id") String id) {
        File file = new File(id);
        byte[] b = new byte[0];
        try {
            b = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            return null;
        }
        return b;
    }
}
