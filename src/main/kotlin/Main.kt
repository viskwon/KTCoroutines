import kotlinx.coroutines.*
import java.util.logging.Logger

suspend fun main() {
    // launch >> 코루틴 빌더 현재 Thread 를 Block 하지 않고 새로운 코루틴을 시작
    // GlobalScope application lifetime 을 가지는 코루틴

    // Job launch 함수의 return 타입  취소가 가능한 백그라운드 작업

 // {} block 을 전달해줘서 필요할 때 해당  해당 block 을 실행 시켜줌
// coroutineScope   는 Thread 를 막지 않고  runblocking 은 현재 thread 를 막는다.
    val job = GlobalScope.launch { // launch a new coroutine in background and continue Job을 리턴해 생성한 코루틴을 참조할 수 있게 함
        //suspend 함수는 오직 코루틴 내에서만 부를수 있다.
        // 코루틴은 해당 Block 을 전달 받아 가지고 있으면서 Thread 에 맞게 컨트롤 해주나보다

        delay(100L) // non-blocking delay for 1 second (default time unit is ms)


        println("World!" +Thread.currentThread()) // print after delay

        hundredPlus()

        println("afterhundredPlus"+Thread.currentThread())

    }

    println("Hello, $job") // main thread continues while coroutine is delayed
 // 원하는 값을 return 할수 있게 만듬
    //  Block 현재 thread 를 막고 끝날 때까지 기다림
    val s2 = runBlocking {
        delay(10000)
        1
    }


    test {
        System.out.println("pass this block ")
        1
    }
    System.out.println("adter Runblocking $s2")


    System.out.println("afterJoin")
}
fun <T> test(st: String = "vs", block: () -> T): T {
    System.out.println("test1 ")
    val s = block.invoke()
    System.out.println("test2  $s")
    return s
}

suspend fun hundredPlus(): Int {
    // couroutine context 를 정해주고
    return withContext(Dispatchers.Unconfined) {
        var a = 0;
        delay(100L)

        a++

        delay(100L)

        a++
        delay(100L)
        a++
        delay(100L)
        a++

        System.out.println("hundredplus"+Thread.currentThread())
        a
    }
}