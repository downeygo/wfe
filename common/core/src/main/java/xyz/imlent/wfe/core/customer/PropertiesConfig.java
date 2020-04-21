package xyz.imlent.wfe.core.customer;

import xyz.imlent.wfe.core.constant.BaseConstant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wfee
 */
public class PropertiesConfig {
    /**
     * 环境
     */
    public static String ACTIVE_ENV = BaseConstant.ENV_DEV;

    /**
     * 加载配置文件
     *
     * @param name
     * @return
     */
    @SuppressWarnings("ALL")
    public static Map<String, Object> initAppConfig(String env, String name) {
        Map<String, Object> configMap = new ConcurrentHashMap<>(16);
        configMap.put("spring.application.name", name);
        configMap.put("spring.profiles.active", env);
        configMap.put("spring.main.allow-bean-definition-overriding", "true");
        // nacos注册中心配置
        configMap.put("spring.cloud.nacos.config.prefix", NacosConfig.NACOS_CONFIG_COMMON_PREFIX);
        configMap.put("spring.cloud.nacos.config.file-extension", NacosConfig.NACOS_CONFIG_FILE_EXTENSION);
        configMap.put("spring.cloud.nacos.config.shared-dataids", NacosConfig.getConfig(ACTIVE_ENV));
        configMap.put("spring.cloud.nacos.discovery.server-addr", NacosConfig.NACOS_ADDR);
        configMap.put("spring.cloud.nacos.config.server-addr", NacosConfig.NACOS_ADDR);
        // seata分布式事务配置
        configMap.put("spring.cloud.alibaba.seata.tx-service-group", name);
        return configMap;
    }
}
