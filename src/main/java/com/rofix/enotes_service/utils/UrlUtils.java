package com.rofix.enotes_service.utils;

import jakarta.servlet.http.HttpServletRequest;

public class UrlUtils {
    public static String getBaseUrl(HttpServletRequest request){
        String protocol = request.getScheme();
        String host = request.getServerName();
        Integer serverPort = request.getServerPort();

        StringBuilder url = new StringBuilder();

        url.append(protocol).append("://").append(host);

        if(serverPort != 80 && serverPort != 443){
            url.append(":").append(serverPort);
        }

        return url.toString();
    }
}
