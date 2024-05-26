package boutique.com.boutique.ServiceImpl;

import boutique.com.boutique.Entities.Media;
import boutique.com.boutique.Repositories.MediaRepo;
import boutique.com.boutique.Services.MediaService;
import boutique.com.boutique.Utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class MediaServiceImpl implements MediaService {
    private final MediaRepo mediaRepo;

    public MediaServiceImpl(MediaRepo mediaRepo) {
        this.mediaRepo = mediaRepo;
    }

    @Override
    public HashMap<String, Object> create(List<MultipartFile> file) {
        HashMap<String, Object> result = new HashMap<>();
        List<Long> ids = new ArrayList<>();
        try {
            file.forEach(item -> {

                Path picturePath = Paths.get(Utils.PICTURE_PATH);
                Utils.init(picturePath);
                String name = Utils.save(item, picturePath);
                Media media = new Media();
                media.setContentType(item.getContentType());
                media.setCreationDate(new Date());
                media.setLabel(name);
                mediaRepo.save(media);
                media.setPath(Utils.createPictureUrl(media.getId(), Utils.PICTURE_BASE_URL));
                Media mediaAdded = mediaRepo.save(media);
                ids.add(mediaAdded.getId());

            });
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", e.getMessage());
            return result;
        }
        result.put("status", "success");
        result.put("message", ids);
        return result;

    }




    @Override
    public Media getMediaById(Long id) {
        Optional<Media> mediaOptional = mediaRepo.findById(id);
        if (mediaOptional.isPresent()) {
            Media media = mediaOptional.get();
            return media;
        }
        return null;
    }
    @Override
    public Resource load(Long id) {
        // TODO Auto-generated method stub
        Media mediaOptional = mediaRepo.findById(id).get();

        Path categoryPath = Paths.get(Utils.PICTURE_PATH);
        return Utils.load(mediaOptional.getLabel(), categoryPath);
    }
    @Override
    public void downloadPDFResource(HttpServletRequest request, HttpServletResponse response, Long id) throws IOException {
        Media media = mediaRepo.findById(id).orElse(null);

        Path file = Paths.get(Paths.get(Utils.PICTURE_PATH) + "/" + media.getLabel());

        String contentType = Files.probeContentType(file);
        if (contentType == null) {
            contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }

        response.setContentType(contentType);
        response.setContentLengthLong(Files.size(file));

        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, ContentDisposition.attachment()
                .filename(file.getFileName().toString(), StandardCharsets.UTF_8).build().toString());
        Files.copy(file, response.getOutputStream());
    }



}
