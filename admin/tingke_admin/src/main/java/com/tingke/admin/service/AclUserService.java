package com.tingke.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tingke.admin.entity.AclUser;
import com.tingke.admin.entity.AclUserVo.PasswordVo;
import com.tingke.admin.model.R;

public interface AclUserService extends IService<AclUser> {

    //用户通关过用户名密码验证
    R loginByName(String name, String password);

    //用户通关过手机号验证
    R loginByPhone(String phone, String code);

    //通过阿里云发送信息
    R getCodeByPhone(String phone);

    //通过id查找用户信息
    R findUserById(String id);

    //修改用户信息
    R editUser(AclUser aclUser);

    //修改用户密码
    R editPassword(PasswordVo passwordVo);

    //发送邮件
    R sendEmail(String id,String email);

}
