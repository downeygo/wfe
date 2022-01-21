package xyz.imlent.wfe.service.system.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.imlent.wfe.api.system.entity.Role;
import xyz.imlent.wfe.core.model.R;
import xyz.imlent.wfe.service.system.service.RoleService;

/**
 * @author wfee
 */
@RestController
@AllArgsConstructor
@RequestMapping(value = "/role")
public class RoleController {
    private RoleService roleService;

    @PostMapping
    public R addRole(@RequestBody Role role) {
        return roleService.addRole(role);
    }
}
