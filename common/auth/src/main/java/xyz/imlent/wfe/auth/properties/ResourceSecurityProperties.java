package xyz.imlent.wfe.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 定义每个服务不需登录可访问的url
 *
 * @author wfee
 */
@Data
@Component
@ConfigurationProperties(prefix = "wfe.security")
public class ResourceSecurityProperties {
    private Map<String, String[]> ignoreUri;
}
