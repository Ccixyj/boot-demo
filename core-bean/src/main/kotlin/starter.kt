import kotlinx.coroutines.runBlocking
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
 class App

fun main(args: Array<String>) = runBlocking {
    val application = runApplication<App>(*args)
}