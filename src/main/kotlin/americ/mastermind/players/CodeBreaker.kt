package americ.mastermind.players

import americ.mastermind.domain.MMSet

class CodeBreaker(val size: Int) {
    fun getGuess(): MMSet {
        return MMSet.random(size)
    }
}