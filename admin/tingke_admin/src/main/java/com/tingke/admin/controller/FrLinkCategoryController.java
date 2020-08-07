package com.tingke.admin.controller;


import com.tingke.admin.entity.FrLinkCategory;
import com.tingke.admin.model.R;
import com.tingke.admin.service.FrLinkCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhx
 * @since 2020-05-31
 */
@Api(value="后台管理“链接分类”管理接口",description = "后台管理“链接分类”管理，增删改查")
@RestController
@RequestMapping("/admin/fr-link-category")
@CrossOrigin
public class FrLinkCategoryController {

    @Autowired
    private FrLinkCategoryService frLinkCategoryService;

    @ApiOperation("快捷链接分类添加")
    @GetMapping("/addLinkCategory/{category}")
    @ApiImplicitParam(name="category",value = "当前分类",required=true,paramType="path")
    public R addLinkCategory(@PathVariable("category")String category){
        R responseResult = frLinkCategoryService.addLinkCategory(category);

        return responseResult;
    }

    @ApiOperation("快捷链接分类删除")
    @GetMapping("/deleteLinkCategory/{id}")
    @ApiImplicitParam(name="id",value = "当前id",required=true,paramType="path")
    public R deleteLinkCategory(@PathVariable("id")String id){
        R responseResult = frLinkCategoryService.deleteLinkCategory(id);

        return responseResult;
    }

    @ApiOperation("快捷链接分类修改")
    @PostMapping("/editLinkCategory")
    public R editLinkCategory(@RequestBody FrLinkCategory frLinkCategory){
        R responseResult = frLinkCategoryService.editLinkCategory(frLinkCategory);

        return responseResult;
    }

    @ApiOperation("快捷链接分类查询")
    @GetMapping("/selectLinkCategory")
    public R selectLinkCategory(){
        R responseResult = frLinkCategoryService.selectLinkCategory();

        return responseResult;
    }

}

