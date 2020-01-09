package xyz.imlent.wfe.api.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import xyz.imlent.wfe.core.constant.AppConstant;
import xyz.imlent.wfe.core.model.R;

/**
 * @author wfee
 */
@FeignClient(
        value = AppConstant.APP_USER_SERVICE_NAME,
        fallback = IUserClientFallback.class
)
public interface IUserClient {

    String API_PREFIX = "/user";

    /**
     * 注册
     *
     * @param username
     * @param password
     * @return
     */
    @GetMapping(value = API_PREFIX + "/register")
    R register(@RequestParam(value = "username") String username,
               @RequestParam(value = "password") String password);

    @DeleteMapping(value = API_PREFIX + "/{username}")
    R delete(@RequestParam(value = "username") @PathVariable("username") String username);
}
