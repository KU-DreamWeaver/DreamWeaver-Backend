package konkuk.dreamweaver.domain.dreamhistory.controller;

import konkuk.dreamweaver.domain.dreamhistory.dto.request.DreamHistoryRequest;
import konkuk.dreamweaver.domain.dreamhistory.dto.response.DreamHistoryResponse;
import konkuk.dreamweaver.domain.dreamhistory.service.DreamHistoryService;
import konkuk.dreamweaver.global.dto.response.BaseResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("dreamhistories")
@RequiredArgsConstructor
public class DreamHistoryController {

    private final DreamHistoryService dreamHistoryService;

    @PostMapping
    public BaseResponse<DreamHistoryResponse> createDreamHistory(@RequestBody DreamHistoryRequest req) {
        return BaseResponse.ok(dreamHistoryService.createDreamHistory(req.keywords(),req.description(), req.emotion(), req.userId()),"해몽 요약 생성 성공");
    }
}
