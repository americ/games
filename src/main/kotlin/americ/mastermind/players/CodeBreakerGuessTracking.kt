package americ.mastermind.players

import americ.mastermind.domain.MMAssessment
import americ.mastermind.domain.MMSet

class CodeBreakerGuessTracking(val size: Int): CodeBreaker {

    val guesses = mutableSetOf<MMSet>()

    override fun getGuess(): MMSet {
        var guess: MMSet
        do {
            guess = MMSet.random(size)
        } while (guesses.contains(guess))
        guesses.add(guess)
        return guess
    }

    override fun reportAssessment(assessment: MMAssessment) {
    }
}