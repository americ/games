package americ.mastermind.players

import americ.mastermind.domain.MMAssessment
import americ.mastermind.domain.MMPeg
import americ.mastermind.domain.MMSet

class CodeMaker(val size: Int) {

    fun createSolution(): MMSet {
        return MMSet.random(size)
    }

    fun assess(solution: MMSet, guess: MMSet): MMAssessment {
        verifySize(solution)
        verifySize(guess)
        if (solution == guess) {
            return MMAssessment(size, 0)
        } else {
            var blackCount = 0
            val unmatchedIndices = mutableListOf<Int>()
            (0..(size - 1)).forEach {
                if (solution.pegs[it] == guess.pegs[it]) {
                    blackCount++
                } else {
                    unmatchedIndices.add(it)
                }
            }

            val unmatchedSolutionPegs = solution.getByIndices(unmatchedIndices).toMutableList()
            val unmatchedGuessPegs = guess.getByIndices(unmatchedIndices)

            var whiteCount = 0
            unmatchedGuessPegs.forEach { guessPeg ->
                if (unmatchedSolutionPegs.contains(guessPeg)) {
                    whiteCount++
                    unmatchedSolutionPegs.removeAt(unmatchedSolutionPegs.indexOfFirst { it == guessPeg })
                }
            }

            return MMAssessment(blackCount, whiteCount)
        }
    }

    private fun verifySize(set: MMSet) {
        if (set.pegs.size != size) {
            throw Exception("MMSet size (${set.pegs.size}) does not match code maker size ($size)")
        }
    }

    fun createAllSolutions(): List<MMSet> {
        return buildSolutions(0)
    }

    private fun buildSolutions(index: Int): List<MMSet> {
//        val indent = " ".repeat(index)
        if (index == size) {
//            println("$indent - termination")
            return listOf(MMSet(mutableListOf()))
        } else {
//            println("$indent - recursing")
            val recursionResult = MMPeg.values().map { peg ->
                val childrenSetList = buildSolutions(index + 1)
                childrenSetList.forEach { set ->
                    set.pegs.add(peg)
                }
                childrenSetList
            }.flatten<MMSet>()
//            println("$indent - recursion result length: ${recursionResult.size}")
            return recursionResult
        }
    }
}