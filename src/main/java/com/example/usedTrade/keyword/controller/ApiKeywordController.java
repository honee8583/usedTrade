package com.example.usedTrade.keyword.controller;

import com.example.usedTrade.keyword.model.KeywordDto;
import com.example.usedTrade.keyword.model.KeywordInput;
import com.example.usedTrade.keyword.service.KeywordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/keyword")
@RequiredArgsConstructor
public class ApiKeywordController {

    private final KeywordService keywordService;

    @PostMapping
    public ResponseEntity<?> createKeyword(@RequestBody @Valid KeywordInput keywordInput) {
        log.info("create keyword: " + keywordInput);

        keywordService.create(keywordInput);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KeywordDto> getKeyword(@PathVariable long id) {
        log.info("get keyword: " + id);

        KeywordDto keywordDto = keywordService.get(id);

        return new ResponseEntity<>(keywordDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<KeywordDto>> getKeywordList() {
        log.info("get keyword list");

        List<KeywordDto> keywordDtoList = keywordService.getKeywordList();

        return new ResponseEntity<>(keywordDtoList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateKeyword(@PathVariable long id,
                                           @RequestBody @Valid KeywordInput keywordInput) {
        log.info("update keyword: " + id);

        keywordService.modify(id, keywordInput);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKeyword(@PathVariable long id) {
        log.info("delete keyword: " + id);

        keywordService.delete(id);

        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
