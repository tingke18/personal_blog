package com.tingke.admin.controller;


import com.tingke.admin.entity.FrLink;
import com.tingke.admin.model.R;
import com.tingke.admin.service.FrLinkService;
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
 * @since 2020-05-31
 */
@Api(value="后台管理“链接”管理接口",description = "后台管理“链接”管理，增删改查")
@RestController
@RequestMapping("/admin/fr-link")
@CrossOrigin
public class FrLinkController {

    @Autowired
    private FrLinkService frLinkService;

    @ApiOperation("快捷链接显示")
    @PostMapping("/selectLink")
    public R selectLink(){
        R responseResult = frLinkService.selectLink();

        return responseResult;
    }

    @ApiOperation("快捷链接分页显示")
    @PostMapping("/selectPageLink/{page}/{limit}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页数",required=true,paramType="path"),
            @ApiImplicitParam(name="limit",value = "分页条数",required=true,paramType="path"),
            @ApiImplicitParam(name="condition",value = "条件",required=false,paramType="query"),
    })
    public R selectPageLink(@PathVariable("page")Long page,
                            @PathVariable("limit")Long limit,
                            @RequestParam(required = false) String condition){
        R responseResult = frLinkService.selectPageLink(page,limit,condition);

        return responseResult;
    }

    @ApiOperation("快捷链接添加")
    @PostMapping("/addLink")
    public R addLink(@RequestBody FrLink frLink){
        R responseResult = frLinkService.addLink(frLink);

        return responseResult;
    }

    @ApiOperation("快捷链接删除")
    @GetMapping("/deleteLink/{id}")
    @ApiImplicitParam(name="id",value = "当前id",required=true,paramType="path")
    public R deleteLink(@PathVariable("id")String id){
        R responseResult = frLinkService.deleteLink(id);

        return responseResult;
    }

    @ApiOperation("快捷链接修改")
    @PostMapping("/editLink")
    public R editLink(@RequestBody FrLink frLink){
        R responseResult = frLinkService.editLink(frLink);

        return responseResult;
    }

    @ApiOperation("根据id快捷链接")
    @GetMapping("/selectLinkById/{id}")
    @ApiImplicitParam(name="id",value = "当前id",required=true,paramType="path")
    public R selectLinkById(@PathVariable("id")String id){
        R responseResult = frLinkService.selectLinkById(id);

        return responseResult;
    }

}

