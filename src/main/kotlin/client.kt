package io.github.starlight220.pingpong

import io.github.starlight220.pingpong.utils.KeyboardTracker
import io.github.starlight220.pingpong.utils.Scheduler
import io.github.starlight220.pingpong.utils.crash
import kotlinx.browser.document
import kotlinx.browser.window

fun main() {
    try {
        window.addEventListener(type = "keydown", KeyboardTracker::press)
        window.addEventListener(type = "keyup", KeyboardTracker::release)

        document.bgColor = BACKGROUND_COLOR


        Scheduler.launch(20)

    } catch(e: Throwable) {
        if(e is Error) throw e
        else crash(e.message ?: "Exception ${e::class.simpleName} thrown:\n\t${e.stackTraceToString()}")
    }
}

