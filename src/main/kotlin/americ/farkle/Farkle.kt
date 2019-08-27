package americ.farkle

import americ.domain.DieRoll
import americ.domain.Roll6

object Farkle {

    @JvmStatic
    fun main(args: Array<String>) {
        val allPossibleRollsWithDups = generateAllPossibleRolls()

        val allPossibleRolls = Roll6.dedup(allPossibleRollsWithDups)

        val specialsNonSpecials = allPossibleRolls.groupBy { roll6 -> isSpecial(roll6) }
        val specialsNonSpecialsWithDups = allPossibleRollsWithDups.groupBy { roll6 -> isSpecial(roll6) }

        val specials = specialsNonSpecials[true]!!
        val nonSpecials = specialsNonSpecials[false]!!

        val specialsWithDups = specialsNonSpecialsWithDups[true]!!
        val nonSpecialsWithDups = specialsNonSpecialsWithDups[false]!!

        val oneOffSpecialMap = mutableMapOf<Roll6, List<Roll6>>()
        nonSpecials.forEach { nonSpecial ->
            val singleDieVariations = nonSpecial.generateAllSingleDieVariations()
            val oneOffSpecials = Roll6.dedup(singleDieVariations.filter { isSpecial(it) })

            println("Non-Special: ${nonSpecial} -> one-away specials: ${oneOffSpecials}")

            if (!oneOffSpecials.isEmpty()) {
                oneOffSpecialMap[nonSpecial] = oneOffSpecials
            }
        }

        println()
        println("Farkle Results")
        println("  ${allPossibleRollsWithDups.size} All possible rolls")
        println("  ${specialsWithDups.size} Special rolls")
        println("  ${nonSpecialsWithDups.size} Non-special rolls")
        println()
        println("Deduped:")
        println("  ${allPossibleRolls.size} All possible rolls")
        println("  ${specials.size} Special rolls")
        println("  ${nonSpecials.size} Non-special rolls")
        println()
        println("  ${oneOffSpecialMap.size} Non-special rolls one away from being a special")
        println("  ${nonSpecials.size - oneOffSpecialMap.size} Non-special rolls NOT one away from being a special")
    }

    private fun isSpecial(roll: Roll6): Boolean {
        return roll.isTwoTriples() ||
                roll.isThreePairs() ||
                roll.isAStraight() ||
                roll.isFourTwo() ||
                roll.isSixOfAKind() ||
                roll.isFiveOfAKind() ||
                roll.isFourOfAKind()
    }

    private fun generateAllPossibleRolls(): List<Roll6> {
        val dieRolls = (1..6).map { DieRoll(it) }

        val rolls = mutableListOf<Roll6>()
        dieRolls.forEach { die1 ->
            dieRolls.forEach { die2 ->
                dieRolls.forEach { die3 ->
                    dieRolls.forEach { die4 ->
                        dieRolls.forEach { die5 ->
                            dieRolls.forEach { die6 ->
                                rolls.add(Roll6.from(die1, die2, die3, die4, die5, die6))
                            }
                        }
                    }
                }
            }
        }

        return rolls
    }
}