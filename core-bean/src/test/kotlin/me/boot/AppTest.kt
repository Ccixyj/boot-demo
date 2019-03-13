package me.boot

import me.boot.bean.TestConfig
import me.boot.dao.User
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.beans.factory.getBeansOfType
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment
import org.springframework.test.context.junit4.SpringRunner

/**
 *
 *  properties = ["app.version=1.0.0"]
 *
 */
@SpringBootTest(classes = [CoreBeanApp::class, TestConfig::class],properties = ["app.version=1.0.0"])
@RunWith(SpringRunner::class)
class AppTest {

    private val logger = LoggerFactory.getLogger(AppTest::class.java)

    @Autowired
    lateinit var app: ApplicationContext

    @Autowired
    lateinit var env:Environment



    @Test
    fun testUser() {
        Assert.assertNotNull(app.getBean<User>())
        val bean = app.getBean("createFunc")
        Assert.assertNotNull(bean)
        logger.info("bean ${bean.javaClass.superclass}")

        logger.info("func ${   app.getBeansOfType(Function::class.java)}")
        logger.info("jdk func ${   app.getBeansOfType(java.util.function.Function::class.java)}")
        logger.info("jdk func ${   app.getBeansOfType(Runnable::class.java)}")


    }


    @Test
    fun testEnv() {

//        Assert.assertEquals("127.0.0.1" , env.getProperty("redis.host"))
        Assert.assertEquals("core-test" , env.getProperty("app.name"))
        Assert.assertEquals("1.0.0" , env.getProperty("app.version"))
        Assert.assertEquals("1" , env.getProperty("app.init.int"))
    }

}