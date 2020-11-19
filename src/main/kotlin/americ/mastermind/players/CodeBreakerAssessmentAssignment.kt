package americ.mastermind.players

import americ.mastermind.domain.MMAssessment
import americ.mastermind.domain.MMAssignment
import americ.mastermind.domain.MMGuessAssessment
import americ.mastermind.domain.MMSet

/**
 * Uses the assessment to make all possible assignments of markers to pegs. For each assignment,
 * generates all possible solutions. Removes from the solution space any solution that's not in
 * the generated possible solutions.
 */
class CodeBreakerAssessmentAssignment(val size: Int) : CodeBreaker {

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

        val assignments = MMAssignment.createAll(lastGuess, assessment)

        val possibleSolutions = assignments.map { assignment ->
            assignment.createAllSolutions()
        }.flatten().toSet()

        solutionSpace.removeIf { solution ->
            !possibleSolutions.contains(solution)
        }
    }
}