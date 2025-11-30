package konkuk.dreamweaver.domain.dreamhistory.controller;

import konkuk.dreamweaver.domain.dreamhistory.dto.request.DreamHistoryRequest;
import konkuk.dreamweaver.domain.dreamhistory.dto.response.DreamHistoryResponse;
import konkuk.dreamweaver.domain.dreamhistory.dto.response.DreamListResponse;
import konkuk.dreamweaver.domain.dreamhistory.service.DreamHistoryService;
import konkuk.dreamweaver.global.dto.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("dreamhistories")
@RequiredArgsConstructor
public class DreamHistoryController {

    private final DreamHistoryService dreamHistoryService;

    @PostMapping
    public BaseResponse<DreamHistoryResponse> createDreamHistory(@RequestBody DreamHistoryRequest req) {
        return BaseResponse.ok(dreamHistoryService.createDreamHistory(req.keywords(),req.description(), req.emotion(), req.userId()),"해몽 요약 생성 성공");
    }

    @GetMapping("/{userId}")
    public BaseResponse<List<DreamListResponse>> getUserDreamHistories(
            @PathVariable Long userId
    ) {
        return BaseResponse.ok(dreamHistoryService.getDreamHistoriesByUser(userId), "꿈 조회 성공");
    }
}