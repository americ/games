package americ.mastermind.domain

data class MMSet(val pegs: MutableList<MMPeg>) {
    companion object {
        fun random(size: Int): MMSet {
            return MMSet(
                (1..size).map { MMPeg.random() }.toMutableList()
            )
        }

        fun createAllSets(size: Int): List<MMSet> {
            return buildSets(size, 0)
        }

        private fun buildSets(size: Int, index: Int): List<MMSet> {
            if (index == size) {
                return listOf(MMSet(mutableListOf()))
            } else {
                val recursionResult = MMPeg.values().map { peg ->
                    val childrenSetList = buildSets(size, index + 1)
                    childrenSetList.forEach { set ->
                        set.pegs.add(peg)
                    }
                    childrenSetList
                }.flatten<MMSet>()
                return recursionResult
            }
        }
    }

    fun getByIndices(indices: List<Int>): List<MMPeg> {
        return indices.map { pegs[it] }
    }

    fun deepCopy(): MMSet {
        return copy(pegs = pegs.toMutableList())
    }
}