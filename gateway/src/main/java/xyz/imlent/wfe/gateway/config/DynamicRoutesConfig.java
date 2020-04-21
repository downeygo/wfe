package xyz.imlent.wfe.gateway.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import xyz.imlent.wfe.core.customer.NacosConfig;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author wfee
 */
@Slf4j
@Configuration
@AllArgsConstructor
@ConditionalOnProperty(prefix = "wfe.gateway", name = "dynamic", havingValue = "true")
public class DynamicRoutesConfig implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher applicationEventPublisher;

    private RouteDefinitionWriter routeDefinitionWriter;

    private List<String> cachedRouteId;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Bean
    public void getGatewayConfig() {
        try {
            ConfigService configService = NacosFactory.createConfigService(NacosConfig.NACOS_ADDR);
            String config = configService.getConfig(NacosConfig.GATEWAY_ROUTE_DATAID, NacosConfig.GATEWAT_ROUTE_GROUP, 5000L);
            // 启动初始化
            parseConfig(config).forEach(r -> addRoute(r));
            log.info("add gateway routes:{}",config);
            // 添加监听
            configService.addListener(NacosConfig.GATEWAY_ROUTE_DATAID, NacosConfig.GATEWAT_ROUTE_GROUP, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String config) {
                    deleteCachedRoute();
                    parseConfig(config).forEach(r -> addRoute(r));
                    publish();
                    log.info("update gateway routes:{}", config);
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析json
     *
     * @param config
     */
    private List<RouteDefinition> parseConfig(String config) {
        return JSONObject.parseArray(config, RouteDefinition.class);
    }

    /**
     * 添加路由
     *
     * @param routeDefinition
     */
    private void addRoute(RouteDefinition routeDefinition) {
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
        cachedRouteId.add(routeDefinition.getId());
    }

    /**
     * 删除缓存路由
     */
    private void deleteCachedRoute() {
        cachedRouteId.forEach(id -> routeDefinitionWriter.delete(Mono.just(id)).subscribe());
        cachedRouteId.removeAll(cachedRouteId);
    }


    /**
     * 发布路由
     */
    private void publish() {
        this.applicationEventPublisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }
}
