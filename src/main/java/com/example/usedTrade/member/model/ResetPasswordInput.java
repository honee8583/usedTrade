package com.example.usedTrade.member.model;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ResetPasswordInput {
    @Email(message = "* 이메일 형식으로 입력해주세요.")
    @NotEmpty(message = "* 이메일은 필수 입력값입니다.")
    private String email;

    @NotBlank(message = "* 이름은 필수 입력값입니다.")
    private String name;
}
