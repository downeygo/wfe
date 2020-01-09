package xyz.imlent.wfe.service.user.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.imlent.wfe.api.system.entity.Role;
import xyz.imlent.wfe.api.system.feign.IRoleClient;
import xyz.imlent.wfe.api.user.entity.User;
import xyz.imlent.wfe.core.exception.ServiceException;
import xyz.imlent.wfe.core.model.R;
import xyz.imlent.wfe.db.base.BaseServiceImpl;
import xyz.imlent.wfe.log.annotation.ApiLog;
import xyz.imlent.wfe.service.user.mapper.UserMapper;
import xyz.imlent.wfe.service.user.service.UserService;

/**
 * @author wfee
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {
    private UserMapper userMapper;

    private PasswordEncoder passwordEncoder;

    private IRoleClient roleClient;

    @ApiLog
    @Override
    @GlobalTransactional
    public R register(String username, String password) {
        User user = userMapper.getUser(username);
        if (ObjectUtils.allNotNull(user)) {
            throw new ServiceException("用户名已存在");
        }
        userMapper.insert(User.builder().username(username).password(passwordEncoder.encode(password)).build());
        roleClient.addRole(Role.builder().name(username).description(username).expression(username).build());
        return R.success("注册成功");
    }

    @ApiLog
    @Override
    public R delete(String username) {
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (!ObjectUtils.allNotNull(user)) {
            throw new ServiceException("当前用户不存在");
        }
        userMapper.deleteById(user.getId());
        return R.success("删除成功");
    }

    @ApiLog
    @Override
    public R modifyPassword(String username, String passwordOld, String passwordNew) {
        User user = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (!ObjectUtils.allNotNull(user)) {
            throw new ServiceException("当前用户不存在");
        }
        if (!passwordEncoder.matches(passwordOld, user.getPassword())) {
            throw new ServiceException("旧密码不正确");
        }
        if (StringUtils.equals(passwordOld, passwordNew)) {
            throw new ServiceException("新密码不能与旧密码一样");
        }
        userMapper.update(User.builder().id(user.getId()).build(), Wrappers.<User>lambdaUpdate().eq(User::getUsername, username));
        return R.success("修改密码成功");
    }

    @ApiLog
    @Override
    public R getUserList() {
        return R.success(this.list());
    }

    @ApiLog
    @Override
    public R getUserByUsername(String username) {
        return R.success(this.getOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username)));
    }
}
