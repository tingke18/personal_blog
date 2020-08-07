package com.tingke.admin.controller;


import com.tingke.admin.entity.FrUser;
import com.tingke.admin.entity.FrUserVo.FrUserVo;
import com.tingke.admin.model.R;
import com.tingke.admin.service.FrUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhx
 * @since 2020-05-31
 */
@Api(value="前端管理“前端用户”管理接口",description = "前端”个人用户“管理，增删改查")
@RestController
@RequestMapping("/admin/fr-user")
@CrossOrigin
public class FrUserController {

    @Autowired
    private FrUserService frUserService;


    @ApiOperation("前端用户注册")
    @PostMapping("/addUser")
    public R AddUser(@RequestBody FrUserVo frUserVo){
        R responseResult = frUserService.AddUser(frUserVo);

        return responseResult;
    }

    @ApiOperation("根据id查询用户信息")
    @PostMapping("/queryUserById")
    public R queryUserById(HttpServletRequest httpServletRequest){
        R responseResult = frUserService.queryUserById(httpServletRequest);

        return responseResult;
    }

    @ApiOperation("用户登陆")
    @PostMapping("/userLogin")
    public R userLogin(@RequestBody FrUser frUser){
        R responseResult = frUserService.userLogin(frUser);

        return responseResult;
    }





}

