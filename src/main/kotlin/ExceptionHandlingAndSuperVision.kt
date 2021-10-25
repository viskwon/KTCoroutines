import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

// uncaught 된 Exception 을 Handle 할 수 있다

// generic catch 아래와 같이 쓰면 generic catch 으로 사용될 수 있다
// uncatch된 exception을 handle 할 수 있음
fun main() = runBlocking {



     //You cannot recover from the exception in the CoroutineExceptionHandler. The coroutine had already completed with the corresponding exception when the handler is called. Normally, the handler is used to log the exception, show some kind of error message, terminate, and/or restart the application.

    val job = GlobalScope.launch(handler) { // root coroutine with launch
        println("Throwing exception from launch")
        try {
            throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
        } catch (a: Exception) {
            println("CoroutineExceptionHandler hjskwon yap got $a ")

            
            CoroutineScope(coroutineContext).launch {
                throw IndexOutOfBoundsException()

            }
        }
    }
    job.join()
    println("Joined failed job")
    val deferred = GlobalScope.async(handler) { // root coroutine with async
        println("Throwing exception from async")
        throw ArithmeticException() // Nothing is printed, relying on user to call await
    }
    //try {
        deferred.await()
        println("Unreached")
//    } catch (e: ArithmeticException) {
  //      println("Caught ArithmeticException")
   // }


}

val handler = CoroutineExceptionHandler { _, exception ->
    println("CoroutineExceptionHandler default exception handler $exception")
}


// async builder always catches all exceptions and represents them in the resulting Deferred object, so its CoroutineExceptionHandler has no effect eith