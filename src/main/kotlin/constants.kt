package io.github.starlight220.pingpong

import kotlinx.browser.document


const val STEP: Int = 10
const val BAR_HEIGHT: Int = 100
const val BAR_WIDTH: Int = 10

const val BACKGROUND_COLOR = "#fff000"

const val LEFT_PLAYER_COLOR = "#1229b0"
const val RIGHT_PLAYER_COLOR = "#e3102c"

const val BALL_COLOR: String = "#10e37d"
const val BALL_DIAMETER: Int = 50
//const val INIT_BALL_SPEED: Double = 20.0
const val MAX_SPEED: Double = 20.0


const val UP = "ArrowUp"
const val DOWN = "ArrowDown"
const val W = "w"
const val S = "s"

const val MAX_Y = 700//window.screen.availHeight
val MAX_X = document.body!!.clientWidth*5/6

const val MIN_Y = 10
const val MIN_X = 0

const val SIDE_OFFSET = 20