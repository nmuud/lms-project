package com.zerobase.fastlms.admin.banner.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BannerDto {
    private Long id;
    private String name;
    private String altText;
    private String url;
    private String target;
    private int sortOrder;
    private boolean visible;
    private String imagePath;
    private String regDate;

    public BannerDto(Long id, String name, String altText, String url,
                     String target, int sortOrder, boolean visible,
                     String imagePath, String regDate) {
        this.id = id;
        this.name = name;
        this.altText = altText;
        this.url = url;
        this.target = target;
        this.sortOrder = sortOrder;
        this.visible = visible;
        this.imagePath = imagePath;
        this.regDate = regDate;
    }

    public BannerDto() {
    }

}
