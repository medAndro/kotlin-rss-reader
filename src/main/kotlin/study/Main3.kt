package study

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {
    runBlocking {
        val time =
            measureTimeMillis {
                val name = async { getName() }
                val lastName = async { getLastName() }
                println("Hello, ${name.await()} ")
                println("Hello, ${lastName.await()}")
            }
        println("Execution took $time ms")
    }
}

suspend fun getName(): String {
    delay(1000)
    return "Jason"
}

suspend fun getLastName(): String {
    delay(1000)
    return "Park"
}
