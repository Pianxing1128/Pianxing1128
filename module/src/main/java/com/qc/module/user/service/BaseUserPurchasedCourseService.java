package com.qc.module.user.service;

import com.qc.module.userPurchasedCourseRelation.service.UserPurchasedCourseRelationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
@Slf4j
public class BaseUserPurchasedCourseService {

    private UserPurchasedCourseRelationService userPurchasedCourseRelationService;
    @Autowired
    public BaseUserPurchasedCourseService(UserPurchasedCourseRelationService userPurchasedCourseRelationService){
        this.userPurchasedCourseRelationService = userPurchasedCourseRelationService;
    }


    public void createUserPurchasedCourseRelation(BigInteger userId,BigInteger courseId) {

        userPurchasedCourseRelationService.edit(userId, courseId);

    }
}
