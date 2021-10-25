import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

fun simple(): List<Int> = listOf(1, 2, 3)

fun simpleSequence(): Sequence<Int> = sequence { // sequence builder
    for (i in 1..3) {
        Thread.sleep(100) // pretend we are computing it
        yield(i) // yield next value
    }
}

// Flows are cold streams  collect 가 불려지기 전에는 block 실행안함
fun flowsimple(): Flow<Int> = flow { // flow builder flow 를 만드는 builder.

    for (i in 1..3) {
        delay(100) // pretend we are doing something useful here
        emit(i) // emit next value
    }
}
suspend fun suspendsimple(): List<Int> {
    delay(1000) // pretend we are doing something asynchronous here
    return listOf(1, 2, 3)
}

fun main(): Unit = runBlocking {
    val job = GlobalScope.launch {
        flowsimple().filter { true }.map {
            mapper(it)
        }.collect {
            System.out.println(it)

        }
    }

    job.join()

    System.out.println("ENd")

    simple().asFlow().flatMapConcat {
        listOf("d", "d").asFlow()
    }

}

suspend fun mapper(i : Int) : String{
    delay(1000)
    return System.currentTimeMillis().toString()

}
/*
fun main() :Unit = runBlocking {
 // lamda 로 block 을 계속 넘기는 구조

    flowsimple().map {
        value ->
        value + 3

    }.collect {
        value ->   println(value)
    }
    // block 을 보내주면 적절한 타이밍에 해당 block 을 실행 시키는 구조라고 보면 되거 같다
    flowsimple().take(1).transform{value ->
        emit("Strings")
    }.collect { value ->  println(value)}


    val a = flowsimple().map {
            value ->
        4

    }.reduce { accumulator, value ->
        System.out.println("accumulator $accumulator")
        accumulator +value
    }

    simple().asFlow().flatMapConcat {
        listOf("d", "d").asFlow()
    }.collect {
        
    }

    System.out.println("aaa $a")



}*/
