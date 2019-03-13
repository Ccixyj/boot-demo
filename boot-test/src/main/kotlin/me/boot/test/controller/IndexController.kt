package me.boot.test.controller

import io.micrometer.core.annotation.Timed
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class IndexController {

    private val logger = LoggerFactory.getLogger(this.javaClass)


    @Autowired
    lateinit var restTemplate: RestTemplate

    @GetMapping("/index")
    @Timed
    fun index(): String {
        logger.info("invoke index func")
        return "index"
    }

    @GetMapping("/book")
    @Timed
    fun book(@RequestParam("id") id: String): String {
        logger.info("invoke index book id : $id")
        return "book$id"
    }

    @GetMapping("/book/{id}")
    @Timed
    fun bookPath(@PathVariable("id") id: String): String {
        logger.info("invoke index book id : $id")
        return "book$id"
    }

    @GetMapping("/a")
    @Timed
    fun aPage(): String {
        return "this is a page!"
    }

    @GetMapping("/b")
    @Timed
    fun bPage(): String {
        return "this is b page!"
    }


}