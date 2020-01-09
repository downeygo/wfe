package xyz.imlent.wfe.service.system;

import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import xyz.imlent.wfe.core.constant.AppConstant;
import xyz.imlent.wfe.core.constant.BaseConstant;
import xyz.imlent.wfe.core.launch.WfeApplication;

/**
 * @author wfee
 */
@SpringCloudApplication
@ComponentScan(BaseConstant.BASE_PACKAGE)
@EnableFeignClients(BaseConstant.BASE_PACKAGE)
public class SystemServiceApplication {
    public static void main(String[] args) {
        WfeApplication.run(AppConstant.APP_SYSTEM_SERVICE_NAME, SystemServiceApplication.class, args);
    }
}
