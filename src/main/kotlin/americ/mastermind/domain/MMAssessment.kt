package americ.mastermind.domain

data class MMAssessment(
    val blackCount: Int,
    val whiteCount: Int
) {
    companion object {
        fun from(solution: MMSet, guess: MMSet, size: Int): MMAssessment {
            verifySize(solution, size)
            verifySize(guess, size)
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

        private fun verifySize(set: MMSet, size: Int) {
            if (set.pegs.size != size) {
                throw Exception("MMSet size (${set.pegs.size}) does not match game size ($size)")
            }
        }
    }
}