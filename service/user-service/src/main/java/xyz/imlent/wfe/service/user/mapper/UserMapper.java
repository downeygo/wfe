package xyz.imlent.wfe.service.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xyz.imlent.wfe.api.user.entity.User;

/**
 * @author wfee
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户is_deleted = 0、1
     *
     * @param username
     * @return
     */
    User getUser(String username);
}
