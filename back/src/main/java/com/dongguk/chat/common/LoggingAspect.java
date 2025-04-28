package com.dongguk.chat.common;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
//
//@Aspect
//@Component
//public class LoggingAspect {
//    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
//
//    @Pointcut("execution(* com.dongguk.chat.*")
//    public void allServiceMethods(){}
//
//    @Before("allServiceMethods()")
//    public void logBeforeMethod(JoinPoint joinPoint){
//        logger.info("메서드 실행 전: {}", joinPoint.getSignature());
//    }
//
//    @AfterReturning(pointcut = "allServiceMethods()", returning = "result")
//    public void logAfterReturning(JoinPoint joinPoint, Object result){
//        logger.info("메서드 실행 후: {} / 반환값: {}", joinPoint.getSignature(), result);
//    }
//
//    @AfterThrowing(pointcut = "allServiceMethods()", throwing = "e")
//    public void logAfterThrowing(JoinPoint joinPoint, Throwable e){
//        logger.error("예외 발생: {} / 예외 {}", joinPoint.getSignature(), e.getMessage());
//    }
//}
