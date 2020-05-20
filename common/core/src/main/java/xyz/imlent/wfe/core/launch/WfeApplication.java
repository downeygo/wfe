package xyz.imlent.wfe.core.launch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xyz.imlent.wfe.core.constant.BaseConstant;
import xyz.imlent.wfe.core.customer.NacosConfig;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目启动器，搞定环境变量问题。
 * 已被 {@link xyz.imlent.wfe.core.config.EnvProcessor} 取代
 *
 * @author wfee
 */
@Slf4j
@Deprecated
public class WfeApplication {

    public static ConfigurableApplicationContext run(String appName, Class source, String... args) {
        SpringApplicationBuilder builder = createSpringApplicationBuilder(appName, source, args);
        return builder.run(args);
    }

    private static SpringApplicationBuilder createSpringApplicationBuilder(String appName, Class source, String... args) {
        Assert.hasText(appName, "[appName]服务名不能为空");
        // 读取环境变量，使用spring boot的规则
        ConfigurableEnvironment environment = new StandardEnvironment();
        MutablePropertySources propertySources = environment.getPropertySources();
        propertySources.addFirst(new SimpleCommandLinePropertySource(args));
        propertySources.addLast(new MapPropertySource(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, environment.getSystemProperties()));
        propertySources.addLast(new SystemEnvironmentPropertySource(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, environment.getSystemEnvironment()));
        // 获取配置的环境变量
        String[] activeProfiles = environment.getActiveProfiles();
        List<String> activeProfileList = Arrays.stream(activeProfiles).collect(Collectors.toList());
        // 获取可使用的环境变量
        List<String> presetProfileList = Arrays.stream(BaseConstant.ENVS).collect(Collectors.toList());
        // 交集
        activeProfileList.retainAll(presetProfileList);
        String activeProfile;
        int size = activeProfileList.size();
        switch (size) {
            case 0:
                activeProfile = BaseConstant.ENV_DEV;
                break;
            case 1:
                activeProfile = activeProfileList.get(0);
                break;
            default:
                throw new RuntimeException(String.format("同时存在多个可用环境变量：[%s]", StringUtils.arrayToCommaDelimitedString(activeProfiles)));
        }
        String startJarPath = WfeApplication.class.getResource("/").getPath().split("!")[0];
        log.info("----启动中，读取到的环境变量:{}，jar地址:{}----", activeProfile, startJarPath);
        Properties props = System.getProperties();
        props.setProperty("spring.application.name", appName);
        props.setProperty("spring.profiles.active", activeProfile);
        props.setProperty("spring.main.allow-bean-definition-overriding", "true");
        // nacos注册中心配置
        props.setProperty("spring.cloud.nacos.config.prefix", NacosConfig.NACOS_CONFIG_COMMON_PREFIX);
        props.setProperty("spring.cloud.nacos.config.file-extension", NacosConfig.NACOS_CONFIG_FILE_EXTENSION);
        props.setProperty("spring.cloud.nacos.config.shared-dataids", NacosConfig.getConfig(activeProfile));
        props.setProperty("spring.cloud.nacos.discovery.server-addr", NacosConfig.NACOS_ADDR);
        props.setProperty("spring.cloud.nacos.config.server-addr", NacosConfig.NACOS_ADDR);
        // seata分布式事务配置
        props.setProperty("spring.cloud.alibaba.seata.tx-service-group", appName);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(source);
        builder.profiles(activeProfile);
        // SPI加载自定义组件
        List<LauncherService> launcherList = new ArrayList<>();
        ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
        launcherList.stream()
                .sorted(Comparator.comparing(LauncherService::getOrder))
                .collect(Collectors.toList())
                .forEach(launcherService -> launcherService.launcher(builder, appName, activeProfile));
        return builder;
    }
}
