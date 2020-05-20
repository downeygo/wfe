package xyz.imlent.wfe.auth.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import xyz.imlent.wfe.core.util.WebUtil;

import java.util.Map;

/**
 * feign客户端添加header
 *
 * @author wfee
 */
@Configuration
public class FeignInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        Map<String, String> headers = WebUtil.getHeaders();
        headers.forEach(requestTemplate::header);
    }
}
