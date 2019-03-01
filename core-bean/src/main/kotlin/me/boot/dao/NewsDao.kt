package me.boot.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.CallableStatementCallback
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
class NewsDao {

    @Autowired
    lateinit var jdbcTemp: JdbcTemplate


    fun allNews(): List<String> = jdbcTemp.query("select * from news") { rs, _ ->
        rs.getString("title")
    }

    suspend fun add(name: String) = jdbcTemp.execute("insert product (name) value (?)", CallableStatementCallback {
        it.setString(1, name)
        it.execute()
        it.updateCount
    })


    @Transactional(rollbackFor = [Exception::class])
    suspend fun addBatch(vararg names: String) = names.count {
        add(it) == 1
    }
}