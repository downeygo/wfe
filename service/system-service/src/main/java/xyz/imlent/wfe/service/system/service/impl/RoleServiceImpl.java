package xyz.imlent.wfe.service.system.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.imlent.wfe.api.system.entity.Role;
import xyz.imlent.wfe.core.exception.ServiceException;
import xyz.imlent.wfe.core.model.R;
import xyz.imlent.wfe.db.base.BaseServiceImpl;
import xyz.imlent.wfe.service.system.mapper.RoleMapper;
import xyz.imlent.wfe.service.system.service.RoleService;

/**
 * @author wfee
 */
@Service
@AllArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService  {

    private RoleMapper roleMapper;

    @Override
    public R addRole(Role role) {
        Integer insert = roleMapper.insert(role);
        if (insert == 0) {
            throw new ServiceException("角色添加失败");
        }
        return R.success("角色添加成功");
    }

    @Override
    public R addUserRole(String username, String rolename) {
        Integer insert = roleMapper.addUserRole(username, rolename);
        if (insert == 0) {
            throw new ServiceException("用户角色添加失败");
        }
        return R.success("用户角色添加成功");
    }
}
