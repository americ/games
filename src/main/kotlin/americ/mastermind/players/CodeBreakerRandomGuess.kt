package americ.mastermind.players

import americ.mastermind.domain.MMAssessment
import americ.mastermind.domain.MMSet

class CodeBreakerRandomGuess(val size: Int): CodeBreaker {
    override fun getGuess(): MMSet {
        return MMSet.random(size)
    }

    override fun reportAssessment(assessment: MMAssessment) {
    }
}