package xyz.imlent.wfe.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import xyz.imlent.wfe.core.customer.NacosProperties;
import xyz.imlent.wfe.gateway.properties.DynamicRoutesProperties;

import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author wfee
 */
@Slf4j
@Configuration
@AllArgsConstructor
@ConditionalOnBean(DynamicRoutesProperties.class)
public class DynamicRoutesConfig implements ApplicationEventPublisherAware {
    private ApplicationEventPublisher publisher;

    private RouteDefinitionWriter routeDefinitionWriter;

    private DynamicRoutesProperties properties;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Bean
    public void refreshGatewayRoute() {
        try {
            ConfigService configService = NacosFactory.createConfigService(NacosProperties.ADDR);
            String routeDataId = properties.getRouteDataId();
            if (StringUtils.isBlank(routeDataId)) {
                throw new NacosException(500, "未匹配到动态路由ID{gateway.route-data-id}");
            }
            String config = configService.getConfig(routeDataId, NacosProperties.DEFAULT_GROUP, 5000L);
            if (StringUtils.isBlank(config)) {
                throw new NacosException(500, "未获取到动态路由配置{" + routeDataId + "}");
            }
            log.info("add gateway dynamic routes:{}", config);
            // 添加监听
            configService.addListener(routeDataId, NacosProperties.DEFAULT_GROUP, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String config) {
                    List<RouteDefinition> routeDefinitionList = parseConfig(config);
                    updateRoutes(routeDefinitionList);
                    log.info("update gateway dynamic routes:{}", config);
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
            log.error(e.getErrMsg());
        }
    }

    /**
     * 解析json
     *
     * @param config
     */
    private List<RouteDefinition> parseConfig(String config) {
        return JSON.parseArray(config, RouteDefinition.class);
    }

    /**
     * 添加路由
     *
     * @param routeDefinition
     */
    private void addRoute(RouteDefinition routeDefinition) {
        routeDefinitionWriter.save(Mono.just(routeDefinition)).subscribe();
    }

    /**
     * 删除路由
     *
     * @param routeDefinition
     */
    private void deleteRoute(RouteDefinition routeDefinition) {
        this.routeDefinitionWriter.delete(Mono.just(routeDefinition.getId())).subscribe();
    }

    /**
     * 更新路由
     *
     * @param routeDefinitionList
     */
    private void updateRoutes(List<RouteDefinition> routeDefinitionList) {
        try {
            routeDefinitionList.forEach(rd -> {
                this.deleteRoute(rd);
                this.addRoute(rd);
            });
            this.publish();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
        }
    }

    /**
     * 发布路由
     */
    private void publish() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }
}
