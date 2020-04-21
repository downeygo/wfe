package xyz.imlent.wfe.uaa;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;
import xyz.imlent.wfe.core.annotation.AppName;
import xyz.imlent.wfe.core.constant.AppConstant;
import xyz.imlent.wfe.core.constant.BaseConstant;

/**
 * @author wfee
 */
@SpringCloudApplication
@ComponentScan(BaseConstant.BASE_PACKAGE)
@AppName(name = AppConstant.APP_UAA_NAME)
public class UaaApplication {
    public static void main(String[] args) {
        SpringApplication.run(UaaApplication.class, args);
    }
}
