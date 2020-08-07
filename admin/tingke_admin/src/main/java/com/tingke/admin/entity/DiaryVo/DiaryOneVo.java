package com.tingke.admin.entity.DiaryVo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DiaryOneVo {

    @ApiModelProperty(value = "年份")
    private String years;

    @ApiModelProperty(value = "子类月日")
    private List<DiaryTwoVo> children;

}
