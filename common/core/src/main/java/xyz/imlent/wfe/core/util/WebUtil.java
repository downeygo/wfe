package xyz.imlent.wfe.core.util;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wfee
 */
public class WebUtil {
    private WebUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static final String UN_KNOWN = "unknown";

    /**
     * 获取RequestAttributes
     *
     * @return
     */
    public static RequestAttributes getRequestAttributes() {
        return RequestContextHolder.getRequestAttributes();
    }

    /**
     * 获取HttpServletRequest
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        if (getRequestAttributes() != null) {
            return ((ServletRequestAttributes) getRequestAttributes()).getRequest();
        }
        return null;
    }

    public static Map<String, String> getHeaders() {
        return getHeaders(getRequest());
    }

    public static Map<String, String> getHeaders(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headerMap = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            headerMap.put(name, value);
        }
        return headerMap;
    }

    /**
     * 获取IP
     *
     * @return
     */
    public static String getIP() {
        return getIP(getRequest());
    }

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    public static String getIP(HttpServletRequest request) {
        Assert.notNull(request, "HttpServletRequest is null");
        String ip = request.getHeader("X-Requested-For");
        if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isEmpty(ip) || UN_KNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return StringUtils.isEmpty(ip) ? null : ip.split(",")[0];
    }
}
