package americ.domain

import java.util.*
import kotlin.collections.LinkedHashMap

data class Roll6(val rolls: Array<DieRoll>) {

    companion object {
        fun from(vararg varargRolls: DieRoll): Roll6 {
            return Roll6(varargRolls as Array<DieRoll>)
        }

        fun dedup(rollList: List<Roll6>): List<Roll6> {

            val sortedRollStringToFirstRoll = LinkedHashMap<String, Roll6>()

            rollList.forEach { roll ->
                val sortedDieRolls = roll.getSortedRoll().toString()
                if (!sortedRollStringToFirstRoll.containsKey(sortedDieRolls)) {
                    sortedRollStringToFirstRoll[sortedDieRolls] = roll
                }
            }

            return sortedRollStringToFirstRoll.values.toList()
        }
    }

    init {
        if (rolls.size != 6) {
            throw Exception("Roll6 array must have six elements")
        }
    }

    val grouped = rolls.groupBy { it.value }
    val sorted = rolls.sortedBy { it.value }

    fun getSortedRoll(): Roll6 {
        return Roll6(sorted.toTypedArray())
    }

    fun isTwoTriples(): Boolean {
        return (grouped.size == 2) &&
                (grouped[grouped.keys.first()]!!.size == 3) &&
                (grouped[grouped.keys.last()]!!.size == 3)
    }

    fun isThreePairs(): Boolean {
        return (grouped.size == 3) &&
                (grouped[grouped.keys.first()]!!.size == 2) &&
                (grouped[grouped.keys.last()]!!.size == 2)
    }

    fun isAStraight(): Boolean {
        return sorted.map { it.value } == listOf(1, 2, 3, 4, 5, 6)
    }

    fun isFourTwo(): Boolean {
        val groupSizes = grouped.values.map { it.size }.sorted()
        return (grouped.size == 2) && (groupSizes == listOf(2, 4))
    }

    fun isSixOfAKind(): Boolean {
        return (grouped.size == 1)
    }

    fun isFiveOfAKind(): Boolean {
        val groupSizes = grouped.values.map { it.size }.sorted()
        return (grouped.size == 2) && (groupSizes == listOf(1, 5))
    }

    fun isFourOfAKind(): Boolean {
        val groupSizes = grouped.values.map { it.size }.sorted()
        return (grouped.size == 3) && (groupSizes == listOf(1, 1, 4))
    }

    override fun toString(): String {
        return rolls.map { it.toString() }.joinToString("")
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) {
            return false
        }

        other as Roll6

        if (!Arrays.equals(rolls, other.rolls)) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(rolls)
    }

    fun generateAllSingleDieVariations(): List<Roll6> {
        return rolls.mapIndexed { index, dieRoll ->
            val otherRolls = dieRoll.getOtherRolls()

            otherRolls.map { otherRoll ->
                val tempRolls = rolls.toMutableList()
                tempRolls[index] = otherRoll
                Roll6(tempRolls.toTypedArray())
            }
        }.flatten()
    }
}
