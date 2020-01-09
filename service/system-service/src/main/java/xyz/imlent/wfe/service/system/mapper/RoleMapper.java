package xyz.imlent.wfe.service.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import xyz.imlent.wfe.api.system.entity.Role;

/**
 * @author wfee
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    /**
     * 添加用户角色
     *
     * @param username
     * @param password
     * @return
     */
    Integer addUserRole(@Param("username") String username, @Param("password") String password);
}
