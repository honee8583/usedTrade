package com.example.usedTrade.main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/error/denied")
    public String error() {
        return "error/denied";
    }
}
