package com.tingke.admin.controller;


import com.tingke.admin.model.R;
import com.tingke.admin.service.FrCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhx
 * @since 2020-06-01
 */
@Api(value="“用户评论”管理接口",description = "“用户评论”管理，增删改查")
@RestController
@RequestMapping("/admin/fr-comment")
@CrossOrigin
public class FrCommentController {

    @Autowired
    private FrCommentService frCommentService;

    @ApiOperation("所有评论显示")
    @PostMapping("/selectComment")
    public R selectComment(){

        R responseResult = frCommentService.selectComment();

        return responseResult;
    }

    @ApiOperation("新增评论显示")
    @PostMapping("/addComment")
    public R addComment(HttpServletRequest httpServletRequest, @RequestParam String content){

        R responseResult = frCommentService.addComment(httpServletRequest,content);

        return responseResult;
    }

    @ApiOperation("查询管理员未读评论条数")
    @PostMapping("/selectNotCheckCommentCount")
    public R selectNotCheckCommentCount(){

        R responseResult = frCommentService.selectNotCheckCommentCount();

        return responseResult;
    }

    @ApiOperation("查询管理员未读评论")
    @PostMapping("/selectNotCheckComment")
    public R selectNotCheckComment(){

        R responseResult = frCommentService.selectNotCheckComment();

        return responseResult;
    }

    @ApiOperation("查询管理员已读评论")
    @PostMapping("/selectCheckComment")
    public R selectCheckComment(){

        R responseResult = frCommentService.selectCheckComment();

        return responseResult;
    }

    @ApiOperation("设置评论已读")
    @GetMapping("/editCommentRead/{id}")
    public R editCommentRead(@PathVariable("id")String id){

        R responseResult = frCommentService.editCommentRead(id);

        return responseResult;
    }

    @ApiOperation("设置所有评论已读")
    @GetMapping("/editAllCommentRead")
    public R editAllCommentRead(){

        R responseResult = frCommentService.editAllCommentRead();

        return responseResult;
    }

    @ApiOperation("管理员新增评论")
    @GetMapping("/AdminAddComment/{id}")
    public R AdminAddComment(@PathVariable("id")String id,
                             @RequestParam("content")String content){

        R responseResult = frCommentService.AdminAddComment(id,content);

        return responseResult;
    }

    @ApiOperation("删除评论")
    @GetMapping("/deleteComment/{id}")
    public R deleteComment(@PathVariable("id")String id){

        R responseResult = frCommentService.deleteComment(id);

        return responseResult;
    }

}

