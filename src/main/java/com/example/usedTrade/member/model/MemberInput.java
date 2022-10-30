package com.example.usedTrade.member.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MemberInput {
    @NotEmpty(message = "* 이메일은 필수 입력값입니다.")
    @Email(message = "* 이메일 형식으로 입력해주세요.")
    private String email;

    @NotEmpty(message = "* 비밀번호는 필수 입력값입니다.")
    @Length(min = 8, max = 16, message = "* 비밀번호는 8자 이상, 16자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "* 이름은 필수 입력값입니다.")
    private String name;

    @NotBlank(message = "* 전화번호는 필수 입력값입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$",
            message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phone;

    @NotBlank(message = "* 주소는 필수 입력값입니다.")
    private String address;

    @NotBlank(message = "* 상세주소는 필수 입력값입니다.")
    private String addressDetail;
}
