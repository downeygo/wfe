package xyz.imlent.wfe.core.handler;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import xyz.imlent.wfe.core.model.R;
import xyz.imlent.wfe.core.util.WebUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author wfee
 */
@RestController
public class GlobalExceptionHandler implements ErrorController {

    private ServerProperties serverProperties;

    @Override
    public String getErrorPath() {
        return serverProperties.getError().getPath();
    }

    @RequestMapping(value = "${server.error.path:${error.path:/error}}")
    public R exception() {
        return R.fail(getStatus().value(), buildMessage());
    }

    private HttpStatus getStatus() {
        Integer statusCode = (Integer) getErrorAttributes("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        try {
            return HttpStatus.valueOf(statusCode);
        } catch (Exception ex) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }

    private Throwable getError() {
        return (Throwable) getErrorAttributes("javax.servlet.error.exception");
    }

    private String getUri() {
        return (String) getErrorAttributes("javax.servlet.error.request_uri");
    }

    private String buildMessage() {
        HttpServletRequest request = WebUtil.getRequest();
        StringBuilder message = new StringBuilder("failed to handle request [");
        message.append(request.getMethod());
        message.append(" ");
        message.append(getUri());
        message.append("]");
        if (getError() != null) {
            message.append(": ");
            message.append(getError().getMessage());
        }
        return message.toString();
    }

    private Object getErrorAttributes(String name) {
        return WebUtil.getRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }
}
