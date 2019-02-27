package me.boot.bootdemo

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class Jeep : Runnable {
    val logger = LoggerFactory.getLogger("Jeep")

    @Async
    override fun run() {
        (0..3).forEach {
            logger.info("jeep sleep 1s ")
            TimeUnit.SECONDS.sleep(1)
        }
    }
}