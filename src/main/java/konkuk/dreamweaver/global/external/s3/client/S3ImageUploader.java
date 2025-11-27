package konkuk.dreamweaver.global.external.s3.client;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import konkuk.dreamweaver.global.exception.CustomException;
import konkuk.dreamweaver.global.external.s3.errorcode.S3ErrorCode;
import konkuk.dreamweaver.global.properties.S3Properties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3ImageUploader {

    private final S3Properties properties;

    private final AmazonS3 amazonS3;

    public String uploadBase64Image(String base64) {
        try {
            String pureBase64 = extractPureBase64(base64);
            byte[] bytes = Base64.getDecoder().decode(pureBase64);

            String key = "dream-images/" + UUID.randomUUID() + ".png";

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/png");
            metadata.setContentLength(bytes.length);

            amazonS3.putObject(properties.s3().bucket(), key, new ByteArrayInputStream(bytes), metadata);

            return properties.s3().baseUrl() + "/" + key;

        } catch (IllegalArgumentException e) {
            throw new CustomException(S3ErrorCode.INVALID_BASE64_DATA);
        } catch (Exception e) {
            throw new CustomException(S3ErrorCode.S3_UPLOAD_FAILED);
        }
    }

    private String extractPureBase64(String dataUrlOrBase64) {
        int commaIndex = dataUrlOrBase64.indexOf(',');
        if (commaIndex != -1) {
            return dataUrlOrBase64.substring(commaIndex + 1);
        }
        return dataUrlOrBase64;
    }
}
