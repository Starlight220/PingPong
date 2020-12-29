package io.github.starlight220.pingpong

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    window.addEventListener(type = "keydown", KeyboardTracker::press)
    window.addEventListener(type = "keyup", KeyboardTracker::release)
    GlobalObjectContainer.leftBar =
        PlayerBar(Side.Left, document.body!!)
    GlobalObjectContainer.rightBar =
        PlayerBar(Side.Right, document.body!!)
    document.bgColor = "#000fff"
    GlobalScope.launch {
        while (true) {
            Logic.runLoop()
            delay(20)
        }
    }
}

object GlobalObjectContainer {
    lateinit var rightBar: PlayerBar
    lateinit var leftBar: PlayerBar
    // ball
}

fun crash(message: String): Nothing {
    window.alert(message)
    throw Error(message)
}