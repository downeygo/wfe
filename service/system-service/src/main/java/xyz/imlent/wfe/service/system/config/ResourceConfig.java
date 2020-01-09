package xyz.imlent.wfe.service.system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import xyz.imlent.wfe.auth.config.DefaultResourceServerConfig;

/**
 * @author wfee
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends DefaultResourceServerConfig {
}
