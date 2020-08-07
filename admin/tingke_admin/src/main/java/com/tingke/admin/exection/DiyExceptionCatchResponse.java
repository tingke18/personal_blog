package com.tingke.admin.exection;

import com.google.common.collect.ImmutableMap;
import com.tingke.admin.model.CommonCode;
import com.tingke.admin.model.ResponseResult;
import com.tingke.admin.model.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//自定义异常捕捉，并返回json值
@ControllerAdvice//控制器方法增强
public class DiyExceptionCatchResponse {

    //使用EXCEPTIONS存放异常类型和错误代码的映射，ImmutableMap的特点一旦创建不可改变，并线程安全
    private static ImmutableMap<Class<? extends Throwable>,ResultCode> EXCEPTIONS;
    //使用builder来构建一个异常类型和错误代码的异常
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();


    public static final Logger LOGGER = LoggerFactory.getLogger(DiyException.class);

    //捕捉自定义异常后会执行此方法，返回错误信息给前端
    @ResponseBody
    @ExceptionHandler(DiyException.class)
    public ResponseResult diyResponseResult(DiyException e){
        LOGGER.error("catch exception: {}",e.getMessage(),e);//记录日志
        ResultCode resultCode = e.getResultCode();
        return new ResponseResult(resultCode);
    }

    //捕获不可知异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        //记录日志
        LOGGER.error("catch exception:{}",exception.getMessage(),exception);
        //判断异常映射是否存在
        if (EXCEPTIONS == null)
            EXCEPTIONS = builder.build();
        //EXCEPTIONS是一个Map集合的映射，通过异常类获取相对应的返回值
        final ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        final ResponseResult responseResult;
        //判断是否有对应异常类的返回值
        if (resultCode != null){//有则直接返回自定义的不可知异常
            responseResult = new ResponseResult(resultCode);
        } else {//无则返回统一异常
            responseResult = new ResponseResult(CommonCode.SERVER_ERROR);
        }
        return responseResult;
    }

    static {
        //在这里加入一些基础的异常类型判断
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
        builder.put(HttpRequestMethodNotSupportedException.class,CommonCode.METHOD_NOT_SUPPORT);
    }


}
