package com.user.services.logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

/**
 * Aspect logs
 *
 *  @author : Lilian
 *  @version : 1.0-SNAPSHOT
 */
@Aspect
@Component
public class LoggingHandler {

    @Value("${log.debug.active}")
    private boolean isLogActive;

    Logger log = LogManager.getLogger(this.getClass());

    @Pointcut("within(com.user.services.controller..*)")
    public void controller() {
    }

    @Pointcut("within(com.user.metier..*)")
    public void metier() {
    }

    @Pointcut("within(com.user.data.dao..*)")
    public void data() {
    }

    /**
     * Log in and out of method with exec time
     *
     * @param joinPoint joinjoint
     * @return ret
     * @throws Throwable
     */
    @Around("controller() || metier() || data()")
    public Object log(ProceedingJoinPoint joinPoint) throws Throwable {
        // Log start
        logStart(joinPoint.toShortString());

        // Calcul duration
        ZonedDateTime start = ZonedDateTime.now();
        Object ret = joinPoint.proceed();
        ZonedDateTime end = ZonedDateTime.now();

        // log end
        logEnd(joinPoint.toShortString(),ChronoUnit.MILLIS.between(start,end) );
        return ret;
    }

    /**
     * Log throwing exceptions with stacktrace
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "controller() || metier() || data()", throwing = "e")
    public void logAfterThrow(JoinPoint joinPoint, Exception e){
        if(isLogActive){
            log.error("- Error " + joinPoint.toShortString());
            log.error(e);
            StringBuilder sb = new StringBuilder();
            Arrays.stream(e.getStackTrace()).forEach(stackTraceElement -> sb.append(stackTraceElement.toString()).append("\n"));
            log.error(sb.toString());
        }
    }

    /**
     * Log method start
     *
     * @param method
     */
    private void logStart(String method){
        if(isLogActive){
            log.info("- Start "+ method + " -");
        }
    }

    /**
     * Log method end
     *
     * @param method
     * @param time
     */
    private void logEnd(String method, long time){
        if(isLogActive){
            log.info("- End " + method + " in " + time + "ms -");
        }
    }
}