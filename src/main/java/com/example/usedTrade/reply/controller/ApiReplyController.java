package com.example.usedTrade.reply.controller;

import com.example.usedTrade.reply.model.ReplyDto;
import com.example.usedTrade.reply.model.ReplyInput;
import com.example.usedTrade.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ApiReplyController {

    private final ReplyService replyService;

    /**
     * 게시글의 댓글 작성
     */
    @PostMapping
    public ResponseEntity<Boolean> writeReply(@RequestBody ReplyInput replyInput,
                                              Principal principal) {
        log.info("writeReply -> " + replyInput);

        replyInput.setEmail(principal.getName());
        replyService.write(replyInput);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * 해당 게시글의 전체 댓글 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<List<ReplyDto>> getReplyList(@PathVariable("id") long tradeId) {
        log.info("getReplyList -> " + tradeId);

        List<ReplyDto> replyDtoList = replyService.getReplyList(tradeId);

        return new ResponseEntity<>(replyDtoList, HttpStatus.OK);
    }

    /**
     * 자신의 댓글 수정
     */
    @PostMapping("/{id}")
    public ResponseEntity<Boolean> modifyReply(@PathVariable("id") long replyId,
                                               @RequestBody ReplyInput modifyInput) {
        modifyInput.setReply_id(replyId);

        log.info("modifyReply -> " + modifyInput);

        replyService.modify(modifyInput);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    /**
     * 자신의 댓글 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteReply(@PathVariable("id") long replyId,
                                               Principal principal) {
        log.info("deleteReply -> " + replyId);

        replyService.remove(replyId, principal.getName());

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
