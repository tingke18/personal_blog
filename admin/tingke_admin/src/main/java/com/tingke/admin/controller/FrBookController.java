package com.tingke.admin.controller;


import com.tingke.admin.entity.FrBook;
import com.tingke.admin.model.R;
import com.tingke.admin.service.FrBookService;
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
@Api(value="后台管理“推荐图书”管理接口",description = "后台管理“推荐图书”管理，增删改查")
@RestController
@RequestMapping("/admin/fr-book")
@CrossOrigin
public class FrBookController {

    @Autowired
    private FrBookService frBookService;

    @ApiOperation("根据id推荐书籍显示")
    @PostMapping("/selectBookById/{id}")
    public R selectBookById(@PathVariable("id")String id){

        R responseResult = frBookService.selectBookById(id);

        return responseResult;
    }

    @ApiOperation("图书分页显示")
    @PostMapping("/selectPageBook/{page}/{limit}")
    @ApiImplicitParams({
            @ApiImplicitParam(name="page",value = "当前页数",required=true,paramType="path"),
            @ApiImplicitParam(name="limit",value = "分页条数",required=true,paramType="path"),
            @ApiImplicitParam(name="condition",value = "条件",required=false,paramType="query"),
    })
    public R selectPageBook(@PathVariable("page")Long page,
                            @PathVariable("limit")Long limit,
                            @RequestParam(required = false) String condition){
        R responseResult = frBookService.selectPageBook(page,limit,condition);

        return responseResult;
    }

    @ApiOperation("推荐书籍添加")
    @PostMapping("/addBook")
    public R addBook(@RequestBody FrBook frBook){

        R responseResult = frBookService.addBook(frBook);

        return responseResult;
    }

    @ApiOperation("推荐书籍修改")
    @PostMapping("/editBook")
    public R editBook(@RequestBody FrBook frBook){

        R responseResult = frBookService.editBook(frBook);

        return responseResult;
    }

    @ApiOperation("推荐书籍删除")
    @DeleteMapping("/deleteBook/{id}")
    @ApiImplicitParam(name="id",value = "当前id",required=true,paramType="path")
    public R deleteBook(@PathVariable("id")String id){

        R responseResult = frBookService.deleteBook(id);

        return responseResult;
    }

}

