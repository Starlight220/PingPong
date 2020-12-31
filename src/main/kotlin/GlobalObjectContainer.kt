package io.github.starlight220.pingpong

import io.github.starlight220.pingpong.elements.*
import io.github.starlight220.pingpong.utils.KeyboardTracker
import io.github.starlight220.pingpong.utils.register
import kotlinx.browser.document
import kotlin.math.PI
import kotlin.random.Random

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
        leftBar.y = MAX_Y/2
        leftBar.register()

        rightBar =
            PlayerBar(Side.Right, object : PlayerBarController {
                override val up: () -> Boolean = { KeyboardTracker[UP] }
                override val down: () -> Boolean = { KeyboardTracker[DOWN] }
            }, document.body!!)
        rightBar.y = MAX_Y/2
        rightBar.register()

        ball = Ball(BALL_DIAMETER, document.body!!)
        ball.location = Coordinates.cartesian(MAX_X / 2, MAX_Y / 2)
        ball.redirect(Coordinates.polar(7, getRandomInitHeading()))
        ball.register()
    }
}

private inline fun getRandomInitHeading(): Double {
    var attempt: Double
    do {
        attempt = Random.nextDouble()
    } while (attempt % 0.5 == 0.25)
    return attempt * 2 * PI
}
