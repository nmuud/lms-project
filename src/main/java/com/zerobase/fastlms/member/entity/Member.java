package com.zerobase.fastlms.member.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Member implements MemberCode {
    
    @Id
    private String userId;
    private String userName;
    private String phone;
    private String password;
    private LocalDateTime regDt;
    private LocalDateTime udtDt;//회원정보 수정일
    
    private boolean emailAuthYn; // 이메일 인증 했는지 체크
    private LocalDateTime emailAuthDt; // 이메일 인증한 날짜
    private String emailAuthKey; // 이메일 인증키
    
    private String resetPasswordKey; // 키일치하면 패스워드 바꿀수있음
    private LocalDateTime resetPasswordLimitDt; // 패스워드리셋시간 제한
    
    private boolean adminYn;
    
    private String userStatus;//이용가능한상태, 정지상태


    private String zipcode;
    private String addr;
    private String addrDetail;
    
}
