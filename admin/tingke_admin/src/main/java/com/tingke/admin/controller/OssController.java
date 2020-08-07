package com.tingke.admin.controller;


import com.tingke.admin.model.R;
import com.tingke.admin.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(value="图片上传接口",description = "实现图片上传")
@RestController
@RequestMapping("TingkeOss/oss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation("OSS图片上传,并修改头像")
    @PostMapping("/{id}")
    public String uploadFileAndEditAvatar(MultipartFile file, @PathVariable("id")String id){

        R responseResult = ossService.uploadFileAndEditAvatar(file,id);

        String str = (String) responseResult.getObject();

        String jsonStr = "{\"url\":"+str+"}";

        return jsonStr;
    }
    @ApiOperation("OSS图片上传")
    @PostMapping
    public R uploadFile(MultipartFile file){

        R responseResult = ossService.uploadFile(file);

        return responseResult;
    }

}
