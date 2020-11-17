package americ.mastermind.players

import americ.mastermind.domain.MMAssessment
import americ.mastermind.domain.MMGuessAssessment
import americ.mastermind.domain.MMSet

/**
 * Eliminates solutions from the problem space that produce different assessments from last guess.
 */
class CodeBreakerFullAssessment(val size: Int) : CodeBreaker {

    val solutionSpace = MMSet.createAllSets(size).toMutableSet()
    val guesses = mutableSetOf<MMSet>()
    val guessAssessments = mutableListOf<MMGuessAssessment>()

    lateinit var lastGuess: MMSet

    override fun getGuess(): MMSet {
        val guess = solutionSpace.first()
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

        solutionSpace.removeIf { solution ->
            MMAssessment.from(solution, lastGuess, size) != assessment
        }
    }
}