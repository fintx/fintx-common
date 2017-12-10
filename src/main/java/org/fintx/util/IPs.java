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
public class IPs {

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
    private IPs() {
        
    }
    /**
     * Get the remote IP from HttpServletRequest
     * 
     * @param request the HttpServletRequest
     * @return String the remote IP
     */
    public static String getRemoteAddress(HttpServletRequest request) {
        if(null==request) {
            throw new IllegalArgumentException("Argument request should not be null!");
        }
        String ip = request.getHeader("X-Forwarded-For");
        if (!Strings.isBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            // If there are proxies, the first IP is the real IP.
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!Strings.isBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (!Strings.isBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (!Strings.isBlank(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    /**
     * Get the local IP
     * 
     * @return String the local IP
     */
    public static String getLocalAddress() {
        String clientId = "";
        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            clientId = inetAddress.getHostAddress();
        } catch (Throwable e) {
            throw new RuntimeException("Get local IP address failed!",e);
        }
        return clientId;
    }

}
