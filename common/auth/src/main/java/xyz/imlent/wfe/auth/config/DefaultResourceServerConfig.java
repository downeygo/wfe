package xyz.imlent.wfe.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import xyz.imlent.wfe.auth.properties.ResourceSecurityProperties;

/**
 * @author wfee
 */
public class DefaultResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private TokenStore tokenStorer;

    @Autowired
    private Environment environment;

    @Autowired
    private ResourceSecurityProperties resourceSecurityProperties;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources
                .resourceId(getResourceId())
                .tokenStore(tokenStorer)
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
    public HttpSecurity getHttpSecurity(HttpSecurity http) throws Exception {
        return http;
    }

    /**
     * 子类重写设置resourceId
     *
     * @return
     */
    public String getResourceId() {
        return environment.getProperty("spring.application.name");
    }


    private String[] getIgnoreUri() {
        return resourceSecurityProperties.getIgnoreUri().get(getResourceId());
    }
}
