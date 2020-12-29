package io.github.starlight220.pingpong.elements

import io.github.starlight220.pingpong.*
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.span
import org.w3c.dom.HTMLElement
import kotlin.math.*

class Ball(size: Int, container: HTMLElement) : GameElement {
    private var element: HTMLElement
    var location: Coordinates = Coordinates.cartesian(window.screen.availWidth/2, window.screen.availHeight/2)
        set(value) {
            if(value.dy.toInt() !in fieldBoundariesY || value.dx.toInt() !in fieldBoundariesX) {
                val (r, theta) = value.polar
                redirect(Coordinates.polar(-r, theta))
                return
            }
            field = value
            console.log(field.toString())
            element.style.left = "${field.dx.toInt()}px"
            element.style.top = "${field.dy.toInt()}px"
        }

    /**
     * radians
     */
    var heading: Number = 0
    var speed: Number = 0

    init {
        container.append {
            span { id = "ball" }.style.apply {
                position = "absolute"
                height = "${size}px"
                width = "${size}px"
                backgroundColor = BALL_COLOR
                borderRadius = "50%"
                display = "inline-block"
                marginLeft = "20px"
                marginRight = "20px"
            }
        }
        element = (document.getElementById("ball") as? HTMLElement
            ?: crash("No element found for id: ${"ball"}"))
    }

    fun move(transform: Coordinates) {
        location += transform
    }

    fun redirect(transform: Coordinates) {
        val (r, theta) = transform.polar
        speed = r
        heading = theta
    }

    override fun periodic() {
        move(Coordinates.polar(speed.toDouble(), heading.toDouble()))
        console.log("[${location.dx}, ${location.dy}, $speed, $heading]")
    }
}

data class Coordinates private constructor(val dx: Double, val dy: Double) {
    companion object {
        fun cartesian(dx: Number, dy: Number) = Coordinates(dx.toDouble(), dy.toDouble())

        /**
         * @param theta angle in radians
         */
        fun polar(r: Number, theta: Double) =
            Coordinates(r.toDouble() * cos(PI -theta), r.toDouble() * sin(theta))

//        fun random() = polar(
//            INIT_BALL_SPEED,
//            2*PI
//        )
    }

    val polar get() = Pair(hypot(dx, dy), asin(dy/hypot(dx, dy)))

    operator fun plus(other: Coordinates) = Coordinates(this.dx + other.dx, this.dy + other.dy)
    operator fun minus(other: Coordinates) = Coordinates(this.dx - other.dx, this.dy - other.dy)
}
