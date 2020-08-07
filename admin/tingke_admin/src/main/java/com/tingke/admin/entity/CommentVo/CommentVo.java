package com.tingke.admin.entity.CommentVo;

import com.tingke.admin.entity.FrComment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CommentVo {


    @ApiModelProperty(value = "评论")
    private FrComment frComment;

    @ApiModelProperty(value = "子评论")
    private List<FrComment> children;

}
