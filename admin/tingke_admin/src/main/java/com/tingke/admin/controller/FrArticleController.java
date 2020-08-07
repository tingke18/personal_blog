package com.tingke.admin.controller;


import com.tingke.admin.entity.ArticleVo.ArticleVo;
import com.tingke.admin.entity.FrArticle;
import com.tingke.admin.model.R;
import com.tingke.admin.service.FrArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhx
 * @since 2020-05-21
 */
@Api(value="后台管理“文章”管理接口",description = "后台管理“文章信息”管理，增删改查")
@RestController
@RequestMapping("/admin/fr-article")
@CrossOrigin
public class FrArticleController {

    @Autowired
    private FrArticleService frArticleService;

    @ApiOperation("前端分页查询文章")
    @PostMapping("/selectFrontArticle/{page}/{limit}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页数",required=true,paramType="path"),
            @ApiImplicitParam(name="limit",value = "分页条数",required=true,paramType="path")
    })
    public R selectFrontArticle(@PathVariable("page")Long page,
                              @PathVariable("limit")Long limit){

        R responseResult = frArticleService.selectFrontArticle(page,limit);

        return responseResult;

    }

    @ApiOperation("前端搜索查询文章")
    @PostMapping("/selectSearchArticle/{page}/{limit}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页数",required=true,paramType="path"),
            @ApiImplicitParam(name="limit",value = "分页条数",required=true,paramType="path")
    })
    public R selectSearchArticle(@PathVariable("page")Long page,
                              @PathVariable("limit")Long limit,
                              @RequestBody(required = false) ArticleVo articleVo){

        R responseResult = frArticleService.selectSearchArticle(page,limit,articleVo);

        return responseResult;

    }


    @ApiOperation("分页查询文章")
    @PostMapping("/selectAllArticle/{page}/{limit}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页数",required=true,paramType="path"),
            @ApiImplicitParam(name="limit",value = "分页条数",required=true,paramType="path")
    })
    public R selectAllArticle(@PathVariable("page")Long page,
                              @PathVariable("limit")Long limit,
                              @RequestBody(required = false) ArticleVo articleVo){

        R responseResult = frArticleService.selectAllArticle(page,limit,articleVo);

        return responseResult;

    }

    @ApiOperation("根据id查询文章")
    @GetMapping("/selectArticleById/{id}")
    @ApiImplicitParam(name="id",value = "文章id",required=true,paramType="path")
    public R selectArticleById(@PathVariable("id")String id){
        R responseResult = frArticleService.selectArticleById(id);

        return responseResult;
    }

    @ApiOperation("新增文章")
    @PostMapping("/addArticle")
    public R addArticle(@RequestBody FrArticle frArticle){
        R responseResult = frArticleService.addArticle(frArticle);

        return responseResult;
    }

    @ApiOperation("逻辑删除文章")
    @GetMapping("/deleteArticle/{id}")
    @ApiImplicitParam(name="id",value = "文章id",required=true,paramType="path")
    public R deleteArticle(@PathVariable("id")String id){
        R responseResult = frArticleService.deleteArticle(id);

        return responseResult;
    }

    @ApiOperation("修改编辑文章")
    @PostMapping("/editArticle")
    public R editArticle(@RequestBody FrArticle frArticle){
        R responseResult = frArticleService.editArticle(frArticle);

        return responseResult;
    }

    @ApiOperation("修改文章发布状态")
    @GetMapping("/editArticlePublish/{id}/{isPublish}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "id主键",required=true,paramType="path"),
            @ApiImplicitParam(name="isPublish",value = "是否发布，1发布，0下线",required=true,paramType="path")
    })
    public R editArticlePublish(@PathVariable("id")String id,
                                @PathVariable("isPublish")Integer isPublish){
        R responseResult = frArticleService.editArticlePublish(id,isPublish);

        return responseResult;
    }


}

