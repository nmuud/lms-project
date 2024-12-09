package com.zerobase.fastlms.log.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "login_history")
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(length = 45)
    private String accessIp;

    @Column(length = 512)
    private String accessUserAgent;

    @Column(nullable = false)
    private LocalDateTime loginDt;

}
