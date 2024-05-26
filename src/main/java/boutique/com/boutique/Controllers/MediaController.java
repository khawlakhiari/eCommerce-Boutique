package boutique.com.boutique.Controllers;

import boutique.com.boutique.Entities.Media;
import boutique.com.boutique.Services.MediaService;
import boutique.com.boutique.view.*;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        super();
        this.mediaService = mediaService;
    }
    @PostMapping(value = "/upload", consumes = "multipart/form-data")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> uploadImage(@RequestParam("image") List<MultipartFile> file) {
        try {
            HashMap<String, Object> result = mediaService.create(file);
            if (result.get("status") == "success")
                return new ResponseEntity<>(result.get("message"), HttpStatus.CREATED);
            else
                return new ResponseEntity<>(result.get("message"), HttpStatus.EXPECTATION_FAILED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getCause(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }



    @GetMapping(value = "/display_pdf/{id}")
    public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response, @PathVariable Long id)
            throws IOException {
        mediaService.downloadPDFResource(request, response, id);

    }

    @GetMapping(value = "/display_image/{id}", produces = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE,
            MediaType.APPLICATION_OCTET_STREAM_VALUE })
    @JsonView(Views.Public.class)
    public ResponseEntity<Object> getImage(@PathVariable Long id) {
        Resource file = mediaService.load(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);

    }


    @GetMapping("/get_media/{id}")
    @JsonView(Views.Public.class)
    public ResponseEntity<?> getMediaById(@PathVariable(name = "id") Long id) {
        try {

            Media media = mediaService.getMediaById(id);
            if (media != null)
                return new ResponseEntity<>(media, HttpStatus.OK);
            else
                return new ResponseEntity<>("Invalid id image", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
