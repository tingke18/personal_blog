package com.tingke.admin.entity.DiaryVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DiaryVo {

    @ApiModelProperty(value = "开始时间")
    private String start;

    @ApiModelProperty(value = "结束时间")
    private String end;
}
