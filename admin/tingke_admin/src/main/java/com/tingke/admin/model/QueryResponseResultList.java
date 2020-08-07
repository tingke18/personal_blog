package com.tingke.admin.model;

import lombok.Data;
import lombok.ToString;

import java.util.List;

//返回list数据
@ToString
@Data
public class QueryResponseResultList<T> {
    //返回总条数
    int total;
    //返回数据金额和
    List<T> list;

}
