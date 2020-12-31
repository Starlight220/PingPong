package io.github.starlight220.pingpong.elements

import io.github.starlight220.pingpong.*
import io.github.starlight220.pingpong.utils.GameElement
import io.github.starlight220.pingpong.utils.ScoreKeeper
import io.github.starlight220.pingpong.utils.crash
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.span
import org.w3c.dom.HTMLElement
import kotlin.math.*

class Ball(size: Int, container: HTMLElement) : GameElement {
    private var element: HTMLElement
    var location: Coordinates =
        Coordinates.cartesian(window.screen.availWidth / 2, window.screen.availHeight / 2)
        set(value) {
            collideX(value)
            collideY(value)
            field = value
//            console.log(field.toString())
            element.style.left = "${field.dx.toInt()}px"
            element.style.top = "${field.dy.toInt()}px"
        }
    /**
     * radians
     */
    private var heading: Number = 0
    private var speed: Number = 0
        set(value) = if (value.toDouble() <= MAX_SPEED) field = value else { console.warn("failed to set speed $value")}

    private fun collideX(coords: Coordinates) {
        val (x, y) = coords
//        console.log(coords)
        if (x > MIN_X + SIDE_OFFSET + BAR_WIDTH && x + BALL_DIAMETER < MAX_X - SIDE_OFFSET - BAR_WIDTH) {
            console.log("NO COLLISION") // DEBUG
            return
        }
        // left goal
        if (x < MIN_X + SIDE_OFFSET) {
            speed = 0
            heading = 0
            ScoreKeeper.goal(Side.Left)
            return
        }
        // right goal
        if (x + BALL_DIAMETER > MAX_X - SIDE_OFFSET) {
            speed = 0
            heading = 0
            ScoreKeeper.goal(Side.Right)
            return
        }
        inline fun barCollision(bar: Int) {
            if (y.toInt() in bar..(bar + BAR_HEIGHT)) {
                heading = PI-heading.toDouble()
                console.log("COLLISION HANDLED") // DEBUG
            } else console.log("MISSED COLLISION") // DEBUG
        }
        if (x < MIN_X + (SIDE_OFFSET + BAR_WIDTH)) {
            // left bar collision
            val leftBarY = GlobalObjectContainer.leftBar.y
            console.log("[LEFT] COLLISION") // DEBUG
            barCollision(leftBarY)
        } else if (x + BALL_DIAMETER > MAX_X - (SIDE_OFFSET + BAR_WIDTH)) {
            // right bar collision
            val rightBarY = GlobalObjectContainer.rightBar.y
            console.log("[RIGHT] COLLISION") // DEBUG
            barCollision(rightBarY)
        }
    }

    private fun collideY(coords: Coordinates): Boolean {
        val (_, y) = coords
        if (y > MIN_Y && y < MAX_Y - BALL_DIAMETER) {
            return false
        }
        console.log("[Y] COLLISION")
        heading = - heading.toDouble()
        return true
    }

    init {
        container.append {
            span { this.id = "ball" }.style.apply {
                position = "absolute"
                height = "${size}px"
                width = "${size}px"
                backgroundColor = BALL_COLOR
                borderRadius = "50%"
                display = "inline-block"
            }
        }
        element = (document.getElementById("ball") as? HTMLElement
            ?: crash("No element found for id: ${"ball"}"))
    }

    private fun move(transform: Coordinates) {
        location += transform
    }

    fun redirect(transform: Coordinates) {
        val (r, theta) = transform.polar
        speed = r
        heading = theta
    }

    override fun periodic() {
        val transform = Coordinates.polar(speed.toDouble(), heading.toDouble())
        console.info(transform)
        move(transform)
        console.log("[${location.dx}, ${location.dy}, ${speed}, ${heading}]")
    }
}

data class Coordinates(val dx: Double, val dy: Double) {
    companion object {
        fun cartesian(dx: Number, dy: Number) = Coordinates(dx.toDouble(), dy.toDouble())

        /**
         * @param theta angle in radians
         */
        fun polar(r: Number, theta: Double) =
            Coordinates(r.toDouble() * cos(theta), r.toDouble() * sin(theta))

//        fun random() = polar(
//            INIT_BALL_SPEED,
//            2*PI
//        )
    }

    val polar get() = Pair(hypot(dx, dy), atan(dy/dx))

    operator fun plus(other: Coordinates) = Coordinates(dx + other.dx, dy + other.dy)
    operator fun minus(other: Coordinates) = Coordinates(dx - other.dx, dy - other.dy)
}
