package com.luyuze.controller;

import com.luyuze.service.UserService;
import com.luyuze.utils.MyJsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public MyJsonResult usernameIsExist(@RequestParam String username) {
        // 1、判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return MyJsonResult.errorMsg("用户名不能为空");
        }
        // 2、查找注册的用户名是否存在
        if (userService.queryUsernameIsExist(username)) {
            return MyJsonResult.errorMsg("用户名已经存在");
        }
        // 3、请求成功，用户名没有重复
        return MyJsonResult.ok();
    }
}
