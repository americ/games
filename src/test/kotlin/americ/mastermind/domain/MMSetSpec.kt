package americ.mastermind.domain

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertNotSame

class MMSetSpec : Spek({
    describe("MMSet") {
        describe(".random()") {
            it("returns a correctly sized set") {
                val set = MMSet.random(4)
                assertEquals(4, set.pegs.size)
            }
        }
        describe(".createAllSets()") {
            describe("for size 1") {
                it("creates all sets") {
                    val sets = MMSet.createAllSets(1)
                    assertEquals(1, sets.first().pegs.size)
                    assertEquals(6, sets.size)
                    val setOfSets = sets.toSet()
                    assertEquals(6, setOfSets.size)
                }
            }
            describe("for size 4") {
                it("creates all sets") {
                    val sets = MMSet.createAllSets(4)
                    assertEquals(4, sets.first().pegs.size)
                    assertEquals(1296, sets.size)
                    val setOfSets = sets.toSet()
                    assertEquals(1296, setOfSets.size)
                }
            }
        }
        describe(".getByIndices()") {
            it ("returns the right pegs") {
                val set = MMSet(mutableListOf(MMPeg.GREEN, MMPeg.YELLOW, MMPeg.BLUE))
                val resultPegs = set.getByIndices(listOf(1, 2, 0, 1))

                val expected = listOf(MMPeg.YELLOW, MMPeg.BLUE, MMPeg.GREEN, MMPeg.YELLOW)
                assertEquals(expected, resultPegs)
            }
        }
        describe(".deepCopy") {
            it("deep copies") {
                val set = MMSet(mutableListOf(MMPeg.GREEN, MMPeg.YELLOW, MMPeg.BLUE))
                val copy = set.deepCopy()
                assertEquals(set, copy)
                assertNotSame(set, copy)
                assertNotSame(set.pegs, copy.pegs)
            }
        }
    }
})