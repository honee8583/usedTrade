package com.example.usedTrade.page;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO<DTO, EN> {

    private List<DTO> dtoList;

    private int totalPage;  // page 객체에서 추출 가능
    private int page;       // page 객체의 pageable객체에서 추출 가능
    private int size;       // page 객체의 pageable객체에서 추출 가능
    private int start, end;
    private boolean prev, next;
    private List<Integer> pageList;

    //result : service에서 가져온 Page데이터, fn : 가져온 page 데이터를 가공할 Function
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() + 1;
        this.size = pageable.getPageSize();

        int tempEnd = (int)(Math.ceil(page/10.0)) * 10; // 소수값 올림

        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end)
                .boxed().collect(Collectors.toList());
    }
}
