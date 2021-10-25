import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(): Unit = runBlocking {

    repeat(100_000) { // launch a lot of coroutines

        launch {
            delay(5000L)
            print("." + Thread.currentThread())
        }

    }
}