package konkuk.dreamweaver.domain.dreamhistory.service;

import konkuk.dreamweaver.domain.dreamhistory.dto.response.DreamHistoryResponse;
import konkuk.dreamweaver.domain.dreamhistory.dto.response.DreamListResponse;
import konkuk.dreamweaver.domain.dreamhistory.entity.DreamHistory;
import konkuk.dreamweaver.domain.dreamhistory.repository.DreamHistoryRepository;
import konkuk.dreamweaver.domain.user.entity.User;
import konkuk.dreamweaver.domain.user.entity.repository.UserRepository;
import konkuk.dreamweaver.domain.user.errorcode.UserErrorCode;
import konkuk.dreamweaver.global.exception.CustomException;
import konkuk.dreamweaver.global.external.openai.client.OpenAiClient;
import konkuk.dreamweaver.global.external.openai.constant.OpenAiPrompt;
import konkuk.dreamweaver.global.external.openai.dto.request.ChatRequestMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static konkuk.dreamweaver.domain.user.errorcode.UserErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DreamHistoryService {

    private final DreamHistoryRepository dreamHistoryRepository;

    private final UserRepository userRepository;

    private final OpenAiClient openAiClient;

    @Transactional
    public DreamHistoryResponse createDreamHistory(List<String> keywords, String description, String emotion, Long userId) {

        String dreamDescription = openAiClient.sendTextRequest(List.of(
                new ChatRequestMessage("system", OpenAiPrompt.TEXT_SYSTEM_PROMPT),
                new ChatRequestMessage("user", String.format(OpenAiPrompt.TEXT_USER_PROMPT,
                        keywords, description, emotion
                ))
        ));

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        String imageUrl = openAiClient.sendImageRequest(String.format(OpenAiPrompt.IMAGE_PROMPT, dreamDescription));

        DreamHistory dreamHistory = DreamHistory.create(dreamDescription, imageUrl, user);
        dreamHistoryRepository.save(dreamHistory);

        return DreamHistoryResponse.from(dreamHistory);

    }

    public List<DreamListResponse> getDreamHistoriesByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new CustomException(USER_NOT_FOUND);
        }

        List<DreamHistory> histories =
                dreamHistoryRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        return histories.stream()
                .map(DreamListResponse::from)
                .toList();
    }
}
