package americ.mastermind.domain

/**
 * Assignment of one marker (black, white, or empty) to each peg in a set.
 */
data class MMAssignment(val guess: MMSet, val markers: MutableList<MMMarker>) {

    val size = guess.pegs.size

    companion object {
        fun createAll(guess: MMSet, assessment: MMAssessment): List<MMAssignment> {
            val markers = assessment.getMarkers()
            val allAssignments = buildAssignments(guess, markers, 0)
            return allAssignments.toSet().toList()
        }

        private fun buildAssignments(guess: MMSet, markers: List<MMMarker>, index: Int): List<MMAssignment> {
            val size = guess.pegs.size
            if (index == size) {
                return listOf(MMAssignment(guess, mutableListOf()))
            } else {
                val indexList = (0..(markers.size - 1)).toList()
                return indexList.map { markerIndex ->
                    val otherIndices = indexList - markerIndex
                    val marker = markers[markerIndex]
                    val otherMarkers = otherIndices.map { markers[it] }

                    val childrenAssignmentList = buildAssignments(guess, otherMarkers, index + 1)
                    childrenAssignmentList.forEach { assignment ->
                        assignment.markers.add(marker)
                    }
                    childrenAssignmentList
                }.flatten()
            }
        }
    }

    fun createAllSolutions(): List<MMSet> {
        return buildSolutions(MMSet(mutableListOf()), 0)
    }

    private fun buildSolutions(set: MMSet, index: Int): List<MMSet> {
        if (index == size) {
            return listOf(set)
        } else {
            val guessPeg = guess.pegs[index]
            when (markers[index]) {
                MMMarker.BLACK -> {
                    set.pegs.add(guessPeg)
                    return buildSolutions(set, index + 1)
                }
                MMMarker.WHITE, MMMarker.EMPTY -> {
                    val solutionPegs = MMPeg.values().toMutableSet()
                    solutionPegs.remove(guessPeg)
                    return solutionPegs.map { solutionPeg ->
                        val childSet = set.deepCopy()
                        childSet.pegs.add(solutionPeg)
                        buildSolutions(childSet, index + 1)
                    }.flatten()
                }
            }
        }
    }
}
