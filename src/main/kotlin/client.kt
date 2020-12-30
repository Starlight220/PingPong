package io.github.starlight220.pingpong

import io.github.starlight220.pingpong.elements.*
import io.github.starlight220.pingpong.utils.KeyboardTracker
import kotlinx.browser.document
import kotlinx.browser.window
import kotlin.math.PI

fun main() {
    try {
        window.addEventListener(type = "keydown", KeyboardTracker::press)
        window.addEventListener(type = "keyup", KeyboardTracker::release)

        document.bgColor = BACKGROUND_COLOR

        GlobalObjectContainer.init()

        Scheduler.launch(200)

    } catch(e: Throwable) {
        if(e is Error) throw e
        else crash(e.message ?: "Exception ${e::class.simpleName} thrown:\n\t${e.stackTraceToString()}")
    }
}

fun crash(message: String): Nothing {
    window.alert(message)
    throw Error(message)
}

object GlobalObjectContainer {
    lateinit var rightBar: PlayerBar
    lateinit var leftBar: PlayerBar
    private lateinit var ball: Ball
    fun init() {
        leftBar =
            PlayerBar(Side.Left, object : PlayerBarController {
                override val up: () -> Boolean = { KeyboardTracker[W] }
                override val down: () -> Boolean = { KeyboardTracker[S] }
            }, document.body!!)
        leftBar.register()

        rightBar =
            PlayerBar(Side.Right, object : PlayerBarController {
                override val up: () -> Boolean = { KeyboardTracker[UP] }
                override val down: () -> Boolean = { KeyboardTracker[DOWN] }
            }, document.body!!)
        rightBar.register()

        ball = Ball(BALL_DIAMETER, document.body!!)
        ball.location = Coordinates.cartesian(MAX_X/2, MAX_Y/2)
        ball.redirect(Coordinates.polar(10, PI * 0.25))
        ball.register()
    }
}
