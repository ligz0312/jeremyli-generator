package com.jeremyli.jeremygenerator.controller;

import com.jeremyli.jeremygenerator.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dict")
public class DataDictController {

    @RequestMapping("/test")
    public Result<Object> test(){


        return Result.ok("success");

    }
}
