package xyz.imlent.wfe.uaa.bean;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import xyz.imlent.wfe.uaa.mapper.UserDetailsMapper;

import java.util.Map;

/**
 * @author wfee
 */
@Component
@AllArgsConstructor
public class UserDetailsBean implements UserDetailsService {
    // private DataSource dataSource;

    private UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> user = userDetailsMapper.getUserByUsername(username);
        if (!ObjectUtils.isEmpty(user)) {
            String[] roles = userDetailsMapper.listRolesByUsername(username);
            String[] permissions = {};
            if (!ObjectUtils.isEmpty(roles)) {
                if (ArrayUtils.contains(roles, "root")) {
                    permissions = userDetailsMapper.listAllPermissions();
                } else {
                    permissions = userDetailsMapper.listPermissionsByRoles(roles);
                }
            }
            return User.withUsername((String) user.get("username"))
                    .password((String) user.get("password"))
                    .accountLocked("1".equals(user.get("account_locked")))
                    .accountLocked("1".equals(user.get("account_expired")))
                    .roles(roles)
                    .authorities(permissions)
                    .build();
        }
        return null;
    }

    // @Bean
    // public UserDetailsService userDetailsService() {
    //     return new JdbcUserDetailsManager(dataSource);
    // }
}
