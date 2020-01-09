package xyz.imlent.wfe.uaa.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author wfee
 */
@Mapper
public interface UserDetailsMapper {
    @Select("select username, password, account_locked, account_expired from oauth_user_details where username = #{username}")
    Map<String, Object> getUserByUsername(String username);

    @Select("select role_name from user_role where username = #{username}")
    List<String> getRolesByUsername(String username);

    @Select({"<script>",
            "select distinct permission_name from role_permission where role_name in",
            "<foreach collection='roleList' item='role' open='(' separator=',' close=')'>",
            "#{role}",
            "</foreach>",
            "</script>"})
    List<String> getPermissionsByRoles(@Param("roleList") List<String> roleList);

    @Select("select permission_name from role_permission")
    List<String> getAllPermissions();
}