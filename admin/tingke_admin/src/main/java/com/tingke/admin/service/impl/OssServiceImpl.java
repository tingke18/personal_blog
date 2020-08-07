package com.tingke.admin.service.impl;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.tingke.admin.config.OssProperties;
import com.tingke.admin.entity.AclUser;
import com.tingke.admin.exection.CastDiyException;
import com.tingke.admin.model.CommonCode;
import com.tingke.admin.model.QueryResponseResultList;
import com.tingke.admin.model.R;
import com.tingke.admin.service.AclUserService;
import com.tingke.admin.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@EnableConfigurationProperties(OssProperties.class)
@SuppressWarnings("all")
public class OssServiceImpl implements OssService {

    @Autowired
    private OssProperties ossProperties;

    @Autowired
    private AclUserService aclUserService;

    //OSS图片上传,并修改头像
    @Override
    public R uploadFileAndEditAvatar(MultipartFile file,String id) {

        try {
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = ossProperties.getEndpoint();
            // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
            String accessKeyId = ossProperties.getAccessId();
            String accessKeySecret = ossProperties.getAccessKey();
            String bucket = ossProperties.getBucket(); // 请填写您的 bucketname 。

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            String fileName = file.getOriginalFilename();

            String uuid = UUID.randomUUID().toString().replaceAll("-","");

            fileName = uuid+fileName;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());

            fileName = format+"/"+fileName;

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucket, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            fileName ="https://"+bucket+"."+ endpoint + "/" + fileName ;

            //修改头像
            AclUser aclUser = new AclUser();
            aclUser.setId(id);
            aclUser.setAvatar(fileName);

            boolean success = aclUserService.updateById(aclUser);
            if (!success){
                CastDiyException.cast(CommonCode.UPLOAD_ERROR);
            }

            return new R(CommonCode.SUCCESS,fileName);

        } catch (Exception e) {
            CastDiyException.cast(CommonCode.UPLOAD_ERROR);
        }
        return new R(CommonCode.FAIL, (QueryResponseResultList) null);
    }

    //OSS图片上传
    @Override
    public R uploadFile(MultipartFile file) {
        try {
            // Endpoint以杭州为例，其它Region请按实际情况填写。
            String endpoint = ossProperties.getEndpoint();
            // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
            String accessKeyId = ossProperties.getAccessId();
            String accessKeySecret = ossProperties.getAccessKey();
            String bucket = ossProperties.getBucket(); // 请填写您的 bucketname 。

            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            String fileName = file.getOriginalFilename();

            String uuid = UUID.randomUUID().toString().replaceAll("-","");

            fileName = uuid+fileName;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(new Date());

            fileName = format+"/"+fileName;

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            ossClient.putObject(bucket, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            fileName ="https://"+bucket+"."+ endpoint + "/" + fileName ;

            return new R(CommonCode.SUCCESS,fileName);

        } catch (Exception e) {
            CastDiyException.cast(CommonCode.UPLOAD_ERROR);
        }
        return new R(CommonCode.FAIL, (QueryResponseResultList) null);
    }


}
