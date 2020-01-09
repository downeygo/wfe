package xyz.imlent.wfe.gateway.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import xyz.imlent.wfe.auth.properties.ResourceSecurityProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wfee
 */
@AllArgsConstructor
@EnableWebFluxSecurity
public class WebSecutityConfig {
    private ResourceSecurityProperties securityProperties;

    @Bean
    public SecurityWebFilterChain webFluxSecurityFilterChain(ServerHttpSecurity http) throws Exception {
        return http.authorizeExchange()
                .anyExchange().permitAll()
                .and()
                .cors()
                .and()
                .csrf().disable().build();
    }


    private String[] getAllIgnoreUri() {
        List<String> list = new ArrayList<>();
        securityProperties.getIgnoreUri().forEach((k, v) -> {
            Arrays.stream(v).forEach(uri -> list.add(uri));
        });
        return list.stream().distinct().toArray(String[]::new);
    }
}
