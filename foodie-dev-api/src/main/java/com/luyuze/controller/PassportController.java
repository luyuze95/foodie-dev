package com.luyuze.controller;

import com.luyuze.pojo.Users;
import com.luyuze.pojo.bo.UserBO;
import com.luyuze.service.UserService;
import com.luyuze.utils.CookieUtils;
import com.luyuze.utils.JsonUtils;
import com.luyuze.utils.MD5Utils;
import com.luyuze.utils.MyJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登陆", tags = {"用于注册登陆的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
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

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public MyJsonResult regist(@RequestBody UserBO userBO,
                               HttpServletRequest request,
                               HttpServletResponse response) {
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
        Users userResult = userService.createUser(userBO);
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);
        return MyJsonResult.ok();
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public MyJsonResult login(@RequestBody UserBO userBO,
                              HttpServletRequest request,
                              HttpServletResponse response) throws Exception {
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        // 0、判断用户名和密码必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return MyJsonResult.errorMsg("用户名或密码不能为空");
        }
        // 1、实现登陆
        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));
        if (userResult == null) {
            return MyJsonResult.errorMsg("用户名或密码不正确");
        }
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);
        return MyJsonResult.ok(userResult);
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public MyJsonResult logout(@RequestParam String userId,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, "user");
        // TODO 用户退出登陆，需要清空购物车
        // TODO 在分布式会话中需要清除用户数据
        return MyJsonResult.ok();
    }
}
