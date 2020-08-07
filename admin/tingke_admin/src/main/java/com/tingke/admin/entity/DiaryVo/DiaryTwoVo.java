package com.tingke.admin.entity.DiaryVo;

import com.tingke.admin.entity.AclDiary;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DiaryTwoVo {

    @ApiModelProperty(value = "月日")
    private String months;

    @ApiModelProperty(value = "子类详细信息")
    private AclDiary diary;


}
