import kotlinx.coroutines.*

// 모든 suspending 함수는 취소가 가능합니다.
fun main() = runBlocking{
    val job = launch(Dispatchers.Default) {

        repeat(1000) { i ->

            println("job: I'm sleeping $i ...")
            delay(1500L)
            println("job: I'm sleeping end $i ...")
        }

    }
    delay(1300L) // delay a bit
    println("main: I'm tired of waiting!")
    job.cancelAndJoin() // cancels the job
    //job.join() // waits for job's completion
    println("main: Now I can quit.")

}