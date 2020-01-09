package xyz.imlent.wfe.gateway;

import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import xyz.imlent.wfe.core.constant.AppConstant;
import xyz.imlent.wfe.core.constant.BaseConstant;
import xyz.imlent.wfe.core.launch.WfeApplication;

/**
 * @author wfee
 */
@SpringCloudApplication
@ComponentScan(BaseConstant.BASE_PACKAGE)
public class GatewayApplication {
    public static void main(String[] args) {
        WfeApplication.run(AppConstant.APP_GATEWAT_NAME, GatewayApplication.class, args);
    }
}
