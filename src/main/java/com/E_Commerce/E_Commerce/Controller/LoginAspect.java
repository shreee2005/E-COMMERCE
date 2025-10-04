package com.E_Commerce.E_Commerce.Controller;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoginAspect.class);
    @Before("execution(*com.substring.irctc.Service.ServiceImpl.*.*(..))")
    public void logBeforMethod(Logger logger){
        logger.info("Getting all the products ");
    }

//    @Around("execution(*com.substring.irctc.Service.ServiceImpl.*.*(..))")
//    public Object logBeforMethod(ProceedingJoinPoint joinPoint) throws Throwable {
//        logger.info("Befor Getting all the products ");
//        Object result = joinPoint.proceed();
//        logger.info("After ");
//        return result;
//    }
}
