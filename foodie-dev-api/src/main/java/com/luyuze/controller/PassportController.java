package com.luyuze.controller;

import com.luyuze.pojo.bo.UserBO;
import com.luyuze.service.UserService;
import com.luyuze.utils.MyJsonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/register")
    public MyJsonResult register(@RequestBody UserBO userBO) {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();
        // 0、判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPassword)) {
            return MyJsonResult.errorMsg("用户名或密码不能为空");
        }
        // 1、查询用户名是否存在
        if (userService.queryUsernameIsExist(username)) {
            return MyJsonResult.errorMsg("用户名已经存在");
        }
        // 2、密码长度不能小于6位
        if (password.length() < 6) {
            return MyJsonResult.errorMsg("密码长度不能小于6");
        }
        // 3、判断两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return MyJsonResult.errorMsg("两次密码输入不一致");
        }
        // 4、实现注册
        userService.createUser(userBO);
        return MyJsonResult.ok();
    }
}
