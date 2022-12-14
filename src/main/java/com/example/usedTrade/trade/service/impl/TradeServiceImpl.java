package com.example.usedTrade.trade.service.impl;

import com.example.usedTrade.error.keyword.exception.KeywordNotFoundException;
import com.example.usedTrade.error.trade.exception.TradeNotFoundException;
import com.example.usedTrade.keyword.entity.Keyword;
import com.example.usedTrade.keyword.repository.KeywordRepository;
import com.example.usedTrade.member.entity.Member;
import com.example.usedTrade.page.PageRequestDTO;
import com.example.usedTrade.page.PageResultDTO;
import com.example.usedTrade.reply.entity.Reply;
import com.example.usedTrade.reply.repository.ReplyRepository;
import com.example.usedTrade.trade.entity.*;
import com.example.usedTrade.trade.model.InterestDto;
import com.example.usedTrade.trade.model.TradeDto;
import com.example.usedTrade.trade.model.TradeImgDto;
import com.example.usedTrade.trade.repository.InterestRepository;
import com.example.usedTrade.trade.repository.TradeImageRepository;
import com.example.usedTrade.trade.repository.TradeRepository;
import com.example.usedTrade.trade.service.TradeImageService;
import com.example.usedTrade.trade.service.TradeService;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final ReplyRepository replyRepository;
    private final InterestRepository interestRepository;

    private final TradeImageService tradeImageService;
    private final TradeImageRepository tradeImageRepository;

    @Override
    @Transactional
    public void register(TradeDto tradeDto, List<MultipartFile> multipartFileList) throws Exception {
        // ????????? ??????
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

        // ????????? ????????? ??????
        for (int i = 0; i < multipartFileList.size(); i++) {
            TradeImage tradeImage = TradeImage.builder()
                                    .trade(trade)
                                    .build();
            tradeImageService.saveTradeImage(tradeImage, multipartFileList.get(i));
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

        Sort sort = getSort(pageRequestDTO.getOrder());

        Pageable pageable = pageRequestDTO.getPageable(sort);

        BooleanBuilder booleanBuilder = getSearch(pageRequestDTO);

        Page<Trade> tradeList = tradeRepository.findAll(booleanBuilder, pageable);

        Function<Trade, TradeDto> fn = TradeDto::entityToDto;

        PageResultDTO<TradeDto, Trade> pageResultDTO
                = new PageResultDTO<>(tradeList, fn);

        return pageResultDTO;
    }

    @Override
    @Transactional
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
    @Transactional
    public void delete(long tradeId) {
        // ????????? ??????
        List<TradeImage> tradeImageList =
                tradeImageRepository.findByTradeIdOrderByIdAsc(tradeId);

        for (TradeImage tradeImage : tradeImageList) {
            tradeImageRepository.deleteById(tradeImage.getId());
        }

        // ????????? ??????
        findTrade(tradeId);
        tradeRepository.deleteById(tradeId);
    }

    @Override
    public PageResultDTO getMyInterestTradeList(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by("trade_id").descending());

        Page<Interest> pageList = interestRepository.findAllByMember(
                Member.builder().email(pageRequestDTO.getKeyword()).build(), pageable);

        Function<Interest, InterestDto> fn = InterestDto::entityToDto;

        PageResultDTO<InterestDto, Interest> pageResultDTO
                = new PageResultDTO<>(pageList, fn);

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

    private Sort getSort(String order) {
        if (order == null || order == "" || order.trim().length() == 0) {
            return Sort.by("regDt").descending();
        } else if (PriceOrder.valueOf(order) == PriceOrder.DESC) {
            return Sort.by("price").descending();
        } else if (PriceOrder.valueOf(order) == PriceOrder.ASC) {
            return Sort.by("price").ascending();
        }
        return null;
    }
}
