package gdghackathon.monthlychallenge.aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;

@Log4j2
public class S3Client {

    @Autowired
    private AwsConfigure awsConfigure;

    private AmazonS3 amazonS3;

    private static final S3Client instance = null;

    private S3Client() {
        createS3Client();
    }

    public static S3Client getInstance() {
        return instance == null
                ? new S3Client()
                : instance;
    }

    // AWS S3Client 생성
    private void createS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(awsConfigure.getAccessKey(), awsConfigure.getSecretKey());
        this.amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.fromName(awsConfigure.getRegion()))
                .build();
    }

    public S3Object get(String key) {
        GetObjectRequest request = new GetObjectRequest(awsConfigure.getBucketName(), key);
        return amazonS3.getObject(request);
    }

    /**
     * 파일 업로드 하기 위한 두 가지 방법
     * 1) File 객체와 key를 통해 생성
     * 2) InputStream, key, File정보(contentType, length)를 통해 생성
     */
    public String upload(File file) {
        PutObjectRequest request = new PutObjectRequest(awsConfigure.getBucketName(), file.getName(), file);
        return executePut(request);
    }

    public String upload(byte[] bytes, String basePath, Map<String, String> metadata) {
        String name = basePath.isEmpty() ? UUID.randomUUID().toString() : basePath + "/" + UUID.randomUUID().toString();
        return upload(new ByteArrayInputStream(bytes), bytes.length, name + ".jpeg", "image/jpeg", metadata);
    }

    public String upload(InputStream in, long length, String key, String contentType, Map<String, String> metadata) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(length);
        objectMetadata.setContentType(contentType);
        if (metadata != null && !metadata.isEmpty()) {
            objectMetadata.setUserMetadata(metadata);
        }
        PutObjectRequest request = new PutObjectRequest(awsConfigure.getBucketName(), key, in, objectMetadata);

        return executePut(request);
    }

    // 썸네일 업로드
    public void upload(BufferedImage image, String fileName, String contentType) {
        try {
            ByteArrayOutputStream outstream = new ByteArrayOutputStream();
            ImageIO.write(image, getRealContentType(contentType), outstream);
            byte[] buffer = outstream.toByteArray();
            InputStream is = new ByteArrayInputStream(buffer);

            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType(contentType);
            meta.setContentLength(buffer.length);
            executePut(new PutObjectRequest(awsConfigure.getBucketName(), fileName, is, meta));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // contentType = "image/jpg" 에서 확장자(jpg)만 얻기
    private String getRealContentType(String contentType) {
        String realContentType = null;

        if (contentType.contains("/")) {
            int index = contentType.indexOf("/");
            realContentType = contentType.substring(index + 1);
        }

        return realContentType == null
                ? contentType
                : realContentType;
    }

    // PutObjectRequest: 객체 메타 데이터 + 파일 데이터로 구성
    public String executePut(PutObjectRequest request) {
        amazonS3.putObject(request.withCannedAcl(CannedAccessControlList.PublicRead));
        log.info("s3 key: " + request.getKey());
        log.info("s3 url: " + awsConfigure.getUrl() + request.getKey());

        return awsConfigure.getUrl() + request.getKey();
    }

    public void delete(String key) {
        DeleteObjectRequest request = new DeleteObjectRequest(awsConfigure.getBucketName(), key);
        amazonS3.deleteObject(request);
    }

}
