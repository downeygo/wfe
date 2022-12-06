package xyz.imlent.wfe.core.customer;

import xyz.imlent.wfe.core.constant.BaseConstant;

/**
 * @author wfee
 * @deprecated 被取代 {@link xyz.imlent.wfe.core.customer.NacosProperties}
 */
@Deprecated
public class NacosConfig {
    private NacosConfig() {
    }

    /**
     * nacos地址
     */
    public static final String NACOS_ADDR = "127.0.0.1:8848";

    /**
     * DEFAULT_GROUP
     */
    public static final String DEFAULT_GROUP = "DEFAULT_GROUP";

    /**
     * 匹配wfe开头配置文件
     */
    public static final String NACOS_CONFIG_PREFIX = "wfe-";

    /**
     * 配置文件类型
     */
    public static final String NACOS_CONFIG_FILE_EXTENSION = "yaml";

    /**
     * 公共配置文件
     *
     */
    public static final String NACOS_CONFIG_COMMON_PREFIX = NACOS_CONFIG_PREFIX + "common";

    /**
     * 开发环境配置文件
     *
     */
    public static final String NACOS_CONFIG_DEV_NAME = NACOS_CONFIG_PREFIX + BaseConstant.ENV_DEV + NACOS_CONFIG_FILE_EXTENSION;

    /**
     * 测试环境配置文件
     *
     */
    @Deprecated
    public static final String NACOS_CONFIG_TEST_NAME = NACOS_CONFIG_PREFIX + BaseConstant.ENV_TEST + NACOS_CONFIG_FILE_EXTENSION;

    /**
     * 生产环境配置文件
     *
     */
    public static final String NACOS_CONFIG_PROD_NAME = NACOS_CONFIG_PREFIX + BaseConstant.ENV_PROD + NACOS_CONFIG_FILE_EXTENSION;

    /**
     * @param env 环境
     * @return 配置文件名称
     */
    public static String getConfig(String env) {
        if (env.equals(BaseConstant.ENV_TEST)) {
            return NACOS_CONFIG_TEST_NAME;
        } else if (env.equals(BaseConstant.ENV_PROD)) {
            return NACOS_CONFIG_PROD_NAME;
        } else {
            return NACOS_CONFIG_DEV_NAME;
        }
    }
}
