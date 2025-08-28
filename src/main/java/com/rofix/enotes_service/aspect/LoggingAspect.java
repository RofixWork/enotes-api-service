package com.rofix.enotes_service.aspect;

import com.rofix.enotes_service.utils.LoggerUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Before;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Before("execution(* com.rofix.enotes_service.controller.*.*(..))")
    public void beforeController(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();

        LoggerUtils.createLog(Level.INFO, className, methodName, "Start Execution...");
    }

    @After("execution(* com.rofix.enotes_service.controller.*.*(..))")
    public void afterController(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();

        LoggerUtils.createLog(Level.INFO, className, methodName, "End Execution...");
    }
}
