package com.green.greengram.feed;

import com.green.greengram.common.model.ResultResponse;
import com.green.greengram.feed.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("feed")
@Tag(name = "2. 피드", description = "피드 관리")
public class FeedController {
    private final FeedService service;

    @PostMapping
    @Operation(summary = "피드 등록", description = "필수: 사진리스트 || 옵션: 위치, 내용")
    public ResultResponse<FeedPostRes> postFeed(@RequestPart List<MultipartFile> pics
                                              , @Valid @RequestPart FeedPostReq p) {
        FeedPostRes res = service.postFeed(pics, p);
        return ResultResponse.<FeedPostRes>builder()
                .resultMessage("피드 등록 완료")
                .resultData(res)
                .build();
    }

    @GetMapping
    @Operation(summary = "Feed 리스트 - N+1", description = "signed_user_id는 로그인한 사용자의 pk")
    public ResultResponse<List<FeedGetRes>> getFeedList(@Valid @ParameterObject @ModelAttribute FeedGetReq p) {
        log.info("FeedController > getFeedList > p: {}", p);
        List<FeedGetRes> list = service.getFeedList(p);
        return ResultResponse.<List<FeedGetRes>>builder()
                .resultMessage(String.format("%d rows", list.size()))
                .resultData(list)
                .build();
    }

    @GetMapping("ver3")
    @Operation(summary = "Feed 리스트 - No N+1", description = "signed_user_id는 로그인한 사용자의 pk")
    public ResultResponse<List<FeedGetRes>> getFeedListVer3(@Valid @ParameterObject @ModelAttribute FeedGetReq p) {
        log.info("FeedController > getFeedList > p: {}", p);
        List<FeedGetRes> list = service.getFeedList3(p);
        return ResultResponse.<List<FeedGetRes>>builder()
                .resultMessage(String.format("%d rows", list.size()))
                .resultData(list)
                .build();
    }

    @DeleteMapping
    @Operation(summary = "Feed 삭제", description = "피드의 댓글, 좋아요 모두 삭제 처리")
    public ResultResponse<Integer> deleteFeed(@ParameterObject @ModelAttribute FeedDeleteReq p) {
        log.info("FeedController > deleteFeed > p: {}", p);
        int result = service.deleteFeed(p);
        return ResultResponse.<Integer>builder()
                .resultMessage("피드가 삭제되었습니다.")
                .resultData(result)
                .build();
    }

    @GetMapping("ver4")
    @Operation(summary = "Feed 리스트 - No N+1", description = "signed_user_id는 로그인한 사용자의 pk")
    public ResultResponse<List<FeedGetRes>> getFeedList2(@Valid @ParameterObject @ModelAttribute FeedGetReq p) {
        List<FeedGetRes> list = service.getFeedList2(p);
        return ResultResponse.<List<FeedGetRes>>builder()
                .resultMessage(String.format("%d rows", list.size()))
                .resultData(list)
                .build();
    }

    @GetMapping("ver5") // n+1 을 해결하기 위해 원래는 service 에서 for 문 돌리는걸, mybatis 에서 해결하는ㄱ 서
    @Operation(summary = "Feed 리스트 - No N+1-using MyBatis", description = "signed_user_id는 로그인한 사용자의 pk")
    public ResultResponse<List<FeedGetRes>> getFeedList5(@Valid @ParameterObject @ModelAttribute FeedGetReq p) {
        List<FeedGetRes> list = service.getFeedList5(p);
        return ResultResponse.<List<FeedGetRes>>builder()
                .resultMessage(String.format("%d rows", list.size()))
                .resultData(list)
                .build();
    }


}
