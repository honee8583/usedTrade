package com.example.usedTrade.member.model;

import lombok.*;

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
public class ChangeMemberInput {
    private String email;

    @NotBlank(message = "* 이름은 필수입력값 입니다.")
    private String name;

    @NotBlank(message = "* 주소는 필수입력값 입니다.")
    private String address;

    @NotBlank(message = "* 상세주소는 필수입력값 입니다.")
    private String addressDetail;

    @NotBlank(message = "* 전화번호는 필수입력값 입니다.")
    @Pattern(regexp = "^01(?:0|1|[6-9])[.-]?(\\d{3}|\\d{4})[.-]?(\\d{4})$", message = "10 ~ 11 자리의 숫자만 입력 가능합니다.")
    private String phone;
}
