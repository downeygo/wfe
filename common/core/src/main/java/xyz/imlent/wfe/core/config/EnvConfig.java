package xyz.imlent.wfe.core.config;

import lombok.experimental.UtilityClass;
import org.springframework.core.env.MapPropertySource;
import xyz.imlent.wfe.core.customer.NacosProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wfee
 */
@UtilityClass
public class EnvConfig {

    /**
     * 配置名称
     */
    private static final String CONFIG_NAME = "wfe-global-config";

    /**
     * 初始化
     *
     * @param environment
     * @param application
     * @return
     */
    public static MapPropertySource init(String environment, String application) {
        return new MapPropertySource(CONFIG_NAME, initProperties(environment, application));
    }

    /**
     * 加载配置文件
     *
     * @param envName
     * @param appName
     * @return
     */
    public static Map<String, Object> initProperties(String envName, String appName) {
        Map<String, Object> configMap = new HashMap<>(16);
        configMap.put("spring.profiles.active", envName);
        configMap.put("spring.application.name", appName);
        configMap.put("spring.main.allow-bean-definition-overriding", "true");
        // nacos注册中心配置
        configMap.put("spring.cloud.nacos.config.server-addr", NacosProperties.ADDR);
        configMap.put("spring.cloud.nacos.discovery.server-addr", NacosProperties.ADDR);
        configMap.put("spring.cloud.nacos.config.namespace", envName);
        // configMap.put("spring.cloud.nacos.config.prefix", NacosProperties.CONFIG_PREFIX + appName);
        configMap.put("spring.cloud.nacos.config.file-extension", NacosProperties.CONFIG_FILE_EXTENSION);
        configMap.put("spring.cloud.nacos.config.shared-dataids", NacosProperties.CONFIG_SHARED_DATA_IDS);
        configMap.put("spring.cloud.nacos.config.refreshable-dataids", true);
        // seata分布式事务配置
        configMap.put("spring.cloud.alibaba.seata.tx-service-group", envName);
        return configMap;
    }
}
