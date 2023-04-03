package com.qc.controller;

import com.qc.mapper.TeacherMapper;
import com.qc.mapper.UserMapper;
import com.qc.service.TeacherService;
import com.qc.service.UserService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@Slf4j
public class UserController {
    
    @Resource
    private UserService userService;

    @Resource
    private UserMapper mapper;


}
