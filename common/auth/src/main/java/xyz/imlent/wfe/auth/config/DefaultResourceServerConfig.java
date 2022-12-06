package xyz.imlent.wfe.auth.config;

import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import xyz.imlent.wfe.auth.properties.ResourceSecurityProperties;

/**
 * @author wfee
 */
@AllArgsConstructor
public class DefaultResourceServerConfig extends ResourceServerConfigurerAdapter {
    private TokenStore tokenStore;

    private Environment environment;

    private ResourceSecurityProperties resourceSecurityProperties;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(getResourceId())
                .tokenStore(tokenStore)
                .stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 读取配置文件中放行的uri
                .antMatchers(getIgnoreUri()).permitAll()
                // 其余全部需要认证
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .cors();
        getHttpSecurity(http);
    }

    /**
     * 子类可重写自定义拦截
     *
     * @param http
     * @throws Exception
     */
    protected HttpSecurity getHttpSecurity(HttpSecurity http) {
        return http;
    }

    /**
     * 子类重写设置resourceId
     *
     * @return
     */
    protected String getResourceId() {
        return environment.getProperty("spring.application.name");
    }


    private String[] getIgnoreUri() {
        return resourceSecurityProperties.getIgnoreUri().get(getResourceId());
    }
}
