package com.zerobase.fastlms.admin.banner.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "banner")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name; // 배너명
    private String imagePath; // 이미지 경로
    private String altText; // 대체 텍스트
    private String url; // 링크 URL
    private String target; // "_blank" 또는 "_self"

    private int sortOrder; // 정렬 순서

    private boolean visible; // 공개 여부
    private LocalDateTime regDate; // 등록일
}
