package me.boot

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class AOP {

    /**
     * 标记方法
     */
    @Pointcut("execution(* me.boot.dao..*.*(..))")
    fun methodLog() = Unit


    @Around("methodLog()")
    fun cut(point: ProceedingJoinPoint) = kotlin.runCatching {
        println("before")
        point.proceed()
    }.onFailure {
        println("onFailure")
    }.onSuccess {
        println("onSuccess")
    }.getOrNull().apply {
        println("after")
    }


}
