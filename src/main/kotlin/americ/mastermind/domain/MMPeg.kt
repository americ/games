package americ.mastermind.domain

import kotlin.math.roundToInt

enum class MMPeg {
    RED,
    GREEN,
    BLUE,
    YELLOW,
    WHITE,
    BLACK;

    companion object {
        fun random(): MMPeg {
            val ord = ((Math.random() * 100).roundToInt() % values().size)
            return values().find { it.ordinal == ord }!!
        }
    }
}