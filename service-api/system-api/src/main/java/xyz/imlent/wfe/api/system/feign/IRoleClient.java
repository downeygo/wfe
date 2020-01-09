package xyz.imlent.wfe.api.system.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import xyz.imlent.wfe.api.system.entity.Role;
import xyz.imlent.wfe.core.constant.AppConstant;
import xyz.imlent.wfe.core.model.R;

/**
 * @author wfee
 */
@FeignClient(
        value = AppConstant.APP_SYSTEM_SERVICE_NAME,
        fallback = IRoleClientFallback.class
)
public interface IRoleClient {
    String API_PREFIX = "/sys/role";

    @PostMapping(value = API_PREFIX)
    R addRole(@RequestBody Role role);
}
