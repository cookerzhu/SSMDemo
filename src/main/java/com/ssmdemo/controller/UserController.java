package com.ssmdemo.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/{id}")
    public Object getUserInfoById(String id){
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select * from tbl_user");
        return mapList ;
    }
}
