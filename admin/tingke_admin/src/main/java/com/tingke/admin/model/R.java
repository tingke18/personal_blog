package com.tingke.admin.model;

import lombok.Data;
import lombok.ToString;

//同一返回响应数据
@ToString
@Data
public class R extends ResponseResult{

    private QueryResponseResultList queryResponseResultList;
    private QueryResponseResultMap queryResponseResultMap;
    private Object object;

    public R(ResultCode resultCode, QueryResponseResultList queryResponseResultList){
        super(resultCode);
        this.queryResponseResultList = queryResponseResultList;
    }

    public R(ResultCode resultCode, QueryResponseResultMap queryResponseResultMap){
        super(resultCode);
        this.queryResponseResultMap = queryResponseResultMap;
    }

    public R(ResultCode resultCode, Object object){
        super(resultCode);
        this.object = object;
    }

}
