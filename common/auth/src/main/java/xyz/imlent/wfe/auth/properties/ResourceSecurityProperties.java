package xyz.imlent.wfe.auth.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 定义每个服务不需登录可访问的url
 *
 * @author wfee
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wfe.security")
public class ResourceSecurityProperties {
    private Map<String, String[]> ignoreUri;
}
