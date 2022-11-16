package com.example.usedTrade.trade.service.impl;

import com.example.usedTrade.error.keyword.exception.KeywordNotFoundException;
import com.example.usedTrade.error.trade.exception.TradeNotFoundException;
import com.example.usedTrade.keyword.entity.Keyword;
import com.example.usedTrade.keyword.repository.KeywordRepository;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.page.PageRequestDTO;
import com.example.usedTrade.page.PageResultDTO;
import com.example.usedTrade.trade.entity.QTrade;
import com.example.usedTrade.trade.entity.Trade;
import com.example.usedTrade.trade.entity.TradeImage;
import com.example.usedTrade.trade.entity.TradeStatus;
import com.example.usedTrade.trade.model.TradeDto;
import com.example.usedTrade.trade.model.TradeImgDto;
import com.example.usedTrade.trade.repository.TradeImageRepository;
import com.example.usedTrade.trade.repository.TradeRepository;
import com.example.usedTrade.trade.service.TradeImageService;
import com.example.usedTrade.trade.service.TradeService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final KeywordRepository keywordRepository;

    private final TradeImageService tradeImageService;
    private final TradeImageRepository tradeImageRepository;

    @Override
    public void register(TradeDto tradeDto, List<MultipartFile> multipartFileList) throws Exception {
        Trade trade = Trade.builder()
                .member(Member.builder().email(tradeDto.getEmail()).build())
                .title(tradeDto.getTitle())
                .content(tradeDto.getContent())
                .price(tradeDto.getPrice())
                .tradeStatus(TradeStatus.valueOf(tradeDto.getTradeStatus()))
                .regDt(LocalDateTime.now())
                .build();

        for (String s : tradeDto.getKeywordList()) {
            Keyword keyword = keywordRepository.findByKeywordName(s)
                    .orElseThrow(KeywordNotFoundException::new);
            trade.addKeyword(s);
        }

        tradeRepository.save(trade);

        for (int i = 0; i < multipartFileList.size(); i++) {
            if (StringUtils.hasText(multipartFileList.get(i).getOriginalFilename())) {
                TradeImage tradeImage = TradeImage.builder()
                        .trade(trade)
                        .build();
                tradeImageService.saveTradeImage(tradeImage, multipartFileList.get(i));
            }
        }
    }

    @Override
    public void modify(TradeDto tradeDto, List<MultipartFile> multipartFileList) throws Exception {
        Trade trade = findTrade(tradeDto.getId());
        trade.modifyTrade(tradeDto);
        tradeRepository.save(trade);

        List<Long> tradeImageIds = tradeDto.getTradeImgIds();
        for (int i = 0;i< multipartFileList.size(); i++) {
            tradeImageService.updateTradeImage(tradeImageIds.get(i), multipartFileList.get(i));
        }
    }

    @Override
    public void delete(long tradeId) {
        // 거래글 삭제
        findTrade(tradeId);
        tradeRepository.deleteById(tradeId);

        // 이미지 삭제
        List<TradeImage> tradeImageList =
                tradeImageRepository.findByTradeIdOrderByIdAsc(tradeId);

        for (TradeImage tradeImage : tradeImageList) {
            tradeImageRepository.deleteById(tradeImage.getId());
        }
    }

    @Override
    @Transactional(readOnly = true)
    public TradeDto getTrade(long tradeId) {
        List<TradeImage> tradeImageList =
                tradeImageRepository.findByTradeIdOrderByIdAsc(tradeId);

        List<TradeImgDto> tradeImgDtoList = tradeImageList.stream()
                .map(TradeImgDto::entityToDto).collect(Collectors.toList());

        Trade trade = findTrade(tradeId);

        TradeDto tradeDto = TradeDto.entityToDto(trade);
        tradeDto.setTradeImgDtoList(tradeImgDtoList);

        return tradeDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PageResultDTO<TradeDto, Trade> getTradeList(PageRequestDTO pageRequestDTO) {
        Pageable pageable =
                pageRequestDTO.getPageable(Sort.by("regDt").descending());

        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);

        Page<Trade> tradeList = tradeRepository.findAll(booleanBuilder, pageable);

        Function<Trade, TradeDto> fn = TradeDto::entityToDto;

        int totalTradeCount = (int) tradeRepository.countAll();

        PageResultDTO<TradeDto, Trade> pageResultDTO
                = new PageResultDTO<>(tradeList, fn);

        return pageResultDTO;
    }

    private Trade findTrade(long tradeId) {
        return tradeRepository.findById(tradeId)
                .orElseThrow(TradeNotFoundException::new);
    }

    private BooleanBuilder getSearch(PageRequestDTO pageRequestDTO) {
        String type = pageRequestDTO.getType();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QTrade qTrade = QTrade.trade;

        String keyword = pageRequestDTO.getKeyword();;

        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }

        if (type.contains("t")) {   // title
            booleanBuilder.or(qTrade.title.contains(keyword));
        }
        if (type.contains("c")) {   // content
            booleanBuilder.or(qTrade.content.contains(keyword));
        }
        if (type.contains("e")) {   // email
            booleanBuilder.or(qTrade.member.email.contains(keyword));
        }
        if (type.contains("k")) {   // keyword
            booleanBuilder.or(qTrade.keywordList.contains(keyword));
        }

        return booleanBuilder;
    }
}
