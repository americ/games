package americ.mastermind.players

import americ.mastermind.domain.MMAssessment
import americ.mastermind.domain.MMGuessAssessment
import americ.mastermind.domain.MMSet

class CodeBreakerAssessmentTracking(val size: Int) : CodeBreaker {

    val solutionSpace = MMSet.createAllSets(size).toMutableSet()
    val guesses = mutableSetOf<MMSet>()
    val guessAssessments = mutableListOf<MMGuessAssessment>()

    lateinit var lastGuess: MMSet

    override fun getGuess(): MMSet {
        var guess: MMSet
        do {
            guess = MMSet.random(size)
        } while (guesses.contains(guess))

        recordGuess(guess)
        return guess
    }

    private fun recordGuess(guess: MMSet) {
        lastGuess = guess
        guesses.add(guess)
        solutionSpace.remove(guess)
    }

    override fun reportAssessment(assessment: MMAssessment) {
        guessAssessments.add(MMGuessAssessment(lastGuess, assessment))
    }
}