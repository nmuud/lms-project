package com.zerobase.fastlms.main.controller;

import com.zerobase.fastlms.admin.banner.entity.Banner;
import com.zerobase.fastlms.admin.banner.service.BannerService;
import com.zerobase.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final MailComponents mailComponents;
    private final BannerService bannerService;

    @GetMapping("/")
    public String index(Model model) {
        // 공개된 배너 데이터를 가져오기
        List<Banner> banners = bannerService.getVisibleBanners();
        model.addAttribute("banners", banners);

        return "index"; // 메인 페이지 뷰
    }

    @RequestMapping("/")
    public String index() {

        //mailComponents.sendMailTest();
        String email = "nmuud@naver.com";
        String subject = " 안녕하세요. 제로베이스 입니다. ";
        String text = "<p>안녕하세요.</p><p>반갑습니다.</p>";

        mailComponents.sendMail(email, subject, text);

        return "index";
    }

    @RequestMapping("/error/denied")
    public String errorDenied() {

        return "error/denied";
    }

}
