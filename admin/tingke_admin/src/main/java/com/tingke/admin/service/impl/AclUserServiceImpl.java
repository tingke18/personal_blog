package com.tingke.admin.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tingke.admin.config.SendEmailProperties;
import com.tingke.admin.config.SendProperties;
import com.tingke.admin.entity.AclUser;
import com.tingke.admin.entity.AclUserVo.PasswordVo;
import com.tingke.admin.exection.CastDiyException;
import com.tingke.admin.mapper.AclUserMapper;
import com.tingke.admin.model.CommonCode;
import com.tingke.admin.model.QueryResponseResultList;
import com.tingke.admin.model.QueryResponseResultMap;
import com.tingke.admin.model.R;
import com.tingke.admin.service.AclUserService;
import com.tingke.admin.utils.SendEmailUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@EnableConfigurationProperties({SendProperties.class,SendEmailProperties.class})
@SuppressWarnings("all")
public class AclUserServiceImpl extends ServiceImpl<AclUserMapper, AclUser> implements AclUserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SendProperties sendProperties;

    @Autowired
    private SendEmailProperties sendEmailProperties;

    @Override
    @Transactional
    //用户通关过用户名密码验证
    public R loginByName(String name, String password) {
        //校验空
        if (StringUtils.isEmpty(name)||StringUtils.isEmpty(password)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }
        //查询
        QueryWrapper<AclUser> wrapper = new QueryWrapper<>();
        wrapper.eq("name",name);
        AclUser aclUser = this.getOne(wrapper);

        if (aclUser == null){
            CastDiyException.cast(CommonCode.USER_PASSWORD_ERROR);
        }

        //获得盐
        String salt = aclUser.getSalt();
        String resPassword = aclUser.getPassword();

        //对用户传入的盐加密
        String checkPassword = DigestUtils.md5Hex(password + salt);

        //判断加盐加密后与数据库密码是否相等
        if (resPassword.equals(checkPassword)){

            QueryResponseResultMap queryResultMap = new QueryResponseResultMap();
            HashMap<String, String> map = new HashMap<>();
            map.put("name",aclUser.getName());
            map.put("avatar",aclUser.getAvatar());
            map.put("id",aclUser.getId());
            map.put("role",aclUser.getRole());
            SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");//设置时间格式
            String loginTime = dateFormat.format(aclUser.getLoginTime());
            map.put("loginTime",loginTime);
            map.put("tag",aclUser.getTag());
            queryResultMap.setTotal(1);
            queryResultMap.setMap(map);

            //更新登陆信息
            AclUser upAclUser = new AclUser();
            upAclUser.setId(aclUser.getId());
            upAclUser.setLoginTime(new Date());

            this.updateById(upAclUser);
            return new R(CommonCode.SUCCESS, queryResultMap);
        }

        return new R(CommonCode.FAIL, (QueryResponseResultList) null);
    }

    @Override
    @Transactional
    //用户通关过手机号验证
    public R loginByPhone(String phone, String code) {
        //校验空
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            CastDiyException.cast(CommonCode.FAIL);
        }

        //判断是否存在手机号
        QueryWrapper wrapper = new QueryWrapper();

        wrapper.eq("phone", phone);

        AclUser aclUser = this.getOne(wrapper);

        if (aclUser != null){

            //获取验证码
            String resultCode = (String) redisTemplate.opsForValue().get("acl_user:" + phone + ":phone");

            //校验空
            if (StringUtils.isEmpty(resultCode)){
                CastDiyException.cast(CommonCode.CODE_PAST);
            }

            //判断正确
            if (resultCode.equals(code)){

                QueryResponseResultMap queryResultMap = new QueryResponseResultMap();
                HashMap<String, String> map = new HashMap<>();
                map.put("name",aclUser.getName());
                map.put("avatar",aclUser.getAvatar());
                map.put("id",aclUser.getId());
                map.put("role",aclUser.getRole());
                SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");//设置时间格式
                String loginTime = dateFormat.format(aclUser.getLoginTime());
                map.put("loginTime",loginTime);
                map.put("tag",aclUser.getTag());
                queryResultMap.setTotal(1);
                queryResultMap.setMap(map);

                //更新登陆信息
                AclUser upAclUser = new AclUser();
                upAclUser.setId(aclUser.getId());
                upAclUser.setLoginTime(new Date());

                this.updateById(upAclUser);

                //删除验证码
                redisTemplate.delete("acl_user:" + phone + ":phone");

                return new R(CommonCode.SUCCESS, queryResultMap);
            }else {
                CastDiyException.cast(CommonCode.CODE_FAIL);
            }
        }

        return new R(CommonCode.FAIL, (QueryResponseResultList) null);
    }

    @Override
    @Transactional
    //阿里发送短信
    public R getCodeByPhone(String phone) {
        if (StringUtils.isEmpty(phone)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //判断手机号验证码是否重复输入
        String is_code = redisTemplate.opsForValue().get("acl_user:" + phone + ":phone");
        if (!StringUtils.isEmpty(is_code)){
            return new R(CommonCode.CODE_REPETITION, (QueryResponseResultList) null);
        }

        //判断是否存在手机号
        QueryWrapper wrapper = new QueryWrapper();

        wrapper.eq("phone", phone);

        int count = this.count(wrapper);

        if (count>0){
            String code = String.valueOf(RandomUtils.nextInt(100000,999999));

            String codeStr = "{\"code\":"+code+"}";//json字符串

            //验证码放入redis
            redisTemplate.opsForValue().set("acl_user:"+phone+":phone",code,5, TimeUnit.MINUTES);

            //发送短信方法
            sendSmsMethod(phone,codeStr);


            return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);

        }else {
            return new R(CommonCode.PHONE_IS_SUCCESS, (QueryResponseResultList) null);
        }


    }

    //通过id查找用户信息
    @Override
    public R findUserById(String id) {
        //校验空
        if (StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //获取并校验
        AclUser aclUser = this.getById(id);
        aclUser.setPassword(null);
        aclUser.setSalt(null);
        if (aclUser == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS,aclUser);
    }

    //修改用户信息
    @Override
    public R editUser(AclUser aclUser) {
        //校验空,判断秘密是否存在，如果存在返回错误，密码在其他地方修改
        if (aclUser == null || StringUtils.isNoneBlank(aclUser.getPassword())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        boolean success = this.updateById(aclUser);

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }


        return new R(CommonCode.SUCCESS,aclUser);
    }

    //修改密码
    @Override
    public R editPassword(PasswordVo passwordVo) {

        if(passwordVo == null){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //整体检验空
        if (StringUtils.isEmpty(passwordVo.getCode())||
            StringUtils.isEmpty(passwordVo.getEmail())||
            StringUtils.isEmpty(passwordVo.getId())||
            StringUtils.isEmpty(passwordVo.getCheckPassword())||
            StringUtils.isEmpty(passwordVo.getPrePassword())||
            StringUtils.isEmpty(passwordVo.getCurrentPassword())){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //判断密码是否与之前相同
        if (passwordVo.getPrePassword().equals(passwordVo.getCurrentPassword())){
            CastDiyException.cast(CommonCode.PASSWORD_SAME);
        }

        //判断密码一致性
        if (!passwordVo.getCheckPassword().equals(passwordVo.getCurrentPassword())){
            CastDiyException.cast(CommonCode.PASSWORD_DIFFERENCE);
        }

        //判断原密码是否正确
        AclUser aclUser = this.getById(passwordVo.getId());
        String password = aclUser.getPassword();

        //输入密码加盐加密
        String checkPassword = DigestUtils.md5Hex(passwordVo.getPrePassword() + aclUser.getSalt());

        //判断原密码正确性
        if (!checkPassword.equals(password)){
            CastDiyException.cast(CommonCode.USER_PASSWORD_ERROR);
        }



        //判断验证码
        String code = passwordVo.getCode();
        String checkCode = redisTemplate.opsForValue().get("acl_user:" + passwordVo.getEmail() + ":email");

        //判断验证码是否存在
        if (StringUtils.isEmpty(checkCode)){
            CastDiyException.cast(CommonCode.CODE_PAST);
        }

        if (!code.equals(checkCode)){
            CastDiyException.cast(CommonCode.CODE_FAIL);
        }

        //更新
        AclUser uAcluser = new AclUser();
        String salt = UUID.randomUUID().toString().substring(0, 6);
        uAcluser.setSalt(salt);
        uAcluser.setId(passwordVo.getId());

        String md5Hex = DigestUtils.md5Hex(passwordVo.getCurrentPassword() + salt);
        uAcluser.setPassword(md5Hex);

        boolean success = this.updateById(uAcluser);

        //删除主键
        redisTemplate.delete("acl_user:" + passwordVo.getEmail() + ":email");

        if (!success){
            CastDiyException.cast(CommonCode.FAIL);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }

    //发送邮件
    @Override
    @Transactional
    public R sendEmail(String id,String email) {
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(id)){
            CastDiyException.cast(CommonCode.INVALID_PARAM);
        }

        //获取用户信息
        AclUser aclUser = this.getById(id);
        if (aclUser == null){
            CastDiyException.cast(CommonCode.FAIL);
        }

        //判断emails是否正确
        if (!aclUser.getEmail().equals(email)){
            CastDiyException.cast(CommonCode.FAIL);
        }

        //获取随机码
        String str = UUID.randomUUID().toString().substring(0,6);

        try {
            SendEmailUtils sendEmailUtils = new SendEmailUtils();
            sendEmailUtils.sendEmail(sendEmailProperties.getFrom(),email,"Tinkke超级管理员密码修改验证",
                    "请注意，如果是本人操作，请在相应的地方填写验证码:"+str+"，五分钟之内有效",sendEmailProperties.getSmtpHost(),sendEmailProperties.getCheckCode());
            redisTemplate.opsForValue().set("acl_user:"+email+":email",str,5,TimeUnit.MINUTES);
        } catch (Exception e) {
            CastDiyException.cast(CommonCode.SEND_EMAIL_ERROR);
        }

        return new R(CommonCode.SUCCESS, (QueryResponseResultList) null);
    }


    //阿里发送短信方法
    public CommonResponse sendSmsMethod(String phone, String code)  {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", sendProperties.getAccessKeyId(), sendProperties.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", sendProperties.getSignName());
        request.putQueryParameter("TemplateCode", sendProperties.getVerifyCodeTemplate());
        request.putQueryParameter("TemplateParam", code);
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());

            return response;
        } catch (com.aliyuncs.exceptions.ServerException e) {
            e.printStackTrace();
        } catch (com.aliyuncs.exceptions.ClientException e) {
            e.printStackTrace();
        }

        return null;

    }


}
