package xyz.imlent.wfe.service.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import xyz.imlent.wfe.auth.config.DefaultResourceServer;

/**
 * @author wfee
 */
@Configuration
@EnableResourceServer
public class ResourceConfig extends DefaultResourceServer {
}
