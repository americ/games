package americ.mastermind.players

import americ.mastermind.domain.MMAssessment
import americ.mastermind.domain.MMGuessAssessment
import americ.mastermind.domain.MMPeg
import americ.mastermind.domain.MMSet

/**
 * Eliminates solutions from the problem space when an assessment has no black or white marks.
 */
class CodeBreakerBlankRule(val size: Int) : CodeBreaker {

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

        if ((assessment.blackCount + assessment.whiteCount) == 0) {
            val guessPegSet = lastGuess.pegs.toSet()
            solutionSpace.removeIf { solution ->
                solutionContainsAny(solution, guessPegSet)
            }
        }
    }

    private fun solutionContainsAny(solution: MMSet, guessPegSet: Set<MMPeg>): Boolean {
        val solutionPegs = solution.pegs.toSet()
        return solutionPegs.intersect(guessPegSet).isNotEmpty()
    }
}