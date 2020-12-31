package io.github.starlight220.pingpong.utils

import io.github.starlight220.pingpong.BACKGROUND_COLOR
import io.github.starlight220.pingpong.GlobalObjectContainer
import kotlinx.atomicfu.AtomicBoolean
import kotlinx.browser.document
import kotlinx.coroutines.*
import org.w3c.dom.HTMLElement
import kotlin.properties.Delegates

interface GameElement {
    fun periodic()
}

fun GameElement.register() = Scheduler.register(this)

object Scheduler {
    private lateinit var elements: Set<GameElement>
    private var delay: Long = 0
    var paused by Delegates.observable(false) { _, _, new ->
        document.bgColor = if(new) "#a83297" else BACKGROUND_COLOR
    }

    private fun runLoop() {
        if(paused) return
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
