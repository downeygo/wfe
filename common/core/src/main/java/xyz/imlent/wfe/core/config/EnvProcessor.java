package xyz.imlent.wfe.core.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import xyz.imlent.wfe.core.annotation.AppEnvEnum;
import xyz.imlent.wfe.core.annotation.AppName;
import xyz.imlent.wfe.core.exception.SystemException;

import java.util.Arrays;

/**
 * 项目启动加载环境
 * 已取代 {@link xyz.imlent.wfe.core.launch.WfeApplication}
 *
 * @author wfee
 */
public class EnvProcessor implements EnvironmentPostProcessor {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        environment.getPropertySources().addFirst(EnvPropertites.init(getEnvName(environment), getAppName(application)));
    }

    /**
     * 获取服务名称
     *
     * @param application application
     * @return 服务名称
     */
    private String getAppName(SpringApplication application) {
        Class<?> aClass = application.getMainApplicationClass();
        AppName appName = aClass.getAnnotation(AppName.class);
        if (ObjectUtils.isEmpty(appName)) {
            throw new SystemException(String.format("请使用[%s]为[%s]命名", AppName.class, aClass));
        }
        if (!StringUtils.hasText(appName.name())) {
            throw new SystemException(String.format("请为[%s]正确命名", aClass));
        }
        return appName.name();
    }

    /**
     * 启动时获取环境
     *
     * @param environment 环境
     * @return 可用环境
     */
    private String getEnvName(ConfigurableEnvironment environment) {
        String[] profiles = environment.getActiveProfiles();
        // 获取可使用的环境变量
        int length = profiles.length;
        if (length == 0) {
            return AppEnvEnum.DEV.name().toLowerCase();
        }
        AppEnvEnum[] appEnvEnums = AppEnvEnum.values();
        if (length > 1 || Arrays.stream(appEnvEnums).noneMatch(e -> e.name().equalsIgnoreCase(profiles[0]))) {
            throw new SystemException(String.format("请在%s环境中选择一个使用，当前环境%s", Arrays.toString(appEnvEnums), Arrays.toString(profiles)));
        }
        return profiles[0];
    }
}
