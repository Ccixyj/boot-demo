package me.boot.test.controller

import me.boot.test.BootDemoApplication
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

/**
 *  SpringBootTest 和 WebMvcTest 冲突
 *  使用@AutoConfigureMockMvc
 */
@SpringBootTest(classes = [BootDemoApplication::class],webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
//@WebMvcTest
class IndexControllerTest {

    private val logger = LoggerFactory.getLogger(IndexControllerTest::class.java)


    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Test
    fun index1() {
        mockMvc.perform(MockMvcRequestBuilders.get("/index")).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("index"))

    }

    @Test
    fun index2() {
        Assert.assertEquals(restTemplate.getForObject<String>("/index") , "index")

    }

    @Test
    fun book() {
        mockMvc.perform(MockMvcRequestBuilders.get("/book?id=100" )).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("book100"))
        Assert.assertEquals(restTemplate.getForObject<String>("/book?id=100") , "book100")
    }

    @Test
    fun bookPath() {
        mockMvc.perform(MockMvcRequestBuilders.get("/book/{id}",100 )).andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("book100"))
        Assert.assertEquals(restTemplate.getForObject<String>("/book/100") , "book100")
    }


}