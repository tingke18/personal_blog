package com.tingke.admin.exection;

import com.tingke.admin.model.ResultCode;

//封装自定义异常方法
public class CastDiyException {


    public static void cast(ResultCode resultCode){
        throw new DiyException(resultCode);
    }


}
