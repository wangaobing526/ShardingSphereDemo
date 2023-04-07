package com.bus.businessdiaryapp.web.controller;

import com.bus.businessdiaryapp.util.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/test")
public class TestController {
    @RequestMapping(value = "index")
    public JsonResult<String> index() {
        return JsonResult.ok("hello");
    }
}
