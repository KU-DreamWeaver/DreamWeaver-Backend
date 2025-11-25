package konkuk.dreamweaver.domain.dreamhistory.service;

import konkuk.dreamweaver.domain.dreamhistory.dto.request.DreamHistoryRequest;
import konkuk.dreamweaver.domain.dreamhistory.dto.response.DreamHistoryResponse;
import konkuk.dreamweaver.domain.dreamhistory.repository.DreamHistoryRepository;
import konkuk.dreamweaver.global.external.openai.client.OpenAiClient;
import konkuk.dreamweaver.global.external.openai.constant.OpenAiPrompt;
import konkuk.dreamweaver.global.external.openai.dto.request.ChatRequestMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DreamHistoryService {

    private final DreamHistoryRepository dreamHistoryRepository;

    private final OpenAiClient openAiClient;

    @Transactional
    public DreamHistoryResponse createDreamHistory(List<String> keywords, String description, String emotion) {

        String dreamDescription = openAiClient.sendTextRequest(List.of(
                new ChatRequestMessage("system", OpenAiPrompt.TEXT_SYSTEM_PROMPT),
                new ChatRequestMessage("user", String.format(OpenAiPrompt.TEXT_USER_PROMPT,
                        keywords, description, emotion
                ))
        ));

        String imageUrl = openAiClient.sendImageReqeust(String.format(OpenAiPrompt.IMAGE_PROMPT, dreamDescription));

        return DreamHistoryResponse.of(dreamDescription, imageUrl);

    }
}
