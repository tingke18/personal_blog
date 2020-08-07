package com.tingke.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tingke.admin.entity.FrUser;
import com.tingke.admin.entity.FrUserVo.FrUserVo;
import com.tingke.admin.exection.CastDiyException;
import com.tingke.admin.mapper.FrUserMapper;
import com.tingke.admin.model.CommonCode;
import com.tingke.admin.model.QueryResponseResultList;
import com.tingke.admin.model.R;
import com.tingke.admin.service.FrUserService;
import com.tingke.admin.utils.JwtUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhx
 * @since 2020-05-31
 */
@Service
public class FrUserServiceImpl extends ServiceImpl<FrUserMapper, FrUser> implements FrUserService {

    //前端用户注册
    @Override
    @Transactional
    public R AddUser(FrUserVo frUserVo) {

        //校空
        if (frUserVo == null){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        String checkEmail = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

        //判断邮箱是否存在
        QueryWrapper<FrUser> wrapper = new QueryWrapper<>();
        wrapper.eq("email",frUserVo.getEmail()).or().eq("name",frUserVo.getName());

        Integer eCount = baseMapper.selectCount(wrapper);
        if (eCount >0){
            CastDiyException.cast(CommonCode.EMAIL_NAME_EXIST);
        }


        //校验邮箱正确性
        if (!frUserVo.getEmail().matches(checkEmail)){
            CastDiyException.cast(CommonCode.EMAIL_FAIL);
        }

        //密码判空
        if (StringUtils.isEmpty(frUserVo.getPassword())||StringUtils.isEmpty(frUserVo.getCheckPassword())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //校验密码是否一致
        if (!frUserVo.getCheckPassword().equals(frUserVo.getPassword())){
            CastDiyException.cast(CommonCode.PASSWORD_DIFFERENCE);
        }

        FrUser frUser = new FrUser();
        //对象copy
        BeanUtils.copyProperties(frUserVo,frUser);

        //生成盐
        String str = UUID.randomUUID().toString().substring(0,6);
        //加盐
        frUser.setSalt(str);
        //加盐加密
        frUser.setPassword(DigestUtils.md5Hex(frUser.getPassword()+str));
        //判断是否成功
        boolean success = this.save(frUser);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        String token = JwtUtils.getJwtToken(frUser.getId(),frUser.getName());


        return new R(CommonCode.SUCCESS,token);
    }

    //根据id查询用户信息
    @Override
    public R queryUserById(HttpServletRequest httpServletRequest) {
        //获取用户id
        String id = JwtUtils.getMemberIdByJwtToken(httpServletRequest);

        FrUser frUser = this.getById(id);

        if (frUser == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS,frUser);
    }

    //用户登陆
    @Override
    public R userLogin(FrUser frUser) {

        if (frUser == null ){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //判断空
        if (StringUtils.isNoneBlank(frUser.getEmail())){
            QueryWrapper<FrUser> wrapper = new QueryWrapper<>();
            wrapper.eq("email",frUser.getEmail());

            //获取用户信息
            FrUser rFrUser = this.getOne(wrapper);

            if (rFrUser == null){
                CastDiyException.cast(CommonCode.USER_PASSWORD_ERROR);
            }

            //获取密码进行校验
            String checkPassword = rFrUser.getPassword();

            String password = DigestUtils.md5Hex(frUser.getPassword() + rFrUser.getSalt());

            if (!password.equals(checkPassword)){
                CastDiyException.cast(CommonCode.USER_PASSWORD_ERROR);
            }

            String token = JwtUtils.getJwtToken(rFrUser.getId(),rFrUser.getName());

            return new R(CommonCode.SUCCESS,token);
        }

        return new R(CommonCode.FAIL, (QueryResponseResultList) null);
    }
}
