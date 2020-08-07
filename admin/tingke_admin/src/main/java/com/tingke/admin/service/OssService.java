package com.tingke.admin.service;

import com.tingke.admin.model.R;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    //OSS图片上传,并修改头像
    R uploadFileAndEditAvatar(MultipartFile file,String id);

    //OSS图片上传
    R uploadFile(MultipartFile file);
}
