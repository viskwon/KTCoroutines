import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.Continuation
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine

val channel = Channel<Int>(5)
fun main() = runBlocking{


    channel.send(2)
    launch {
        for (x in 1..5) channel.send(x * x)
        channel.send(2)
        channel.close() // we're done sending
    }
// here we print received values using `for` loop (until the channel is closed)
    for (y in channel) println(y)
    println("Done!")

    val squares = produceSquares()
     //Comsumer
    squares.consumeEach { println(it) }


}
fun aa()  {


}






// 확장함수 producer
fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) send(x * x)
}