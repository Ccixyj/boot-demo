package me.boot

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import me.boot.dao.NewsDao
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import redis.clients.jedis.Jedis
import javax.sql.DataSource


/**
 * 事务：
 * 1. 添加 @EnableTransactionManagement
 * 2. 添加 @Transactional
 * 3. 默认 RuntimeException 级别
 *
 *
 */
@SpringBootApplication(scanBasePackages = ["me.boot"])
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true) //false默认使用jdk proxy ， cglib:没有接口仍需要使用cglib
//@EnableRedis
class CoreBeanApp {

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var newsDao: NewsDao

    val logger = LoggerFactory.getLogger("me.boot.main")
    @Bean
    fun testBean(platformTransactionManager: PlatformTransactionManager): Any {
        logger.info(">>>>>>>>>>" + platformTransactionManager.javaClass.name)
        return Any()
    }

//    // 创建事务管理器1
//    @Bean(name = ["txManager1"])
//    fun txManager(dataSource: DataSource): PlatformTransactionManager {
//        return DataSourceTransactionManager(dataSource)
//    }

}


fun main(args: Array<String>) = runBlocking(Dispatchers.Default) {
    println("start...")
    launch {
        val application = runApplication<CoreBeanApp>(*args)


        launch {
            application.testCoreBean()
        }

        launch {
            application.testJedis()
        }
        delay(1600)
        application.close()
    }
    println("go on...")
}

fun ApplicationContext.testJedis() {
    val logger = LoggerFactory.getLogger("Jedis")
    val jedis = getBean<Jedis>("jedis")
    logger.info("redis $jedis")
    jedis.set("hello", "world")
    logger.info("redis hello: ${jedis.get("hello")}")
}


suspend fun ApplicationContext.testCoreBean() {
    val logger = LoggerFactory.getLogger("me.boot.CoreBeanApp")
    logger.info(" app catalog --->${getBean<DataSource>().connection}")
    val app = getBean<CoreBeanApp>()
    logger.info(" app catalog --->${app.dataSource}")
    logger.info("all news -> ${app.newsDao.allNews().joinToString("   |   ")}")
    logger.info("add product-> ${app.newsDao.add("hello world")}")
}


