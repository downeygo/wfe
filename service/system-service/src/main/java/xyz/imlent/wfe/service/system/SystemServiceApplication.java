package xyz.imlent.wfe.service.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import xyz.imlent.wfe.core.constant.AppConstant;
import xyz.imlent.wfe.core.constant.BaseConstant;
import xyz.imlent.wfe.core.constant.BaseProperties;
import xyz.imlent.wfe.core.launch.WfeApplication;

/**
 * @author wfee
 */
@SpringCloudApplication
@ComponentScan(BaseProperties.BASE_PACKAGE)
@EnableFeignClients(BaseProperties.BASE_PACKAGE)
public class SystemServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SystemServiceApplication.class, args);
    }
}
