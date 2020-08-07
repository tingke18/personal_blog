package com.tingke.admin.entity.LinkVo;

import com.tingke.admin.entity.FrLink;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class LinkVo {

    @ApiModelProperty(value = "分类")
    private String category;

    @ApiModelProperty(value = "link集合")
    private List<FrLink> frLinks;
}
