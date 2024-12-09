package com.zerobase.fastlms.admin.banner.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class FileStorageService {

    private final String baseLocalPath = "C:\\Users\\RE\\aajava\\fastlms3\\files";
    private final String baseUrlPath = "/files";

    public String storeFile(MultipartFile file) throws IOException {

        String baseLocalPath = "C:/Users/RE/aajava/fastlms3/files";
        String baseUrlPath = "/files";

        LocalDate now = LocalDate.now();
        String[] dirs = {
                String.format("%s/%d", baseLocalPath, now.getYear()),
                String.format("%s/%d/%02d", baseLocalPath, now.getYear(), now.getMonthValue()),
                String.format("%s/%d/%02d/%02d", baseLocalPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth())
        };

        String urlDir = String.format("%s/%d/%02d/%02d", baseUrlPath, now.getYear(), now.getMonthValue(), now.getDayOfMonth());

        for (String dir : dirs) {
            File fileDir = new File(dir);
            if (!fileDir.exists()) {
                fileDir.mkdirs(); // 디렉토리 생성
            }
        }

        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileExtension = "";
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        }

        String newFilename = String.format("%s/%s.%s", dirs[2], uuid, fileExtension);
        String urlFilename = String.format("%s/%s.%s", urlDir, uuid, fileExtension);

        Files.copy(file.getInputStream(), Paths.get(newFilename), StandardCopyOption.REPLACE_EXISTING);

        return urlFilename; // URL 경로 반환
    }

}
