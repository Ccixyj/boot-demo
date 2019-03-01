import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import javax.sql.DataSource


@SpringBootApplication(scanBasePackages = ["me.boot"])
class CoreBeanApp {

    @Autowired
    lateinit var dataSource: DataSource
}

val logger = LoggerFactory.getLogger("CoreBeanApp")

fun main(args: Array<String>) = runBlocking(Dispatchers.Default) {
    println("start...")
    launch {
        val application = runApplication<CoreBeanApp>(*args)
        logger.info(" app catalog --->${application.getBean<DataSource>().connection}")
        logger.info(" app catalog --->${application.getBean<CoreBeanApp>().dataSource}")
        delay(1600)
        application.close()
    }
    println("go on...")
}