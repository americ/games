package americ.mastermind.players

import americ.mastermind.domain.MMAssessment
import americ.mastermind.domain.MMSet

class CodeMaker(val size: Int) {

    fun createSolution(): MMSet {
        return MMSet.random(size)
    }

    fun assess(solution: MMSet, guess: MMSet): MMAssessment {
        return MMAssessment.from(solution, guess, size)
    }

    private fun verifySize(set: MMSet) {
        if (set.pegs.size != size) {
            throw Exception("MMSet size (${set.pegs.size}) does not match code maker size ($size)")
        }
    }
}