package xyz.imlent.wfe.service.user.service;

import xyz.imlent.wfe.core.model.R;
import xyz.imlent.wfe.db.base.BaseService;
import xyz.imlent.wfe.api.user.entity.User;

/**
 * @author wfee
 */
public interface UserService extends BaseService<User> {

    /**
     * 用户名密码注册
     *
     * @param username
     * @param password
     * @return
     */
    R register(String username, String password);

    /**
     * 用户删除
     *
     * @param username
     * @return
     */
    R delete(String username);

    /**
     * 修改密码
     *
     * @param username
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    R modifyPassword(String username, String passwordOld, String passwordNew);

    /**
     * 用户集合
     *
     * @return
     */
    R getUserList();

    /**
     * 获取用户
     *
     * @return
     */
    R getUserByUsername(String username);
}
