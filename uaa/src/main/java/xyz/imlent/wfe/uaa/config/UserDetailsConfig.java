package xyz.imlent.wfe.uaa.config;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import xyz.imlent.wfe.uaa.mapper.UserDetailsMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wfee
 */
@Component
@AllArgsConstructor
public class UserDetailsConfig implements UserDetailsService {
    private UserDetailsMapper userDetailsMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> user = userDetailsMapper.getUserByUsername(username);
        if (!ObjectUtils.isEmpty(user)) {
            List<String> roles = userDetailsMapper.getRolesByUsername(username);
            List<String> permissions = new ArrayList<>();
            if (!ObjectUtils.isEmpty(roles)) {
                if (roles.contains("admin")) {
                    permissions = userDetailsMapper.getAllPermissions();
                } else {
                    permissions = userDetailsMapper.getPermissionsByRoles(roles);
                }
            }
            return User.withUsername((String) user.get("username"))
                    .password((String) user.get("password"))
                    .accountLocked("1".equals(user.get("account_locked")))
                    .accountLocked("1".equals(user.get("account_expired")))
                    .roles(roles.toArray(new String[roles.size()]))
                    .authorities(permissions.toArray(new String[permissions.size()]))
                    .build();
        }
        return null;
    }
}
