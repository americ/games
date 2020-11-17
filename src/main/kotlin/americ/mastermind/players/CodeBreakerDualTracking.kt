package americ.mastermind.players

import americ.mastermind.domain.MMSet

class CodeBreakerDualTracking(val size: Int): CodeBreaker {

    val solutionSpace = MMSet.createAllSets(size).toMutableSet()
    val guesses = mutableSetOf<MMSet>()

    override fun getGuess(): MMSet {
        var guess: MMSet
        do {
            guess = MMSet.random(size)
        } while (guesses.contains(guess))

        guesses.add(guess)
        solutionSpace.remove(guess)
        return guess
    }
}