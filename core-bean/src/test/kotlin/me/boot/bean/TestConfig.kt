package me.boot.bean

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.util.function.Function

@TestConfiguration
class TestConfig {

    @Bean
    fun createFunc() = {}

    @Bean
    fun createRun() = Runnable {  }


    @Bean
    fun createFuncJDK() = Function<String, String> {
        it
    }
}