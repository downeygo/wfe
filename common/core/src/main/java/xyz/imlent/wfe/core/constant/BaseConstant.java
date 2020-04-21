package xyz.imlent.wfe.core.constant;

import xyz.imlent.wfe.core.annotation.AppEnv;

/**
 * @author wfee
 */
public interface BaseConstant {
    /**
     * 包名
     */
    String BASE_PACKAGE = "xyz.imlent.wfe";

    String ENV_DEV = AppEnv.DEV.name().toLowerCase();
    String ENV_TEST = AppEnv.TEST.name().toLowerCase();
    String ENV_PROD = AppEnv.PROD.name().toLowerCase();

    String[] ENVS = new String[]{ENV_DEV, ENV_TEST, ENV_PROD};
}
