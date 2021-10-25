import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() = runBlocking {


    val time = measureTimeMillis { // 기본적으로 코루틴내 코드는 순차적으로 수행된다.
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            println("The answer is ${one + two}")
    }
    println("Completed in $time ms")

// deferred 와 job 의 차이 ... 결과를 안가져온다??
// public interface Deferred<out T> : Job {
    // Deferred 는 JOB 을 implemented 한  interface 값을 나중에 주는거라고 생각하면됨
    val time2 = measureTimeMillis { // 기본적으로 코루틴내 코드는 순차적으로 수행된다.
        val one = async {doSomethingUsefulOne()} // async 는 launch 와 유사하지만 defferd 를 return 하는 차이점이 있음
        // 동시에 여러개를 수행할 때는 async 를 사용해야함 concurrent programming.
        val two = doSomethingUsefulTwo()
        println("The answer is ${one.await() + two}")
    }
    println("Completed in $time2 ms")

    //동시에 수행하도록 하려면 async??



    val time3 = measureTimeMillis { // 추천하지 않는 구조
        // we can initiate async actions outside of a coroutine
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // but waiting for a result must involve either suspending or blocking.
        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time3 ms")


}

suspend fun fetchFavorites(userid :String) : String{
    val user = getUserInfo(userid) // suspend
    val list = getMediaList(user) // suspend
    return list
}


suspend fun getMediaList(user: String) : String{
    delay(1000)
    return "bb"
}

suspend fun getUserInfo(userid: String) : String{

    delay(1000)
    return "dd"
}

// The result type of somethingUsefulOneAsync is Deferred<Int>
fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

// The result type of somethingUsefulTwoAsync is Deferred<Int>
fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

suspend fun doSomethingUsefulOne() : Int {
    delay(2000L) // pretend we are doing something useful here
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}

suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}