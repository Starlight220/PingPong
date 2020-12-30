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
    var location: Coordinates =
        Coordinates.cartesian(window.screen.availWidth / 2, window.screen.availHeight / 2)
        set(value) {
            if (this.collideY(value)) return
            if (this.collideX(value)) return
            field = value
            console.log(field.toString())
            this.element.style.left = "${field.dx.toInt()}px"
            this.element.style.top = "${field.dy.toInt()}px"
        }

    private fun collideX(coords: Coordinates): Boolean {
        val (x, y) = coords
        console.log(coords)
        if (x > MIN_X + SIDE_OFFSET + BAR_WIDTH && x + BALL_DIAMETER < MAX_X - SIDE_OFFSET - BAR_WIDTH) {
            console.log("NO COLLISION") // DEBUG

            return false
        }
        if (x < MIN_X + BAR_WIDTH || x + BALL_DIAMETER > MAX_X - BAR_WIDTH) {
            val currentSpeed = this.speed
            val currentHeading = this.heading

            this.speed = currentSpeed.toDouble()
            this.heading = PI - currentHeading.toDouble()

            console.log("WALL COLLISION") // DEBUG

            return true
        }
        fun barCollision(bar: Int): Boolean {
            if (y.toInt() in bar..(bar + BAR_HEIGHT)) {
                val currentSpeed = this.speed
                val currentHeading = this.heading

                this.speed = currentSpeed.toDouble()
                this.heading = PI - currentHeading.toDouble()

                console.log("COLLISION HANDLED") // DEBUG

                return true
            }
            console.log("MISSED COLLISION") // DEBUG
            return false
        }
        if (x < MIN_X + SIDE_OFFSET + BAR_WIDTH) {
            // left bar collision
            val leftBarY = GlobalObjectContainer.leftBar.y

            console.log("[LEFT] COLLISION") // DEBUG

            return barCollision(leftBarY)
        } else if (x + BALL_DIAMETER > MAX_X - SIDE_OFFSET - BAR_WIDTH) {
            // right bar collision
            val rightBarY = GlobalObjectContainer.rightBar.y

            console.log("[RIGHT] COLLISION") // DEBUG

            return barCollision(rightBarY)
        }

        crash("ERROR at Ball::collideX [coords= $coords]")
    }

    private fun collideY(coords: Coordinates): Boolean {
        val (_, y) = coords
        if (y > MIN_Y && y < MAX_Y - BALL_DIAMETER) {
            return false
        }

        val currentSpeed = this.speed
        val currentHeading = this.heading

        this.speed = currentSpeed.toDouble()
        this.heading = PI - currentHeading.toDouble()

        return true
    }

    /**
     * radians
     */
    private var heading: Number = 0
    private var speed: Number = 0
        set(value) = if (value.toDouble() <= MAX_SPEED) field = value else { console.warn("failed to set speed $value")}

    init {
        container.append {
            this.span { this.id = "ball" }.style.apply {
                this.position = "absolute"
                this.height = "${size}px"
                this.width = "${size}px"
                this.backgroundColor = BALL_COLOR
                this.borderRadius = "50%"
                this.display = "inline-block"
            }
        }
        this.element = (document.getElementById("ball") as? HTMLElement
            ?: crash("No element found for id: ${"ball"}"))
    }

    private fun move(transform: Coordinates) {
        this.location += transform
    }

    fun redirect(transform: Coordinates) {
        val (r, theta) = transform.polar
        this.speed = r
        this.heading = theta
    }

    override fun periodic() {
        val transform = Coordinates.polar(this.speed.toDouble(), this.heading.toDouble())
        console.info(transform)
        this.move(transform)
        console.log("[${this.location.dx}, ${this.location.dy}, ${this.speed}, ${this.heading}]")
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

    val polar get() = Pair(hypot(this.dx, this.dy), asin(this.dy / hypot(this.dx, this.dy)))

    operator fun plus(other: Coordinates) = Coordinates(this.dx + other.dx, this.dy + other.dy)
    operator fun minus(other: Coordinates) = Coordinates(this.dx - other.dx, this.dy - other.dy)
}
