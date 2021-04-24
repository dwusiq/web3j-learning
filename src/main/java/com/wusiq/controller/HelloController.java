package com.wusiq.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/helloWord")
public class HelloController {

    /***
     * http://localhost:8888/Quick-Start-Demo/helloWord/hello?userName=daming
     * @param username
     * @return
     */
    @RequestMapping("hello")
    public String hello(@RequestParam(name = "userName") String username) {
        String result = String.format("hello %s", username);
        log.info(result);
        return result;
    }
}
