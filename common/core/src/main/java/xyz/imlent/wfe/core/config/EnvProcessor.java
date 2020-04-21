package xyz.imlent.wfe.core.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import xyz.imlent.wfe.core.annotation.AppName;
import xyz.imlent.wfe.core.customer.PropertiesConfig;

/**
 * @author wfee
 */
public class EnvProcessor implements EnvironmentPostProcessor {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String name = getAppName(application);
        MapPropertySource mapPropertySource = new MapPropertySource("wfe-global-config", PropertiesConfig.initAppConfig(name));
        environment.getPropertySources().addFirst(mapPropertySource);
    }

    private String getAppName(SpringApplication application) {
        Class<?> aClass = application.getMainApplicationClass();
        AppName appName = aClass.getAnnotation(AppName.class);
        if (ObjectUtils.isEmpty(appName)) {
            throw new RuntimeException(String.format("请使用[%s]为[%s]命名", AppName.class, aClass));
        }
        if (!StringUtils.hasText(appName.name())) {
            throw new RuntimeException(String.format("请为[%s]正确命名", aClass));
        }
        return appName.name();
    }
}
