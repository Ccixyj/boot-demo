package me.boot.test.controller

import io.micrometer.core.annotation.Timed
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class IndexController {

    private val logger = LoggerFactory.getLogger(this.javaClass)


    @GetMapping("/")
    @Timed
    fun index(): String {
        return "this is me.boot.main page!"
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