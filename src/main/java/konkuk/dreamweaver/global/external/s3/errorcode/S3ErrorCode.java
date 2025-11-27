package konkuk.dreamweaver.global.external.s3.errorcode;

import konkuk.dreamweaver.global.exception.errorcode.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum S3ErrorCode implements ErrorCode {
    INVALID_BASE64_DATA(HttpStatus.BAD_REQUEST.value(), "유효하지 않은 이미지 데이터입니다."),
    S3_UPLOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR.value(), "이미지 업로드에 실패했습니다."),
    INVALID_S3_BUCKET(HttpStatus.INTERNAL_SERVER_ERROR.value(), "S3 버킷 설정이 잘못되었습니다.");

    ;

    private final int httpStatus;
    private final String message;

    @Override
    public int getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
