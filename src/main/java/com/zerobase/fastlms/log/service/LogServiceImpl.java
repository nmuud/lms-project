package com.zerobase.fastlms.log.service;

import com.zerobase.fastlms.log.entity.Log;
import com.zerobase.fastlms.log.model.AccessParam;
import com.zerobase.fastlms.log.model.LogParam;
import com.zerobase.fastlms.log.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;

    @Override
    public AccessParam checkData(HttpServletRequest request) {
        String clientIp = getClientIP(request);
        String userAgent = request.getHeader("User-Agent");
        if (userAgent == null) {
            userAgent = "Unknown User-Agent";
        }

        AccessParam accessParam = AccessParam.builder()
                .accessIp(clientIp)
                .accessUserAgent(userAgent)
                .build();

        return accessParam;
    }

    private String getClientIP(HttpServletRequest request) {
        String[] headers = {
                "X-FORWARDED-FOR", "Proxy-Client-IP", "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR", "X-Real-IP",
                "X-RealIP", "REMOTE_ADDR"
        };

        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() > 0 && !"unknown".equalsIgnoreCase(ip)) {
                return ip;
            }
        }
        return request.getRemoteAddr();
    }

    @Override
    public void save(LogParam logParam) {
        Log log = Log.builder()
                .loginId(logParam.getId())
                .loginDt(LocalDateTime.now())
                .accessIp(logParam.getAccessIp())
                .accessUserAgent(logParam.getAccessUserAgent())
                .build();
        logRepository.save(log);
    }

    @Override
    public List<Log> getLoginHistoryByUserId(String userId) {
        return logRepository.findAllByLoginIdOrderByLoginDtDesc(userId);
    }

    @Override
    public LocalDateTime getLastLoginByUserId(String userId) {
        List<Log> logs = logRepository.findAllByLoginIdOrderByLoginDtDesc(userId);
        if (logs.isEmpty()) {
            return null; // 로그인 기록이 없으면 null 반환
        }
        return logs.get(0).getLoginDt(); // 가장 최근 로그인 기록 반환
    }

}
