package boutique.com.boutique.Services;

import boutique.com.boutique.Entities.Media;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface MediaService {
    HashMap<String, Object> create(List<MultipartFile> file);

    Media getMediaById(Long id);
    Resource load(Long id);
 void downloadPDFResource(HttpServletRequest request, HttpServletResponse response,Long id) throws IOException;
}
