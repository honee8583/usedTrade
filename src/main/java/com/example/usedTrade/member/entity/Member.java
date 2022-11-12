package com.example.usedTrade.member.entity;

import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
public class Member implements MemberStatus{

    @Id
    private String email;
    private String password;
    private String name;
    private String address;
    private String address_detail;
    private String phone;
    private int trade_num;
    private String status;
    private boolean fromSocial;
    private LocalDateTime regDt;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<String> roles = new HashSet<>();

    // Email Auth
    private boolean emailAuthYn;
    private String emailAuthKey;
    private LocalDateTime emailAuthDt;

    // Reset Password
    private String resetPasswordKey;
    private LocalDateTime resetPasswordLimitDt;

    // email auth
    public void changeEmailAuth(String memberStatus, boolean emailAuthYn, LocalDateTime emailAuthDt) {
        this.status = memberStatus;
        this.emailAuthYn = emailAuthYn;
        this.emailAuthDt = emailAuthDt;
    }

    // update member info
    public void changeMemberInfo(String name, String address, String address_detail, String phone) {
        this.name = name;
        this.address = address;
        this.address_detail = address_detail;
        this.phone = phone;
    }

    // change password
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    // set reset password info
    public void changeResetPasswordKey(String resetPasswordKey, LocalDateTime resetPasswordLimitDt) {
        this.resetPasswordKey = resetPasswordKey;
        this.resetPasswordLimitDt = resetPasswordLimitDt;
    }

    // reset password
    public void resetPassword(String newPassword) {
        this.changePassword(newPassword);
        this.resetPasswordKey = "";
        this.resetPasswordLimitDt = null;
    }

    public void withdrawMember() {
        this.password = "";
        this.name = "withdrawed member";
        this.address = "";
        this.address_detail = "";
        this.phone = "";
        this.trade_num = 0;
        this.regDt = null;
        this.status = MEMBER_STATUS_WITHDRAW;
        this.emailAuthYn = false;
        this.emailAuthDt = null;
        this.emailAuthKey = "";
        this.resetPasswordKey = "";
        this.resetPasswordLimitDt = null;
    }

    public void addMemberRole(MemberRole memberRole) {
        roles.add(String.valueOf(memberRole));
    }
}