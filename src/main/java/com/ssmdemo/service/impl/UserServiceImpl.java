package com.ssmdemo.service.impl;

import com.ssmdemo.common.Page;
import com.ssmdemo.mapper.UserMapper;
import com.ssmdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhuguangchuan on 2018/5/30.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper ;

    @Override
    public List<Map<String, Object>> queryList(Page page,Map<String,Object> params) {
        return userMapper.queryList(page,"test");
    }
}
