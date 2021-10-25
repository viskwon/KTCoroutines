import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ProducerScope
import java.text.DateFormat
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Future
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger
import kotlin.coroutines.*

import kotlinx.coroutines.*


fun main() = runBlocking {
    val job = GlobalScope.launch() { // root coroutine with launch
        println("Throwing exception from launch")
        try {
        throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
        }catch (e : Exception){
            println("exception")



        }
    }

        job.join()


    println("Joined failed job")
    val deferred = GlobalScope.async { // root coroutine with async
        println("Throwing exception from async")
        throw ArithmeticException() // Nothing is printed, relying on user to call await
    }

    try {
        deferred.await()
        println("Unreached")
    } catch (e: ArithmeticException) {
        println("Caught ArithmeticException")
    }
}


/*

fun main(): Unit = runBlocking {

    val job = async {
        val aa = async { asyncf(1,5000)
        1}
        val dd = launch { launchf()
        5}

        System.out.println(dd.join())


        asyncf(2)
        asyncf(3)
        asyncf(4)
    }
    job.join()


}

*/

suspend fun  asyncf(i : Int , wait : Long  = 1000){

    delay(wait)
    val d = Date()
    System.out.println("async $i $d")
}

suspend fun  launchf(){

    delay(1000)
    val d = Date()
    System.out.println("launch $d")
}



suspend fun a(){


    withContext(Dispatchers.Unconfined) {
        System.out.println("asss ${Thread.currentThread()}")
        delay(10000)
    }

    GlobalScope.launch {  }



}


suspend fun <T> CompletableFuture<T>.await(): T {
    return suspendCoroutine<T> { continuation: Continuation<T> ->
        whenComplete { result, exception ->
            if (exception == null)
                continuation.resume(result)
            else
                continuation.resumeWithException(exception)

        }
    }
}




fun launch2(context: CoroutineContext = EmptyCoroutineContext, block: suspend () -> Unit) {


}