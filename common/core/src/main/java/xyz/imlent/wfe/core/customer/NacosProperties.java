package xyz.imlent.wfe.core.customer;

/**
 * @author downey
 * @date 2022/1/21
 */
public interface NacosProperties {
    /**
     * nacos地址
     */
    String ADDR = "192.168.0.126:8848";

    /**
     * DEFAULT_GROUP
     */
    String DEFAULT_GROUP = "DEFAULT_GROUP";

    /**
     * 匹配wfe开头配置文件
     */
    String CONFIG_PREFIX = "wfe-";

    /**
     * 配置文件类型
     */
    String CONFIG_FILE_EXTENSION = "yaml";

    /**
     * 共享ID配置
     */
    String[] CONFIG_SHARED_DATAIDS = new String[]{"wfe-common.yaml"};
}
