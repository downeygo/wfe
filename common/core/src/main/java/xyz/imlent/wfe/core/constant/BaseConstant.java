package xyz.imlent.wfe.core.constant;

/**
 * @author wfee
 */
public interface BaseConstant {
    /**
     * 包名
     */
    String BASE_PACKAGE = "xyz.imlent.wfe";

    String ENV_DEV = "dev";
    String ENV_TEST = "test";
    String ENV_PROD = "prod";

    String[] ENVS = new String[]{ENV_DEV, ENV_TEST, ENV_PROD};
}
