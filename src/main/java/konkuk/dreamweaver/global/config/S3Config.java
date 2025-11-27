package konkuk.dreamweaver.global.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import konkuk.dreamweaver.global.properties.S3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class S3Config {

    private final S3Properties properties;

    @Bean
    public AmazonS3 amazonS3(){
        BasicAWSCredentials credentials = new BasicAWSCredentials(properties.credentials().accessKey(), properties.credentials().secretKey());

        return AmazonS3ClientBuilder.standard()
                .withRegion(properties.region().value())
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }
}
