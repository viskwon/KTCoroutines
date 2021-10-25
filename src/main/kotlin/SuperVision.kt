import kotlinx.coroutines.*

fun main() = runBlocking {
    yield()
    supervisorScope {

    }
    val supervisor = SupervisorJob()

    with(CoroutineScope(coroutineContext + supervisor)) {
        // launch the first child -- its exception is ignored for this example (don't do this in practice!)
        val firstChild = launch(CoroutineExceptionHandler { _, _ ->  }) {
            try {
                delay(Long.MAX_VALUE)
            }finally {
                println("The first child is cancelled")
            }

            //throw AssertionError("The first child is cancelled")
        }
        // launch the second child
        val secondChild = launch {
            //firstChild.join()
            // Cancellation of the first child is not propagated to the second child
            println("The first child is cancelled: ${firstChild.isCancelled}, but the second one is still active")
            try {
                delay(Long.MAX_VALUE)
            } finally {
                // But cancellation of the supervisor is propagated
                println("The second child is cancelled because the supervisor was cancelled")
            }
        }
        // wait until the first child fails & completes

        println("Cancelling the supervisor")
        delay(100)
        supervisor.cancel()
        secondChild.join()
        delay(100000)

    }

}