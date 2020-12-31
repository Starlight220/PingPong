package io.github.starlight220.pingpong.utils

import io.github.starlight220.pingpong.GlobalObjectContainer
import kotlinx.browser.document
import kotlinx.coroutines.*
import org.w3c.dom.HTMLElement

interface GameElement {
    fun periodic()
}

fun GameElement.register() = Scheduler.register(this)

object Scheduler {
    private lateinit var elements: Set<GameElement>
    private var delay: Long = 0
    private fun runLoop() {
        elements.forEach {
            it.periodic()
        }

//        (document.getElementById("ball") as? HTMLElement)!!.style.run {
//            console.log("left: $left \t top: $top")
//        }
    }

    fun register(element: GameElement) {
        if(!::elements.isInitialized) elements = HashSet()
        elements += element
    }

    private var job: Job? = null
    fun launch(delay: Long = Scheduler.delay) {
        Scheduler.delay = delay
        GlobalObjectContainer.init()
        job = GlobalScope.launch {
            while (true) {
                runLoop()
                delay(delay)
            }
        }
    }

    fun close() {
        job?.cancel()
        job = null
        elements = HashSet()
    }
}
