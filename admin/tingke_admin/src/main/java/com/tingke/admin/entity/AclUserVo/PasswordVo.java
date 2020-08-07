package com.tingke.admin.entity.AclUserVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PasswordVo {

    @ApiModelProperty("用户id")
    private String id;

    @ApiModelProperty("原始密码")
    private String prePassword;

    @ApiModelProperty("修改的密码")
    private String currentPassword;

    @ApiModelProperty("密码验证密码")
    private String checkPassword;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("校验码")
    private String code;
}
