package io.github.starlight220.pingpong.utils

import org.w3c.dom.events.Event
import org.w3c.dom.events.KeyboardEvent

object KeyboardTracker {
    private val map = HashMap<String, Boolean>()

    fun press(event: Event) {
        if (event !is KeyboardEvent) return
        map[event.key] = true
    }

    fun release(event: Event) {
        if (event !is KeyboardEvent) return
        map[event.key] = false
    }

    operator fun get(key: String) = map[key] ?: false
}