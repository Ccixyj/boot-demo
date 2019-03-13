package me.boot.dao

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.slf4j.LoggerFactory
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
class UserMapperTest {
    private val logger = LoggerFactory.getLogger(UserMapperTest::class.java)

    @MockBean
    lateinit var mapper: UserMapper

    @Before
    fun init() {
        BDDMockito.given(mapper.createUser("admin")).willReturn(1)
        BDDMockito.given(mapper.createUser("")).willReturn(0)
        BDDMockito.given(mapper.createUser(null)).willThrow(NullPointerException::class.java)
    }

    @Test(expected = NullPointerException::class)
    fun createUser() {
        logger.info("start ----->")
        logger.info("create :${mapper.createUser("admin")}")
        logger.info("create :${mapper.createUser("abc")}")
        logger.info("create :${mapper.createUser("")}")
        mapper.createUser(null)
    }
}