package com.tingke.admin.controller;


import com.tingke.admin.entity.AclDiary;
import com.tingke.admin.entity.DiaryVo.DiaryVo;
import com.tingke.admin.model.R;
import com.tingke.admin.service.AclDiaryService;
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
 * @since 2020-05-30
 */
@Api(value="后台管理“个人日志”管理接口",description = "后台管理“个人日志”管理，增删改查")
@RestController
@RequestMapping("/admin/acl-diary")
@CrossOrigin
public class AclDiaryController {

    @Autowired
    private AclDiaryService aclDiaryService;

    @ApiOperation("封装个人日志显示")
    @GetMapping("/selectDiaryVo")
    public R selectDiaryVo(){

        R responseResult = aclDiaryService.selectDiaryVo();

        return responseResult;
    }


    @ApiOperation("个人日志显示")
    @GetMapping("/selectDiary/{page}/{limit}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页数",required=true,paramType="path"),
            @ApiImplicitParam(name="limit",value = "分页条数",required=true,paramType="path")
    })
    public R selectDiary(@PathVariable("page")Long page,
                         @PathVariable("limit")Long limit){

        R responseResult = aclDiaryService.selectDiary(page,limit);

        return responseResult;
    }

    @ApiOperation("后台个人日志查询")
    @PostMapping("/selectAllDiary/{page}/{limit}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页数",required=true,paramType="path"),
            @ApiImplicitParam(name="limit",value = "分页条数",required=true,paramType="path")
    })
    public R selectAllDiary(@PathVariable("page")Long page,
                            @PathVariable("limit")Long limit,
                            @RequestBody(required = false) DiaryVo diaryVo){

        R responseResult = aclDiaryService.selectAllDiary(page,limit,diaryVo);

        return responseResult;
    }

    @ApiOperation("根据id查询随笔")
    @GetMapping("/selectDiaryById/{id}")
    @ApiImplicitParam(name="id",value = "文章id",required=true,paramType="path")
    public R selectDiaryById(@PathVariable("id")String id){
        R responseResult = aclDiaryService.selectDiaryById(id);

        return responseResult;
    }

    @ApiOperation("新增随笔")
    @PostMapping("/addDiary")
    public R addDiary(@RequestBody AclDiary aclDiary){
        R responseResult = aclDiaryService.addDiary(aclDiary);

        return responseResult;
    }

    @ApiOperation("修改随笔")
    @PostMapping("/editDiary")
    public R editDiary(@RequestBody AclDiary aclDiary){
        R responseResult = aclDiaryService.editDiary(aclDiary);

        return responseResult;
    }

    @ApiOperation("删除随笔")
    @GetMapping("/deleteDiary/{id}")
    @ApiImplicitParam(name="id",value = "文章id",required=true,paramType="path")
    public R deleteDiary(@PathVariable("id") String id){
        R responseResult = aclDiaryService.deleteDiary(id);

        return responseResult;
    }

    @ApiOperation("编辑随笔发布状态")
    @GetMapping("/editDiaryPublish/{id}/{isPublish}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="id",value = "id主键",required=true,paramType="path"),
            @ApiImplicitParam(name="isPublish",value = "是否发布，1发布，0下线",required=true,paramType="path")
    })
    public R editDiaryPublish(@PathVariable("id")String id,
                              @PathVariable("isPublish")Integer isPublish){
        R responseResult = aclDiaryService.editDiaryPublish(id,isPublish);

        return responseResult;
    }

}

