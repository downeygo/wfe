package xyz.imlent.wfe.api.system.feign;

import org.springframework.stereotype.Component;
import xyz.imlent.wfe.api.system.entity.Role;
import xyz.imlent.wfe.core.model.R;

/**
 * @author wfee
 */
@Component
public class IRoleClientFallback implements IRoleClient {
    @Override
    public R addRole(Role role) {
        return R.fail("添加角色异常");
    }
}
