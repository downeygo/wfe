package xyz.imlent.wfe.api.user.feign;

import org.springframework.stereotype.Component;
import xyz.imlent.wfe.core.model.R;

/**
 * @author wfee
 */
@Component
public class IUserClientFallback implements IUserClient {
    @Override
    public R register(String username, String password) {
        return R.fail("注册异常");
    }

    @Override
    public R delete(String username) {
        return R.fail("删除异常");
    }
}
