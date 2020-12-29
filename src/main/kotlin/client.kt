package io.github.starlight220.pingpong

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.w3c.dom.HTMLBodyElement

val body: HTMLBodyElement = document.body as? HTMLBodyElement ?: crash("body is null")

fun main() {
//    window.addEventListener(type = "keydown", { window.alert((it as KeyboardEvent).key) })
    window.addEventListener(type = "keydown", KeyboardTracker::press)
    window.addEventListener(type = "keyup", KeyboardTracker::release)
    GlobalObjectContainer.leftBar = PlayerBar(Side.Left, body)
    GlobalObjectContainer.rightBar = PlayerBar(Side.Right, body)
    document.bgColor = "#000fff"
    GlobalScope.launch { while(true) {
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