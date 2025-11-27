package konkuk.dreamweaver.global.external.openai.errorcode;

import konkuk.dreamweaver.global.exception.errorcode.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
public enum OpenAiErrorCode implements ErrorCode {

    INVALID_OPENAI_RESPONSE(HttpStatus.BAD_GATEWAY.value(), "OpenAI 응답이 올바르지 않습니다."),
    TEXT_GENERATION_FAILED(HttpStatus.BAD_GATEWAY.value(), "텍스트 생성 중 오류가 발생했습니다."),
    IMAGE_GENERATION_FAILED(HttpStatus.BAD_GATEWAY.value(), "이미지 생성 중 오류가 발생했습니다."),
    EMPTY_IMAGE_RESPONSE(HttpStatus.BAD_GATEWAY.value(), "OpenAI가 유효한 이미지 데이터를 반환하지 않았습니다.")
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
