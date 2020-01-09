package xyz.imlent.wfe.core.launch;

import org.springframework.boot.builder.SpringApplicationBuilder;
import xyz.imlent.wfe.core.config.NacosConfig;
import xyz.imlent.wfe.core.constant.AppConstant;

import java.util.Properties;

/**
 * @author wfee
 */
public class LauncherServiceImpl implements LauncherService {

    @Override
    public void launcher(SpringApplicationBuilder builder, String appName, String profile) {
        Properties props = System.getProperties();
        // nacos配置
        props.setProperty("spring.cloud.nacos.discovery.server-addr", NacosConfig.NACOS_ADDR);
        props.setProperty("spring.cloud.nacos.config.server-addr", NacosConfig.NACOS_ADDR);
        // seata分布式事务配置
        props.setProperty("spring.cloud.alibaba.seata.tx-service-group", appName);
    }

}
