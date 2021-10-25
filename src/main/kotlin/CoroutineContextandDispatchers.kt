import kotlinx.coroutines.*

private val mainScope = MainScope()
fun main(): Unit = runBlocking{




    launch { // context of the parent, main runBlocking coroutine
        println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
        // 처음 시작은 들어온 Thread 고 다시 suspend 가 resume 될때는  function invoke 된 Thread 에서 수행됨
        println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
        println("Default               : I'm working in thread ${Thread.currentThread().name}")
    }
    launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
        println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
    }
    //CoroutineScope LifeCycle 과 Coroutine 의 주기를 묶어서 제대로 coroutine 들이 해제되도록 하는
   // viewmodel scope 는 viewmodel 에 coroutineScope 가 정의 되어 있고 viewmodel 이 사라질 때 coroutine 도 같이 cancel 이 된다.
    // scope 를 잘쓰면 무의미하게 coroutines 가 살아있거나 계속 동작하는 걸 막을 수 있다.
    //  KTX extension 에 android 의 cycle 에 해당 하는 coroutinescope가 있어 가져다 쓰면됨


    // Thread -Local Data 

}

fun destroy(){
    mainScope.cancel()
}