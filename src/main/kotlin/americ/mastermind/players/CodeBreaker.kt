package americ.mastermind.players

import americ.mastermind.domain.MMAssessment
import americ.mastermind.domain.MMSet

interface CodeBreaker {
    fun getGuess(): MMSet
    fun reportAssessment(assessment: MMAssessment)
}