package americ.domain

data class DieRoll(val value: Int) {

    companion object {
        val valueRange = 1..6
        val valueSequence: List<Int> = valueRange.toList()
    }

    init {
        if (!valueRange.contains(value)) {
            throw Exception("Die Roll value must be in the range 1..6")
        }
    }

    fun getOtherRolls(): List<DieRoll> {
        val values = valueSequence.toMutableList()
        values.remove(value)
        return values.map { DieRoll(it) }
    }

    override fun toString() = value.toString()
}