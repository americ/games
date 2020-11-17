package americ.mastermind.domain

data class MMSet(val pegs: MutableList<MMPeg>) {
    companion object {
        fun random(size: Int): MMSet {
            return MMSet(
                (1..size).map { MMPeg.random() }.toMutableList()
            )
        }
    }

    fun getByIndices(unmatchedIndices: List<Int>): List<MMPeg> {
        return unmatchedIndices.map { pegs[it] }
    }
}