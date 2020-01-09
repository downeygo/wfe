package xyz.imlent.wfe.service.user.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.imlent.wfe.core.model.R;
import xyz.imlent.wfe.api.user.entity.User;
import xyz.imlent.wfe.service.user.service.UserService;

/**
 * @author wfee
 */
@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @RequestMapping(value = "/register", method = {RequestMethod.GET, RequestMethod.POST})
    public R register(@RequestParam String username,
                      @RequestParam String password) {
        return userService.register(username, password);
    }

    @DeleteMapping(value = "/{username}")
    public R delete(@PathVariable("username") String username) {
        return userService.delete(username);
    }

    @PutMapping(value = "/modifyPassword")
    public R modifyPassword(String username, String passwordOld, String passwordNew) {
        return userService.modifyPassword(username, passwordOld, passwordNew);
    }

    @GetMapping(value = "/")
    public R getUserList() {
        return userService.getPage(new Page<>(), new User());
//        return userService.getUserList();
    }

    @GetMapping(value = "/{username}")
    public R getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }
}
