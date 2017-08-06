/**
 *  Copyright 2017 FinTx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fintx.util;

import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
public class Ips {

    // public static String getRemoteAddress() {
    // RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
    // if (requestAttributes == null) {
    // return StringUtil.EMPTY;
    // }
    // HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
    // if (request == null) {
    // return StringUtil.EMPTY;
    // }
    // return getRemoteAddress(request);
    // }

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
