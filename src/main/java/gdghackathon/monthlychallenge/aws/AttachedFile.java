package gdghackathon.monthlychallenge.aws;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

import static org.apache.commons.io.FilenameUtils.getExtension;
import static org.apache.commons.lang3.StringUtils.defaultIfEmpty;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class AttachedFile {

    private final String originalFileName;
    private final String contentType;
    private final byte[] bytes;

    public AttachedFile(String originalFileName, String contentType, byte[] bytes) {
        this.originalFileName = originalFileName;
        this.contentType = contentType;
        this.bytes = bytes;
    }

    public static AttachedFile of(MultipartFile multipartFile) {
        try {
            return verify(multipartFile)
                    ? new AttachedFile(multipartFile.getOriginalFilename(), multipartFile.getContentType(), multipartFile.getBytes())
                    : null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean verify(MultipartFile multipartFile) {
        if (multipartFile != null && multipartFile.getSize() > 0 && multipartFile.getOriginalFilename() != null) {
            String contentType = multipartFile.getContentType();
            return !Objects.isNull(contentType) &&
                    (contentType.toLowerCase().startsWith("images")) || contentType.toLowerCase().startsWith("image");
        }
        return false;
    }

    public String extension(String defaultExtension) {
        return defaultIfEmpty(getExtension(originalFileName), defaultExtension);
    }


    public String randomName(String defaultExtension) {
        return randomName(null, defaultExtension);
    }

    public String randomName(String basePath, String defaultExtension) {
        String name = isEmpty(basePath) ? UUID.randomUUID().toString() : basePath + "/" + UUID.randomUUID().toString();
        return name + "." + extension(defaultExtension);
    }

    public InputStream inputStream() {
        return new ByteArrayInputStream(bytes);
    }

    public long length() {
        return bytes.length;
    }

    public String getContentType() {
        return contentType;
    }

}
