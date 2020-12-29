package io.github.starlight220.pingpong

import kotlinx.browser.document
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLElement

interface GameElement {
    fun periodic()
}

fun GameElement.register() = Scheduler.register(this)

object Scheduler {
    private lateinit var elements: Set<GameElement>
    var delay: Long = 0
    fun runLoop() {
        elements.forEach {
            it.periodic()
        }

        (document.getElementById("ball") as? HTMLElement)!!.style.run {
            console.log("left: $left \t top: $top")
        }
    }

    fun register(element: GameElement) {
        if(!::elements.isInitialized) elements = HashSet()
        elements += element
    }

    fun launch(delay: Long = this.delay) {
        this.delay = delay
        GlobalScope.launch {
            while (true) {
                runLoop()
                delay(delay)
            }
        }
    }

}
