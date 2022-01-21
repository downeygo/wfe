package xyz.imlent.wfe.core.constant;

import xyz.imlent.wfe.core.annotation.AppEnvEnum;

/**
 * @author wfee
 * @deprecated 最新 {@link xyz.imlent.wfe.core.constant.BaseProperties}
 */
@Deprecated
public interface BaseConstant {
    /**
     * 包名
     */
    String BASE_PACKAGE = "xyz.imlent.wfe";

    String ENV_DEV = AppEnvEnum.DEV.name().toLowerCase();
    String ENV_TEST = AppEnvEnum.TEST.name().toLowerCase();
    String ENV_PROD = AppEnvEnum.PROD.name().toLowerCase();
    String[] ENVS = new String[]{ENV_DEV, ENV_TEST, ENV_PROD};
}
