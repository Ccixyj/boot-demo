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
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource


/**
 * 事务：
 * 1. 添加 @EnableTransactionManagement
 * 2. 添加 @Transactional
 * 3. 默认 RuntimeException 级别
 */
@SpringBootApplication(scanBasePackages = ["me.boot"])
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = false)
class CoreBeanApp {

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var newsDao: NewsDao

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

val logger = LoggerFactory.getLogger("CoreBeanApp")

fun main(args: Array<String>) = runBlocking(Dispatchers.Default) {
    println("start...")
    launch {
        val application = runApplication<CoreBeanApp>(*args)
        logger.info(" app catalog --->${application.getBean<DataSource>().connection}")
        val app = application.getBean<CoreBeanApp>()
        logger.info(" app catalog --->${app.dataSource}")
        logger.info("all news -> ${app.newsDao.allNews().joinToString("   |   ")}")
//        logger.info("add product-> ${app.newsDao.add("hello world")}")
//        logger.info("add product-> ${app.newsDao.addBatch("A", "B", "C")}")
        delay(1600)

        application.close()
    }
    println("go on...")
}