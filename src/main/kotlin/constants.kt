package io.github.starlight220.pingpong

import kotlinx.browser.window

const val STEP: Int = 10
const val BAR_HEIGHT: Int = 100

const val BACKGROUD_COLOR = "#fff000"

const val LEFT_PLAYER_COLOR = "#1229b0"
const val RIGHT_PLAYER_COLOR = "#e3102c"

const val BALL_COLOR: String = "#10e37d"
const val BALL_DIAMETER: Int = 50
const val INIT_BALL_SPEED: Double = 20.0


const val UP = "ArrowUp"
const val DOWN = "ArrowDown"
const val W = "w"
const val S = "s"

val MAX_Y = window.screen.availHeight
val MAX_X = window.screen.availWidth

val fieldBoundariesX = 20..MAX_X
val fieldBoundariesY = 10..(3*MAX_Y/4)
