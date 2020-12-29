package io.github.starlight220.pingpong

import kotlinx.coroutines.GlobalScope
import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent



const val move: Int = 10



object KeyboardTracker {
    private const val UP = "ArrowUp"
    const val DOWN = "ArrowDown"
    const val W = "w"
    const val S = "s"
    private val map = HashMap<String, Boolean>()

    fun press(event: Event) {
        if(event !is KeyboardEvent) return
        map[event.key] = true
    }
    fun release(event: Event) {
        if(event !is KeyboardEvent) return
        map[event.key] = false
    }

    fun acceptLeft(event: Event) {
        if (event !is KeyboardEvent) return

        console.log("[l]: ${event.key}")
    }

    fun acceptRight(event: Event) {
        if (event !is KeyboardEvent) return

        console.log("[r]: ${event.key}")
    }

    fun poll() {
        when {
            map[UP] ?: false -> GlobalObjectContainer.rightBar.move(move)
            map[DOWN] ?: false -> GlobalObjectContainer.rightBar.move(-move)
        }
        when {
            map[W] ?: false -> GlobalObjectContainer.leftBar.move(move)
            map[S] ?: false -> GlobalObjectContainer.leftBar.move(-move)
        }
    }
}