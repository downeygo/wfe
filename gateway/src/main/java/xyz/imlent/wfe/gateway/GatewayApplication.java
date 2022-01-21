package xyz.imlent.wfe.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import xyz.imlent.wfe.core.annotation.AppName;
import xyz.imlent.wfe.core.constant.AppConstant;
import xyz.imlent.wfe.core.constant.BaseProperties;

/**
 * @author wfee
 */
@SpringCloudApplication
@ComponentScan(BaseProperties.BASE_PACKAGE)
@AppName(name = AppConstant.APP_GATEWAY_NAME)
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
