package io.github.starlight220.pingpong.utils

import kotlinx.browser.window

fun crash(message: String): Nothing {
    window.alert(message)
    throw Error(message)
}