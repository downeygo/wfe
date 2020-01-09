package xyz.imlent.wfe.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author wfee
 */
@RestController
public class HelloController {
    @GetMapping(value = "/hello")
    public String hello() {
        return "hello";
    }
}
