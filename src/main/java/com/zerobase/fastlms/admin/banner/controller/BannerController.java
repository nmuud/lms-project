package com.zerobase.fastlms.admin.banner.controller;

import com.zerobase.fastlms.admin.banner.dto.BannerDto;
import com.zerobase.fastlms.admin.banner.entity.Banner;
import com.zerobase.fastlms.admin.banner.service.BannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class BannerController {

    private final BannerService bannerService;

    @GetMapping("/admin/banner/list.do")
    public String list(Model model) {
        List<Banner> banners = bannerService.getAllBanners();

        // Controller에서 Banner를 BannerDto로 변환
        List<BannerDto> bannerDtos = banners.stream().map(banner -> {
            BannerDto dto = new BannerDto();
            dto.setId(banner.getId());
            dto.setName(banner.getName());
            dto.setAltText(banner.getAltText());
            dto.setUrl(banner.getUrl());
            dto.setTarget(banner.getTarget());
            dto.setSortOrder(banner.getSortOrder());
            dto.setVisible(banner.isVisible());
            dto.setImagePath(banner.getImagePath().replace("\\", "/")); // 이미지 경로 추가
            dto.setRegDate(banner.getRegDate() != null
                    ? banner.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    : ""); // 날짜 포맷팅
            return dto;
        }).collect(Collectors.toList());

        model.addAttribute("banners", bannerDtos);
        return "admin/banner/list";
    }

    @PostMapping("/admin/banner/delete")
    @ResponseBody
    public void deleteBanners(@RequestBody List<Long> ids) {
        bannerService.deleteBanners(ids);
    }

    @PostMapping("/admin/banner/add")
    public String addBanner(@ModelAttribute BannerDto bannerDto,
                            @RequestParam("file") MultipartFile file) throws IOException {
        // 이미지 파일 저장
        String imagePath = bannerService.storeFile(file);

        // 배너 정보에 이미지 경로 추가
        bannerDto.setImagePath(imagePath);

        // 배너 저장 서비스 호출
        bannerService.saveBanner(bannerDto, file);

        return "redirect:/admin/banner/list.do";
    }

    @GetMapping("/admin/banner/add.do")
    public String addBannerForm() {
        return "admin/banner/add";
    }

    @GetMapping("/admin/banner/edit.do")
    public String editBannerForm(@RequestParam("id") Long id, Model model) {
        BannerDto bannerDto = bannerService.getBannerById(id);
        model.addAttribute("banner", bannerDto);
        return "admin/banner/edit";
    }

    @PostMapping("/admin/banner/edit.do")
    public String editBanner(@ModelAttribute BannerDto bannerDto,
                             @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        bannerService.updateBanner(bannerDto, file);
        return "redirect:/admin/banner/list.do";
    }

}
