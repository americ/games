package americ.domain

import org.junit.jupiter.api.assertThrows
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class Roll6Spec : Spek({
    describe("Roll6") {

        val one = DieRoll(1)
        val two = DieRoll(2)
        val three = DieRoll(3)
        val four = DieRoll(4)
        val five = DieRoll(5)
        val six = DieRoll(6)

        val threePairs1 = Roll6.from(one, one, two, two, three, three)
        val threePairs2 = Roll6.from(six, six, four, four, five, five)
        val threePairsOutOfOrder = Roll6.from(four, six, five, six, four, five)

        val twoTriples1 = Roll6.from(one, one, one, two, two, two)
        val twoTriples2 = Roll6.from(six, six, six, five, five, five)
        val twoTriplesOutOfOrder = Roll6.from(one, two, one, two, one, two)

        val fourOfAKind = Roll6.from(one, one, one, one, two, three)
        val fourOfAKindOutOfOrder = Roll6.from(one, two, three, one, one, one)

        val fiveOfAKind = Roll6.from(one, one, one, one, one, two)
        val fiveOfAKindOutOfOrder = Roll6.from(one, two, one, one, one, one)

        val sixOfAKind = Roll6.from(one, one, one, one, one, one)

        val straight = Roll6.from(one, two, three, four, five, six)
        val straightOutOfOrder = Roll6.from(six, one, three, two, four, five)

        describe("constructor") {
            it("allows array size 6") {
                Roll6(arrayOf(one, two, three, four, five, six))
            }
            it("rejects array size less than 6") {
                assertThrows<Exception> {
                    Roll6(arrayOf(one, two, three, four, five))
                }
            }
            it("rejects array size more than 6") {
                assertThrows<Exception> {
                    Roll6(arrayOf(one, two, three, four, five, six, one))
                }
            }
        }

        describe("from factory method") {
            it("allows array size 6") {
                straight
            }
            it("rejects array size less than 6") {
                assertThrows<Exception> {
                    Roll6.from(one, two, three, four, five)
                }
            }
            it("rejects array size more than 6") {
                assertThrows<Exception> {
                    Roll6.from(one, two, three, four, five, six, one)
                }
            }
        }

        describe("isTwoTriples()") {
            it("detects two triples") {
                assertTrue(twoTriples1.isTwoTriples())
                assertTrue(twoTriples2.isTwoTriples())
            }
            it("detects two triples out of order") {
                assertTrue(twoTriplesOutOfOrder.isTwoTriples())
            }

            it("rejects others") {
                assertFalse(Roll6.from(one, one, one, two, two, three).isTwoTriples())
                assertFalse(threePairs1.isTwoTriples())
                assertFalse(sixOfAKind.isTwoTriples())
                assertFalse(straight.isTwoTriples())
            }
        }

        describe("isThreePairs()") {
            it("detects three pairs") {
                assertTrue(threePairs1.isThreePairs())
                assertTrue(threePairs2.isThreePairs())
            }
            it("detects three pairs ouf of order") {
                assertTrue(threePairsOutOfOrder.isThreePairs())
            }
            it("rejects others") {
                assertFalse(twoTriples1.isThreePairs())
                assertFalse(sixOfAKind.isThreePairs())
                assertFalse(straight.isThreePairs())
            }
        }

        describe("isAStraight()") {
            it("detects a straight") {
                assertTrue(straight.isAStraight())
            }
            it("detects straight out of order") {
                assertTrue(straightOutOfOrder.isAStraight())
            }
            it("rejects others") {
                assertFalse(twoTriples1.isAStraight())
                assertFalse(threePairs1.isAStraight())
                assertFalse(sixOfAKind.isAStraight())
            }
        }

        describe("isFourTwo") {
            it("detects a four-two") {
                assertTrue(Roll6.from(one, one, two, two, two, two).isFourTwo())
            }
            it("detects four-two out of order") {
                assertTrue(Roll6.from(two, two, one, one, two, two).isFourTwo())
            }
            it("rejects others") {
                assertFalse(twoTriples1.isFourTwo())
                assertFalse(threePairs1.isFourTwo())
                assertFalse(sixOfAKind.isFourTwo())
            }
        }

        describe("isSixOfAKind") {
            it("detects six-of-a-kind") {
                assertTrue(sixOfAKind.isSixOfAKind())
            }
            it("rejects othes") {
                assertFalse(twoTriples1.isSixOfAKind())
                assertFalse(threePairs1.isSixOfAKind())
                assertFalse(fourOfAKind.isSixOfAKind())
                assertFalse(fiveOfAKind.isSixOfAKind())
            }
        }

        describe("isFiveOfAKind") {
            it("detects five of a kind") {
                assertTrue(fiveOfAKind.isFiveOfAKind())
            }
            it("detects five of a kind out of order") {
                assertTrue(fiveOfAKindOutOfOrder.isFiveOfAKind())
            }
            it("rejects othes") {
                assertFalse(twoTriples1.isFiveOfAKind())
                assertFalse(threePairs1.isFiveOfAKind())
                assertFalse(fourOfAKind.isFiveOfAKind())
                assertFalse(sixOfAKind.isFiveOfAKind())
            }
        }

        describe("isFourOfAKind") {
            it("detects five of a kind") {
                assertTrue(fourOfAKind.isFourOfAKind())
            }
            it("detects five of a kind out of order") {
                assertTrue(fourOfAKindOutOfOrder.isFourOfAKind())
            }
            it("rejects othes") {
                assertFalse(twoTriples1.isFourOfAKind())
                assertFalse(threePairs1.isFourOfAKind())
                assertFalse(fiveOfAKind.isFourOfAKind())
                assertFalse(sixOfAKind.isFourOfAKind())
            }
        }

        describe("generateAllSingleDieVariations()") {
            it("generates them all") {
                val variations = sixOfAKind.generateAllSingleDieVariations()
                assertEquals(6 * 5, variations.size)

                assertTrue(variations.contains(Roll6.from(two, one, one, one, one, one)))
                assertTrue(variations.contains(Roll6.from(one, one, two, one, one, one)))

                assertTrue(variations.contains(Roll6.from(six, one, one, one, one, one)))
                assertTrue(variations.contains(Roll6.from(one, one, six, one, one, one)))
            }
        }

        describe("dedup()") {
            it("dedups clones") {
                val roll1 = Roll6.from(one, one, one, one, one, one)
                val roll2 = Roll6.from(one, one, one, one, one, one)
                assertEquals(listOf(roll1), Roll6.dedup(listOf(roll1, roll2)))
            }
            it("dedups variations") {
                val roll1 = Roll6.from(two, one, one, one, one, one)
                val roll2 = Roll6.from(one, two, one, one, one, one)
                val roll3 = Roll6.from(one, one, two, one, one, one)
                val roll4 = Roll6.from(one, one, one, two, one, one)
                val roll5 = Roll6.from(one, one, one, one, two, one)
                val roll6 = Roll6.from(one, one, one, one, one, two)
                assertEquals(listOf(roll1), Roll6.dedup(listOf(roll1, roll2, roll3, roll4, roll5, roll6)))
            }
            it("does nothing to non-dups") {
                val roll1 = Roll6.from(two, one, one, one, one, one)
                val roll2 = Roll6.from(one, three, one, one, one, one)
                val roll3 = Roll6.from(one, one, four, one, one, one)
                assertEquals(listOf(roll1, roll2, roll3), Roll6.dedup(listOf(roll1, roll2, roll3)))
            }
        }
    }
})