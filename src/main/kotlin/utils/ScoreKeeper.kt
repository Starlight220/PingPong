package io.github.starlight220.pingpong.utils

import io.github.starlight220.pingpong.elements.Side
import kotlinx.browser.window

object ScoreKeeper {
    fun goal(side: Side) {
        window.alert("${(!side).name} wins!")
        Scheduler.close()
    }
}
