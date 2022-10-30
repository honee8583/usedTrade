package com.example.usedTrade.member.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResetPasswordFormInput {
    @NotBlank(message = "* 비밀번호는 필수 입력값입니다.")
    @Length(min = 8, max = 16, message = "* 비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    private String newPassword;

    @NotEmpty(message = "* key값이 존재해야 합니다.")
    private String resetPasswordKey;
}
