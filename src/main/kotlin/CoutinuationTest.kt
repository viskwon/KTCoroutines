import kotlinx.coroutines.*
import kotlin.coroutines.coroutineContext

fun main(): Unit = runBlocking {
    System.out.println("main  start")

    launch {  newCoroutineScope()}.join()





    System.out.println("main  end")
}

suspend fun newCoroutineScope(){
    System.out.println("newCoroutineScope  start")
    CoroutineScope(coroutineContext).launch {
        System.out.println("newCoroutineScope in launch")
        delay(3000)
        System.out.println("newCoroutineScope in end")
        onBG("dd"){ s1 , s2 ->

            launch { delay(3000) }.join()

        }
    }.join()
    System.out.println("newCoroutineScope  end")


}

suspend fun onBG( ss : String , fun2: suspend (String , String) -> Unit) {
    System.out.println("onBG  start")
    delay(3000)
    fun2.invoke("1","2")

    System.out.println("onBG  end")


}