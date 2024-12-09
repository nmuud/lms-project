package com.zerobase.fastlms.admin.banner.service.impl;

import com.zerobase.fastlms.admin.banner.entity.Banner;
import com.zerobase.fastlms.admin.banner.dto.BannerDto;
import com.zerobase.fastlms.admin.banner.repository.BannerRepository;
import com.zerobase.fastlms.admin.banner.service.BannerService;
import com.zerobase.fastlms.admin.banner.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;
    private final FileStorageService fileStorageService;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Override
    public List<Banner> getAllBanners() {
        return bannerRepository.findAllByOrderBySortOrderAsc();
    }

    @Transactional
    @Override
    public void deleteBanners(List<Long> ids) {
        bannerRepository.deleteAllByIdIn(ids);
    }

    @Override
    public void saveBanner(BannerDto bannerDto, MultipartFile file) throws IOException {
        // 파일 저장 및 경로 반환
        String imagePath = fileStorageService.storeFile(file);

        Banner banner = Banner.builder()
                .name(bannerDto.getName())
                .imagePath(imagePath.replace("\\", "/"))
                .altText(bannerDto.getAltText())
                .url(bannerDto.getUrl())
                .target(bannerDto.getTarget())
                .sortOrder(bannerDto.getSortOrder())
                .visible(bannerDto.isVisible())
                .regDate(LocalDateTime.now())
                .build();
        bannerRepository.save(banner);
    }

    @Override
    public List<Banner> getVisibleBanners() {
        return bannerRepository.findAllByVisibleTrueOrderBySortOrderAsc();
    }

    @Override
    public String storeFile(MultipartFile file) throws IOException {
        return fileStorageService.storeFile(file); // FileStorageService를 사용해 파일 저장
    }

    @Override
    public BannerDto getBannerById(Long id) {
        Banner banner = bannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("배너를 찾을 수 없습니다."));
        return BannerDto.builder()
                .id(banner.getId())
                .name(banner.getName())
                .altText(banner.getAltText())
                .url(banner.getUrl())
                .target(banner.getTarget())
                .sortOrder(banner.getSortOrder())
                .visible(banner.isVisible())
                .imagePath(banner.getImagePath())
                .regDate(banner.getRegDate() != null
                        ? banner.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                        : null)
                .build();
    }

    @Override
    public void updateBanner(BannerDto bannerDto, MultipartFile file) throws IOException {
        Banner banner = bannerRepository.findById(bannerDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("배너를 찾을 수 없습니다."));

        // 새로운 파일 업로드 처리
        if (file != null && !file.isEmpty()) {
            String newImagePath = fileStorageService.storeFile(file);
            banner.setImagePath(newImagePath.replace("\\", "/"));
        }

        // 배너 정보 업데이트
        banner.setName(bannerDto.getName());
        banner.setAltText(bannerDto.getAltText());
        banner.setUrl(bannerDto.getUrl());
        banner.setTarget(bannerDto.getTarget());
        banner.setSortOrder(bannerDto.getSortOrder());
        banner.setVisible(bannerDto.isVisible());
        bannerRepository.save(banner);
    }

}
