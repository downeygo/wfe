package xyz.imlent.wfe.service.system.service;

import xyz.imlent.wfe.api.system.entity.Role;
import xyz.imlent.wfe.core.model.R;
import xyz.imlent.wfe.db.base.BaseService;

/**
 * @author wfee
 */
public interface RoleService extends BaseService<Role> {
    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    R addRole(Role role);

    /**
     * 为用户添加角色
     *
     * @param username
     * @param rolename
     * @return
     */
    R addUserRole(String username, String rolename);
}
