package me.boot.dao

import me.boot.CoreBeanApp
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.junit4.SpringRunner


@SpringBootTest(classes = [CoreBeanApp::class])
@RunWith(SpringRunner::class)
class NewsDaoTest {

    val logger = LoggerFactory.getLogger(javaClass)

    @Autowired
    lateinit var app: ApplicationContext


    @Autowired
    lateinit var newsDao: NewsDao

    @Test
    fun allNews() {
        logger.info("app $app")
        logger.info("app ${app.getBeansOfType(NewsDao::class.java)}")
        assertTrue(newsDao.allNews().isNotEmpty())
    }
}