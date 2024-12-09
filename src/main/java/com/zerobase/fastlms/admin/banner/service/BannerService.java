package com.zerobase.fastlms.admin.banner.service;

import com.zerobase.fastlms.admin.banner.entity.Banner;
import com.zerobase.fastlms.admin.banner.dto.BannerDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BannerService {
    // 모든 배너 가져오기
    List<Banner> getAllBanners();

    // 특정 배너 삭제
    void deleteBanners(List<Long> ids);

    // 배너 저장
    void saveBanner(BannerDto bannerDto, MultipartFile file) throws IOException;

    List<Banner> getVisibleBanners(); // 공개된 배너 가져오기

    String storeFile(MultipartFile file) throws IOException;

    BannerDto getBannerById(Long id); // 배너 상세 조회

    void updateBanner(BannerDto bannerDto, MultipartFile file) throws IOException; // 배너 수정
}
