package americ.mastermind.players

import americ.mastermind.domain.MMSet

class CodeBreakerRandomGuess(val size: Int): CodeBreaker {
    override fun getGuess(): MMSet {
        return MMSet.random(size)
    }
}