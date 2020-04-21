package xyz.imlent.wfe.service.user;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import xyz.imlent.wfe.core.annotation.AppName;
import xyz.imlent.wfe.core.constant.AppConstant;
import xyz.imlent.wfe.core.constant.BaseConstant;

/**
 * @author wfee
 */
@EnableAsync
@SpringCloudApplication
@ComponentScan(BaseConstant.BASE_PACKAGE)
@EnableFeignClients(BaseConstant.BASE_PACKAGE)
@AppName(name = AppConstant.APP_USER_SERVICE_NAME)
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
