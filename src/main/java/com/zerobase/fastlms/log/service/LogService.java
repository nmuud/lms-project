package com.zerobase.fastlms.log.service;

import com.zerobase.fastlms.log.entity.Log;
import com.zerobase.fastlms.log.model.AccessParam;
import com.zerobase.fastlms.log.model.LogParam;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface LogService {

    /**
     * login데이터 수집하기
     */
    AccessParam checkData(HttpServletRequest request);

    /**
     * log 저장하기
     */
    void save(LogParam logParam);

    List<Log> getLoginHistoryByUserId(String userId);

    LocalDateTime getLastLoginByUserId(String userId);

}
