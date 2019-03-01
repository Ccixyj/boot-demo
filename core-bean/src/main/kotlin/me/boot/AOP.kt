package me.boot

import com.fasterxml.jackson.databind.ObjectMapper
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
        println("before  ： $point ${point.signature.name} ${point.args}")
        point.proceed()
    }.onFailure {
        println("onFailure")
        it.printStackTrace()
    }.onSuccess {
        println("onSuccess")
    }.getOrNull().apply {
        println("after")
    }


}
