package xyz.imlent.wfe.log.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.imlent.wfe.log.listener.ApiLogListener;

/**
 * @author wfee
 */
@Configuration
@ConditionalOnWebApplication
public class LogAutoConfig {

    @Bean
    public ApiLogListener apiLogListener() {
        return new ApiLogListener();
    }
}
