package com.tingke.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tingke.admin.entity.FrUser;
import com.tingke.admin.entity.FrUserVo.FrUserVo;
import com.tingke.admin.model.R;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhx
 * @since 2020-05-31
 */
public interface FrUserService extends IService<FrUser> {

    //前端用户注册
    R AddUser(FrUserVo frUserVo);

    //根据id查询用户信息
    R queryUserById(HttpServletRequest httpServletRequest);

    //用户登陆
    R userLogin(FrUser frUser);
}
