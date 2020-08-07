package com.tingke.admin.model;

import lombok.Data;
import lombok.ToString;

import java.util.Map;

//返回map数据
@ToString
@Data
public class QueryResponseResultMap<T,V> {

    //返回总条数
    int total;
    //返回数据金额和
    Map<T,V> map;
}
