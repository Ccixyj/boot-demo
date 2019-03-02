package me.boot

import com.alibaba.druid.pool.DruidDataSource
import org.springframework.boot.SpringBootConfiguration
import org.springframework.stereotype.Component
import javax.sql.DataSource


@SpringBootConfiguration
class DBConfig {

    //@Bean(name = ["druid"])
    fun druidDS(): DataSource = DruidDataSource().apply {

        url = "jdbc:mysql://localhost:3306/ktor?serverTimezone=GMT%2B8"
        username = "root"
        password = "root"


        initialSize = 1
        minIdle = 3
        maxActive = 20
        // 配置获取连接等待超时的时间
        maxWait = 60000
        isKeepAlive = true
        // 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
        timeBetweenEvictionRunsMillis = 60000
        // 配置一个连接在池中最小生存的时间，单位是毫秒
        minEvictableIdleTimeMillis = 30000
        validationQuery = "select 'x'"
        isTestWhileIdle = true
        isTestOnBorrow = false
        isTestOnReturn = false
        // 打开PSCache，并且指定每个连接上PSCache的大小
        isPoolPreparedStatements = true
        maxPoolPreparedStatementPerConnectionSize = 20
        // 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
        //
        addFilters("stat,wall,slf4j")
        // 通过connectProperties属性来打开mergeSql功能；慢SQL记录
        setConnectionProperties("druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000")
        // 合并多个DruidDataSource的监控数据
        //useGlobalDataSourceStat= true
    }
}