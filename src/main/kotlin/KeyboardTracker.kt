package io.github.starlight220.pingpong

import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent

object KeyboardTracker {
    private const val UP = "ArrowUp"
    private const val DOWN = "ArrowDown"
    private const val W = "w"
    private const val S = "s"
    private val map = HashMap<String, Boolean>()

    fun press(event: Event) {
        if(event !is KeyboardEvent) return
        map[event.key] = true
    }
    fun release(event: Event) {
        if(event !is KeyboardEvent) return
        map[event.key] = false
    }

    fun poll() {
        when {
            map[UP] ?: false -> GlobalObjectContainer.rightBar.move(STEP)
            map[DOWN] ?: false -> GlobalObjectContainer.rightBar.move(-STEP)
        }
        when {
            map[W] ?: false -> GlobalObjectContainer.leftBar.move(STEP)
            map[S] ?: false -> GlobalObjectContainer.leftBar.move(-STEP)
        }
    }
}