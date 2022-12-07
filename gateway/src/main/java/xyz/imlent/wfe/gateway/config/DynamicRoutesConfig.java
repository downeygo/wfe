package xyz.imlent.wfe.gateway.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import reactor.core.publisher.Mono;
import xyz.imlent.wfe.gateway.properties.DynamicRoutesProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author wfee
 */
@Slf4j
@Configuration
@ConditionalOnBean(DynamicRoutesProperties.class)
public class DynamicRoutesConfig implements ApplicationEventPublisherAware {
    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    private DynamicRoutesProperties properties;

    @Autowired
    private Environment environment;

    private List<String> cacheIds = new ArrayList<>(16);

    private Lock lock = new ReentrantLock();

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Bean
    public void refreshGatewayRoute() {
        try {
            String serverAddr = environment.getProperty("spring.cloud.nacos.config.server-addr");
            String namespace = environment.getProperty("spring.cloud.nacos.config.namespace");
            String group = environment.getProperty("spring.cloud.nacos.config.group");
            Properties nacosProperties = new Properties();
            nacosProperties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
            nacosProperties.setProperty(PropertyKeyConst.NAMESPACE, namespace);
            ConfigService configService = NacosFactory.createConfigService(nacosProperties);
            String routeDataId = properties.getRouteDataId();
            if (StringUtils.isBlank(routeDataId)) {
                throw new NacosException(0, "未匹配到动态路由ID{gateway.route-data-id}");
            }
            String config = configService.getConfig(routeDataId, group, 5000L);
            if (StringUtils.isBlank(config)) {
                throw new NacosException(0, "未获取到动态路由配置{" + routeDataId + "}");
            }
            updateRoute(config);
            log.info("add gateway dynamic routes:{}", config);
            // 添加监听
            configService.addListener(routeDataId, group, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String config) {
                    updateRoute(config);
                    log.info("update gateway dynamic routes:{}", config);
                }
            });
        } catch (NacosException e) {
            e.printStackTrace();
            log.error(e.getErrMsg());
        }
    }

    /**
     * @param config
     */
    private void updateRoute(String config) {
        List<RouteDefinition> routeDefinitionList = parse(config);
        updateRoutes(routeDefinitionList);
    }

    /**
     * 解析json
     *
     * @param config
     */
    private List<RouteDefinition> parse(String config) {
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
     * @param id
     */
    private void deleteRoute(String id) {
        this.routeDefinitionWriter.delete(Mono.just(id)).subscribe();
    }

    /**
     * 更新路由
     *
     * @param routeDefinitionList
     */
    private void updateRoutes(List<RouteDefinition> routeDefinitionList) {
        lock.lock();
        try {
            // 删除缓存中的路由
            cacheIds.forEach(this::deleteRoute);
            routeDefinitionList.forEach(rd -> {
                this.addRoute(rd);
                // 将路由添加到缓存
                cacheIds.add(rd.getId());
            });
            this.publish();
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getLocalizedMessage());
        } finally {
            lock.unlock();
        }
    }

    /**
     * 发布路由
     */
    private void publish() {
        this.publisher.publishEvent(new RefreshRoutesEvent(this.routeDefinitionWriter));
    }
}
