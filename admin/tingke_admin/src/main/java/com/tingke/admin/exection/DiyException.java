package com.tingke.admin.exection;

import com.tingke.admin.model.ResultCode;
import lombok.Data;
import lombok.ToString;

//自定义异常
@Data
@ToString
public class DiyException extends RuntimeException {

    private ResultCode resultCode;

    public DiyException(ResultCode resultCode){
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode(){
        return this.resultCode;
    }

}
