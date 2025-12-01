package konkuk.dreamweaver.domain.dreamhistory.service;

import konkuk.dreamweaver.domain.dreamhistory.dto.response.DreamHistoryResponse;
import konkuk.dreamweaver.domain.dreamhistory.dto.response.DreamListResponse;
import konkuk.dreamweaver.domain.dreamhistory.entity.DreamHistory;
import konkuk.dreamweaver.domain.dreamhistory.entity.DreamHistoryKeyword;
import konkuk.dreamweaver.domain.dreamhistory.repository.DreamHistoryKeywordRepository;
import konkuk.dreamweaver.domain.dreamhistory.repository.DreamHistoryRepository;
import konkuk.dreamweaver.domain.user.entity.User;
import konkuk.dreamweaver.domain.user.entity.repository.UserRepository;
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

    private final DreamHistoryKeywordRepository dreamHistoryKeywordRepository;

    private final UserRepository userRepository;

    private final OpenAiClient openAiClient;

    @Transactional
    public DreamHistoryResponse createDreamHistory(List<String> keywords, String description, String emotion, Long userId) {

        String aiSummary = openAiClient.sendTextRequest(List.of(
                new ChatRequestMessage("system", OpenAiPrompt.TEXT_SYSTEM_PROMPT),
                new ChatRequestMessage("user", String.format(OpenAiPrompt.TEXT_USER_PROMPT,
                        keywords, description, emotion
                ))
        ));

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        String imageUrl = openAiClient.sendImageRequest(String.format(OpenAiPrompt.IMAGE_PROMPT, aiSummary));

        DreamHistory dreamHistory = DreamHistory.create(emotion, aiSummary, description, imageUrl, user);
        dreamHistoryRepository.save(dreamHistory);

        List<DreamHistoryKeyword> keywordEntities = keywords.stream()
                .map(keyword -> DreamHistoryKeyword.create(keyword, dreamHistory))
                .toList();

        dreamHistoryKeywordRepository.saveAll(keywordEntities);

        return DreamHistoryResponse.from(dreamHistory, keywords);

    }

    public List<DreamListResponse> getDreamHistoriesByUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new CustomException(USER_NOT_FOUND);
        }

        List<DreamHistory> histories =
                dreamHistoryRepository.findAllByUserIdOrderByCreatedAtDesc(userId);

        return histories.stream()
                .map(history -> {
                    List<String> keywords = dreamHistoryKeywordRepository
                            .findAllByDreamHistory(history)
                            .stream()
                            .map(DreamHistoryKeyword::getKeyword)
                            .toList();

                    return DreamListResponse.of(
                            history.getId(),
                            history.getUser().getId(),
                            keywords,
                            history.getAiSummary(),
                            history.getDescription(),
                            history.getEmotion(),
                            history.getImageUrl(),
                            history.getCreatedAt()
                    );
                })
                .toList();
    }
}
