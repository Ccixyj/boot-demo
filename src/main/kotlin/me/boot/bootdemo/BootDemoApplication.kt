package me.boot.bootdemo

import javafx.application.Application.launch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.beans.factory.getBean
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.context.support.GenericApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.stereotype.Component
import java.util.concurrent.atomic.AtomicInteger
import javax.annotation.Resource

@SpringBootApplication
@EnableAsync
class BootDemoApplication

private val logger = LoggerFactory.getLogger("main")


fun main(args: Array<String>) = runBlocking {
    //--spring.config.location=classpath:/application.properties,file:D:/Develop/Java/Projects/kotlin/boot-demo/tomcat.properties
    val id = AtomicInteger(1)
    val application = runApplication<BootDemoApplication>(*args)
    launch(Dispatchers.Default) {
        //        logger.info("local name :${application.environment.getProperty("local.name")}")
        logger.info(application.getBean<AppConfig>().toString())
        application.getBean<AppConfig>().show()
        logger.info(application.getBean<DataSourceConfig>().toString())
        logger.info(application.getBean<DS>().toString())
        application.getBean<Runnable>("jeep").run()
        delay(600)
        application.publishEvent(MyEvent(id.getAndIncrement()))
    }

    GenericApplicationContext {
        logger.info("-----------GenericApplicationContext-----------")
        logger.info(application.getBean<EventListener>().toString())
        logger.info("-----------GenericApplicationContext-----------")
    }


    GenericApplicationContext {
        logger.info(application.getBean<EventListener>().toString())
        refresh()
        application.publishEvent(MyEvent(id.getAndIncrement()))
    }

    delay(8000)

    application.close()


}


/**
 * 自定义配置
 *
 */

@Component
class AppConfig {

    @Value("\${local.name}")
    private lateinit var name: String


    @Value("\${local.port:8080}")
    private var port: Int = 0

    @Value("\${url}")
    private lateinit var url: String

    @Value("\${driverClassName}")
    private lateinit var driverClassName: String

    @Resource
    private lateinit var app: ApplicationContext

    @Bean
    fun createJeep() = Jeep()

    @Bean
    @ConditionalOnClass(name = ["me.boot.bootdemo.FileConfig"])
    fun createFileC() = FileConfig()


    /**
     * --spring.profiles.active=dev,test
     */
    @Bean
    @Profile("test")
    @ConditionalOnClass(name = ["org.slf4j.LoggerFactory123"])
    fun createFileCTest() = FileConfig()

    @Bean
    @Profile("dev")
    @ConditionalOnBean(name = ["createFileCTest"])
    fun createFileCDev() = FileConfig()


    fun show() {
        logger.info("env :${app.environment}")
        logger.info("env local port:${app.environment.getProperty("local.port", "1111")}")
        logger.info("createFileC = ${kotlin.runCatching { app.getBean("createFileC") }.getOrNull()}")
        logger.info("createFileCTest = ${kotlin.runCatching { app.getBean("createFileCTest") }.getOrNull()}")
        logger.info("createFileCDev = ${kotlin.runCatching { app.getBean("createFileCDev") }.getOrNull()}")
    }

    override fun toString(): String {
        return "AppConfig(name='$name', port=$port, url='$url', driverClassName='$driverClassName', app=$app)"
    }


}

@Component
@ConfigurationProperties(prefix = "ds")
class DS {


    var root: String = ""

    var password: String = ""

    override fun toString(): String {
        return "DS(root='$root', pass='$password')"
    }

}

data class MyEvent(val id: Int) : ApplicationEvent(id)

/**
 *  1.application.addListener()
 *  2.配置properties: context.listener.classes=me.boot.bootdemo.EventListener
 *  @see org.springframework.boot.context.config.DelegatingApplicationListener
 *  3.配置handle
 *  4.spi机制
 *  @see org.springframework.core.io.support.SpringFactoriesLoader
 */
@Component
class EventListener : ApplicationListener<ApplicationEvent> {

    private val logger = LoggerFactory.getLogger("EventListener")

    override fun onApplicationEvent(event: ApplicationEvent) {
        logger.info(">>>receive event <<< $event")
    }
}

@Component
@Async
class EventHandle {

    private val logger = LoggerFactory.getLogger("EventHandle")

    @org.springframework.context.event.EventListener
    fun onHandle(event: ApplicationEvent) {
        logger.info(">>>receive handle <<< $event")
    }
}


/**
 *
 * @see org.springframework.boot.CommandLineRunner
 * 最后启动的回调
 */
@Component
@Order(1100)
class ServerRunner : CommandLineRunner {
    override fun run(vararg args: String?) {
        logger.info("ServerRunner : app start ${args.joinToString()}")
    }
}

/**
 *
 * @see org.springframework.boot.ApplicationRunner
 * 最后启动的回调
 * 与CommandLineRunner相同,参数不同
 */
@Component
@Order(1100)
class ServerAppRunner : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        logger.info("ServerAppRunner : app start ${args?.optionNames}")
    }
}