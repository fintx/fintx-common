package org.fintx.util;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;

public class IPUtil {
  
//    public static String getRemoteAddress() {
//        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        if (requestAttributes == null) {
//            return StringUtil.EMPTY;
//        }
//        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
//        if (request == null) {
//            return StringUtil.EMPTY;
//        }
//        return getRemoteAddress(request);
//    }

    public static String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (Strings.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // If there are proxies, the first IP is the real IP.
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (Strings.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (Strings.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (Strings.isNotBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

   
    public static String getLocalAddress() {
        String clientId = "";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            clientId = inetAddress.getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientId;
    }

}
