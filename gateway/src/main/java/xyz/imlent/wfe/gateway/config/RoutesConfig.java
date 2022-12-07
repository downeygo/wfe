package xyz.imlent.wfe.gateway.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import xyz.imlent.wfe.core.constant.AppConstant;

/**
 * @author wfee
 */
@Component
@ConditionalOnProperty(prefix = "wfe.gateway", name = "dynamic", havingValue = "false")
public class RoutesConfig {
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r
                        .path("/uaa/**")
                        .filters(f -> f.stripPrefix(0))
                        .uri("lb://" + AppConstant.APP_UAA_NAME))
                .route(r -> r
                        .path("/user/**")
                        .filters(f -> f.stripPrefix(0))
                        .uri("lb://" + AppConstant.APP_USER_SERVICE_NAME))
                .build();
    }
}
