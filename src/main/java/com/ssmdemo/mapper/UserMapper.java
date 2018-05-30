package com.ssmdemo.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuguangchuan on 2018/5/29.
 */
@Mapper
public interface UserMapper {
    List<Map<String,Object>> queryList();
}
