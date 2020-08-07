package com.tingke.admin.entity.FrUserVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FrUserVo {

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "用户名")
    private String name;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "校验密码")
    private String checkPassword;

}
