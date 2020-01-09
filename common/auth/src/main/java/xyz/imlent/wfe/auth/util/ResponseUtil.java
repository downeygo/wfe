package xyz.imlent.wfe.auth.util;

import com.alibaba.fastjson.JSON;
import org.springframework.http.MediaType;
import xyz.imlent.wfe.core.model.R;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author wfee
 */
public class ResponseUtil {
    private ResponseUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void responseSuccessWriter(HttpServletResponse response, String msg, int httpStatus) throws IOException {
        responseWriter(response, true, msg, httpStatus);
    }

    public static void responseFailWriter(HttpServletResponse response, String msg, int httpStatus) throws IOException {
        responseWriter(response, false, msg, httpStatus);
    }

    private static void responseWriter(HttpServletResponse response, boolean success, String msg, int httpStatus) throws IOException {
        response.setStatus(httpStatus);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        R r = success ? R.success(httpStatus, msg) : R.fail(httpStatus, msg);
        response.getWriter().write(JSON.toJSONString(r));
        response.getWriter().close();
    }
}
