package com.example.usedTrade.member.controller;

import com.example.usedTrade.member.error.ServiceResult;
import com.example.usedTrade.UsedTradeApplication;
import com.example.usedTrade.member.dto.MemberDto;
import com.example.usedTrade.member.model.*;
import com.example.usedTrade.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private static final Logger logger =
            LoggerFactory.getLogger(UsedTradeApplication.class);

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/member/login")
    public String login() {
        return "member/login";
    }

    @GetMapping("/member/register")
    public String register(Model model) {

        logger.info("### MemberController Get member/register ###");
        model.addAttribute("memberInput", new MemberInput());

        return "member/register";
    }

    @PostMapping("/member/register")
    public String registerSubmit(Model model,
                                 @Valid MemberInput memberInput,
                                 BindingResult bindingResult) {

        logger.info("### Register Member Input: " + memberInput);

        if (bindingResult.hasErrors()) {
            return "member/register";
        }

        try {
            ServiceResult result = memberService.register(memberInput);
            model.addAttribute("result", result.isResult());
            if (!result.isResult()) {
                model.addAttribute("error", result.getErrorCode().getDescription());
            }
        } catch (IllegalStateException e) {
            model.addAttribute("message", e.getMessage());
            return "member/register";
        }

        return "member/register_complete";
    }

    @GetMapping("/member/email-auth")
    public String emailAuth(Model model, String id) {
        logger.info("### emailAuthUuid: " + id);

        ServiceResult result = memberService.emailAuth(id);
        model.addAttribute("result", result.isResult());
        if (!result.isResult()) {
            model.addAttribute("message", result.getErrorCode().getDescription());
        }

        return "member/email_auth";
    }

    @GetMapping("/member/myPage")
    public String myPage(Model model, Principal principal) {

        MemberDto memberDto = memberService.getInfo(principal.getName());
        logger.info("### MemberDto : " + memberDto);
        model.addAttribute("memberDto", memberDto);
        model.addAttribute("changeMemberInput", new ChangeMemberInput());

        return "member/myPage";
    }

    @PostMapping("/member/update")
    public String update(Model model, @Valid ChangeMemberInput changeMemberInput,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        logger.info("### " + changeMemberInput);

        if (bindingResult.hasErrors()) {
            MemberDto memberDto = memberService.getInfo(changeMemberInput.getEmail());
            model.addAttribute("memberDto", memberDto);
            logger.info("### MemberDto: " + memberDto);
            return "member/myPage";
        }

        try {
            ServiceResult result = memberService.update(changeMemberInput);
            redirectAttributes.addFlashAttribute("result", result.isResult());
            if (!result.isResult()) {
                redirectAttributes.addFlashAttribute("error", result.getErrorCode().getDescription());
            }
        } catch (IllegalStateException e) {
            model.addAttribute("message", e.getMessage());
            return "member/myPage";
        }

        return "redirect:/member/myPage";
    }

    @GetMapping("/member/change/password")
    public String changePassword() {

        return "member/change_password";
    }

    @PostMapping("/member/change/password")
    public String changePasswordSubmit(ChangePasswordInput passwordInput,
                                       Principal principal,
                                       RedirectAttributes redirectAttributes) {

        logger.info("### ChangePasswordInput: " + passwordInput);
        ServiceResult result = memberService.changePassword(principal.getName(), passwordInput);

        redirectAttributes.addFlashAttribute("changeResult", result.isResult());

        return "redirect:/member/change/password";
    }


    @GetMapping("/member/reset/password")
    public String resetPassword(Model model) {

        model.addAttribute("resetPasswordInput", new ResetPasswordInput());

        return "member/reset_password";
    }

    @PostMapping("/member/reset/password")
    public String resetPasswordSubmit(Model model, @Valid ResetPasswordInput passwordInput,
                                      BindingResult bindingResult,
                                      RedirectAttributes redirectAttributes) {
        logger.info("### ResetPasswordInput: " + passwordInput);

        if (bindingResult.hasErrors()) {
            return "member/reset_password";
        }

        try {
            ServiceResult result = memberService.sendResetPasswordEmail(passwordInput);
            redirectAttributes.addFlashAttribute("result", result.isResult());
            if (!result.isResult()) {
                redirectAttributes.addFlashAttribute("error", result.getErrorCode().getDescription());
            }
        } catch (IllegalStateException e) {
            model.addAttribute("message", e.getMessage());
            return "member/reset_password";
        }

        return "redirect:/member/reset/password";
    }

    @GetMapping("/member/reset/passwordMail")
    public String resetPasswordMail(Model model, String resetPasswordKey) {
        logger.info("### resetPasswordKey: " + resetPasswordKey);

        ServiceResult result = memberService.checkResetPasswordKey(resetPasswordKey);

        model.addAttribute("resetPasswordFormInput", new ResetPasswordFormInput());
        model.addAttribute("resetPasswordKey", resetPasswordKey);
        model.addAttribute("result", result.isResult());
        if (!result.isResult()) {
            model.addAttribute("error", result.getErrorCode().getDescription());
        }

        return "member/reset_password_form";
    }

    @PostMapping("/member/reset/passwordMail")
    public String resetPasswordMailSubmit(Model model,
                                          @Valid ResetPasswordFormInput resetPasswordFormInput,
                                          BindingResult bindingResult) {
        logger.info("### ResetPasswordFormInput: " + resetPasswordFormInput);

        if (bindingResult.hasErrors()) {
            model.addAttribute("result", true);
            return "member/reset_password_form";
        }

        try{
            ServiceResult result = memberService.resetPassword(resetPasswordFormInput);
            model.addAttribute("result", result.isResult());
            if (!result.isResult()) {
                model.addAttribute("error", result.getErrorCode().getDescription());
            }
        } catch (IllegalStateException e) {
            model.addAttribute("message", e.getMessage());
            return "member/reset_password_form";
        }

        return "member/reset_password_result";
    }

    @GetMapping("/member/withdraw")
    public String withdraw() {
        return "member/withdraw";
    }

    @PostMapping("/member/withdraw")
    public String withdrawSubmit(Model model, Principal principal, String password) {

        logger.info("### withdraw password: " + password);

        ServiceResult result = memberService.withdraw(principal.getName(), password);
        model.addAttribute("result", result.isResult());
        if (!result.isResult()) {
            model.addAttribute("error", result.getErrorCode().getDescription());
            return "member/withdraw";
        }

        return "/index";
    }
}
