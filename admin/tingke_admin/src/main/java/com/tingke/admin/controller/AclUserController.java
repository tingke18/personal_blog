package com.tingke.admin.controller;


import com.tingke.admin.entity.AclUser;
import com.tingke.admin.entity.AclUserVo.PasswordVo;
import com.tingke.admin.model.R;
import com.tingke.admin.service.AclUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhx
 * @since 2020-05-17
 */
@Api(value="后台管理“超级管理员”管理接口",description = "后台管理“用户登陆”管理，增删改查")
@RestController
@RequestMapping("/admin/acl-user")
@CrossOrigin
public class AclUserController {

    @Autowired
    private AclUserService aclUserService;


    @ApiOperation("通过用户名密码校验")
    @PostMapping("/loginByName")
    @ApiImplicitParams({
            @ApiImplicitParam(name="name",value = "用户名",required=true,paramType="query"),
            @ApiImplicitParam(name="password",value = "密码",required=true,paramType="query")
    })
    public R loginByName(@RequestParam(value = "name",required = true)String name,
                                @RequestParam(value = "password",required = true)String password
                      ){
        R responseResult = aclUserService.loginByName(name,password);

        return responseResult;
    }

    @ApiOperation("通过手机验证码校验")
    @PostMapping("/loginByPhone")
    @ApiImplicitParams({
            @ApiImplicitParam(name="phone",value = "手机号",required=true,paramType="query"),
            @ApiImplicitParam(name="code",value = "验证码",required=true,paramType="query")
    })
    public R loginByPhone(@RequestParam(value = "phone",required = true)String phone,
                                           @RequestParam(value = "code",required = true)String code
    ){
        R responseResult = aclUserService.loginByPhone(phone,code);

        return responseResult;
    }


    @ApiOperation("通过阿里云发送信息")
    @GetMapping("/send/{phone}")
    @ApiImplicitParam(name="phone",value = "手机号",required=true,paramType="path")
    public R getCodeByPhone(@PathVariable("phone")String phone){
        R responseResult = aclUserService.getCodeByPhone(phone);

        return responseResult;
    }

    @ApiOperation("通过id查找用户信息")
    @GetMapping("/findUserById/{id}")
    @ApiImplicitParam(name="id",value = "用户id",required=true,paramType="path")
    public R findUserById(@PathVariable("id")String id){
        R responseResult = aclUserService.findUserById(id);

        return responseResult;
    }

    @ApiOperation("修改用户信息")
    @PostMapping("/editUser")
    public R editUser(@RequestBody AclUser aclUser){
        R responseResult = aclUserService.editUser(aclUser);

        return responseResult;
    }

    @ApiOperation("修改用户密码")
    @PostMapping("/editPassword")
    public R editPassword(@RequestBody PasswordVo passwordVo){
        R responseResult = aclUserService.editPassword(passwordVo);

        return responseResult;
    }

    @ApiOperation("发送邮件")
    @PostMapping("/sendEmail/{id}/{email}")
    public R sendEmail(@PathVariable(value = "email",required = true) String email,
                       @PathVariable(value = "id",required = true)  String id){
        R responseResult = aclUserService.sendEmail(id,email);

        return responseResult;
    }

}

