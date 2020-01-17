package controller;

import model.Artist;
import model.FileUploadEntity;
import model.FileUploadPOJO;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.ArtistService;
import service.FilesManager;
import service.SessionServiceImpl;
import service.UserServiceImpl;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "*")
public class ImageController {
    private static Logger log = Logger.getLogger(ImageController.class.getName());
    private final Integer MAX_THREADS = 8;
    @Autowired
    private ArtistService artistService;
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
            User userSession = sessionService.getSessionByToken(token);
            if (userSession == null){
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }

            final String pathName = future.get();
            if (pathName != null) {
                if (info.getFileUploadEntity().equals(FileUploadEntity.USER_PROFILE)) {
                    User user = userService.getUserById(info.getId());
                    if (user != null && userSession != null && user.getUserId() == userSession.getUserId()) {
                        user.setProfileImgSrc(pathName);
                        userService.updateUserInfo(user.getUserId(), user);
                        return new ResponseEntity<>(HttpStatus.OK);
                    } else {
                        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
                    }
                }

                if (info.getFileUploadEntity().equals(FileUploadEntity.ARTIST_COVER)){
                    try {
                        Artist artist = artistService.getById(info.getId());
                        artist.setCoverUrl(pathName);
                        if (artistService.save(artist)== null)
                            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                        return new ResponseEntity(HttpStatus.OK);
                    } catch (Exception e){
                        return new ResponseEntity(HttpStatus.BAD_REQUEST);
                    }
                }

                if (info.getFileUploadEntity().equals(FileUploadEntity.ARTIST_AVATAR)){
                    try {
                        Artist artist = artistService.getById(info.getId());
                        artist.setAvatarUrl(pathName);
                        if (artistService.save(artist)== null)
                            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
                        return new ResponseEntity(HttpStatus.OK);
                    } catch (Exception e){
                        return new ResponseEntity(HttpStatus.BAD_REQUEST);
                    }
                }

                if (info.getFileUploadEntity().equals(FileUploadEntity.EVENT_POSTER)){
                    // TO DO: Solve for events
                }

                if (info.getFileUploadEntity().equals(FileUploadEntity.POST)){
                    // TO DO: Solve for posts
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
