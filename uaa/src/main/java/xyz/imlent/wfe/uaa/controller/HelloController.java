package xyz.imlent.wfe.uaa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wfee
 */
@RestController
public class HelloController {
    @GetMapping(value = "/hello")
    public String hello() {
        int i = 1 / 0;
        return "hello";
    }
}
