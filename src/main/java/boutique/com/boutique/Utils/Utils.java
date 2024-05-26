package boutique.com.boutique.Utils;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

public class Utils {
    public static final String BOUTIQUE_BASE_URL = "https://localhost:8081/cms";

    	public static final String PICTURE_PATH = "C:" + File.separator + "pictures" ;
//    public static final String PICTURE_PATH = "/opt/uploadImages/";
    public static final String PICTURE_BASE_URL = "/media/display_image/";
    public static String createPictureUrl(Long idFile, String url) {
        return BOUTIQUE_BASE_URL + url + idFile;
    }
    public static void init(Path path) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectory(path);
            }

        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }
    public static String renameFile(String fileName) {
        String extension = FilenameUtils.getExtension(fileName);
        String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);
        String name = fileNameWithOutExt + parseDateToStringFileName(new Date()) + "." + extension;
        return name;

    }
    public static String parseDateToStringFileName(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        // System.out.println(sdf.format(date));
        return sdf.format(date);
    }
    public static String save(MultipartFile file, Path path) {
        try {
            String name = Utils.renameFile(file.getOriginalFilename());
            Files.copy(file.getInputStream(), path.resolve(name));

            return name;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static Resource load(String filename, Path path) {
        try {
            Path file = path.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

}
