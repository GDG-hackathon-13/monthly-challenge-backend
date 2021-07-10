package gdghackathon.monthlychallenge.aws;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AwsConfigure {

    @Value("${cloud.aws.s3.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.s3.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    @Value("${cloud.aws.s3.region}")
    private String region;

    @Value("${cloud.aws.s3.url}")
    private String url;


}
