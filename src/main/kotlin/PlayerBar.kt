package io.github.starlight220.pingpong

import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.div
import kotlinx.html.dom.append
import kotlinx.html.id
import org.w3c.dom.HTMLElement

private val range = 10..600
enum class Side { Right, Left }
class PlayerBar(side: Side, container: HTMLElement) {
    private var element: HTMLElement
    var y: Int
        get() = element.style.marginTop.removeSuffix("px").toInt()
        set(value) {
            if (!range.contains(value)) return
            element.style.marginTop = "${value}px"
        }

    init {
        val _id = "bar" + side.name
        container.append {
            div { id = _id }.style.apply {
                position = "absolute"
                when (side) {
                    Side.Right -> right = "20px"
                    Side.Left -> left = "20px"
                }
                marginTop = "10px"
                marginBottom = "20px"
                height = "100px"
                width = "10px"
                backgroundColor = "#aaaaaa"
            }
        }
        element =
            (document.getElementById(_id) as? HTMLElement ?: crash("No element found for id: $_id"))
    }

    /**
     * Move the player's bar a certain distance, in pixels.
     * Positive is up.
     */
    fun move(diff: Int) {
        // invert so positive is up.
        y -= diff
    }
}

fun crash(message: String): Nothing {
    window.alert(message)
    throw Error(message)
}


