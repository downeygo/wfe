package xyz.imlent.wfe.core.customer;

import lombok.experimental.UtilityClass;

/**
 * @author downey
 * @date 2022/1/21
 */
@UtilityClass
public class NacosProperties {
    /**
     * nacos地址
     */
    public static final String ADDR = "192.168.0.126:8848";

    /**
     * DEFAULT_GROUP
     */
    public static final String DEFAULT_GROUP = "DEFAULT_GROUP";

    /**
     * 匹配wfe开头配置文件
     */
    public static final String CONFIG_PREFIX = "wfe-";

    /**
     * 配置文件类型
     */
    public static final String CONFIG_FILE_EXTENSION = "yaml";

    /**
     * 共享ID配置
     */
    public static final String[] CONFIG_SHARED_DATAIDS = new String[]{"wfe-common.yaml"};
}
