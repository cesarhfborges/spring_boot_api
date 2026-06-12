package br.com.chfb.api.utils;

import jakarta.servlet.http.HttpServletRequest;

public final class RequestUtils {

    public static String getClientIp(HttpServletRequest request) {

        String forwarded = request.getHeader("X-Forwarded-For");

        if (forwarded != null && !forwarded.isBlank()) {

            return forwarded
                    .split(",")[0]
                    .trim();
        }

        String ip = request.getRemoteAddr();

        if ("0:0:0:0:0:0:0:1".equals(ip) || "::1".equals(ip)) {
            return "127.0.0.1";
        }

        return ip;
    }
}
