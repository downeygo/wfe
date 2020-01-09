package xyz.imlent.wfe.service.user;

import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import xyz.imlent.wfe.core.constant.AppConstant;
import xyz.imlent.wfe.core.constant.BaseConstant;
import xyz.imlent.wfe.core.launch.WfeApplication;

/**
 * @author wfee
 */
@EnableAsync
@SpringCloudApplication
@ComponentScan(BaseConstant.BASE_PACKAGE)
@EnableFeignClients(BaseConstant.BASE_PACKAGE)
public class UserServiceApplication {
    public static void main(String[] args) {
        WfeApplication.run(AppConstant.APP_USER_SERVICE_NAME, UserServiceApplication.class, args);
    }
}
