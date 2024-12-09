package com.zerobase.fastlms.admin.banner.repository;

import com.zerobase.fastlms.admin.banner.entity.Banner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BannerRepository extends JpaRepository<Banner, Long> {
    List<Banner> findAllByOrderBySortOrderAsc();
    void deleteAllByIdIn(List<Long> ids);
    List<Banner> findAllByVisibleTrueOrderBySortOrderAsc();
}
