package com.ssmdemo.controller;

import com.ssmdemo.Exception.ServerException;
import com.ssmdemo.common.CODE;
import com.ssmdemo.common.Page;
import com.ssmdemo.common.ResponseData;
import com.ssmdemo.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User controller
 * Created by zhuguangchuan on 2018/5/28.
 */
@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    @RequestMapping("/{id}")
    public Object getUserInfoById(@PathVariable String id) throws ServerException {
        //throw exception
        if(StringUtils.isEmpty(id)){
            throw ServerException.build(CODE.Fail,"exception");
        }
        return new ResponseData(CODE.Success);
    }

    @RequestMapping("/list")
    public Object list(Page page){
        List<Map<String, Object>> mapList = userService.queryList(page,new HashMap<String,Object>(){});
        return new ResponseData(CODE.Success,mapList);
    }
}
