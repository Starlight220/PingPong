package io.github.starlight220.pingpong.elements

import io.github.starlight220.pingpong.*
import kotlinx.browser.document
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.id
import org.w3c.dom.HTMLElement
import org.w3c.dom.css.CSSStyleDeclaration

enum class Side(
    private val roffset: Int? = null,
    private val loffset: Int,
    private val color: String
) {
    Right(loffset = MAX_X - SIDE_OFFSET, color = RIGHT_PLAYER_COLOR),
    Left(loffset = SIDE_OFFSET, color = LEFT_PLAYER_COLOR);

    val id = "bar" + this.name
    fun applyStyle(style: CSSStyleDeclaration) = with(style) {
        position = "absolute"
        marginTop = "10px"
        top = "10px"
        left = "${loffset}px"
        roffset?.let { right = "${it}px" }
//        ?.let { left = "${it}px" }
        marginBottom = "20px"
        height = "${BAR_HEIGHT}px"
        width = "${BAR_WIDTH}px"
        backgroundColor = this@Side.color
    }
}

class PlayerBar(side: Side, private val controls: PlayerBarController, container: HTMLElement) :
    GameElement {
    private var speed = STEP
    private var element: HTMLElement
    var y: Int
        get() = element.style.top.removeSuffix("px").toInt()
        set(value) {
            if (value !in MIN_Y..(MAX_Y - BAR_HEIGHT)) return
            element.style.top = "${value}px"
        }

    init {
        container.append {
            side.applyStyle(div { id = side.id }.style)
        }
        element =
            (document.getElementById(side.id) as? HTMLElement
                ?: crash("No element found for id: ${side.id}"))
    }

    /**
     * Move the player's bar a certain distance, in pixels.
     * Positive is up.
     */
    private fun move(diff: Int) {
        // invert so positive is up.
        y -= diff
    }

    override fun periodic() {
        when {
            controls.up() -> move(speed)
            controls.down() -> move(-speed)
        }
    }
}

interface PlayerBarController {
    val up: () -> Boolean
    val down: () -> Boolean
}
