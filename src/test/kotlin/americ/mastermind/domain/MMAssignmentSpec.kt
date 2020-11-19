package americ.mastermind.domain

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MMAssignmentSpec : Spek({
    describe("MMAssignment") {
        describe(".createAll") {

            it("works with mixed markers") {
                val guess = MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.GREEN))
                val assessment = MMAssessment(1, 1, 3)

                val actual = MMAssignment.createAll(guess, assessment)

                val expected = listOf(
                    MMAssignment(guess, mutableListOf(MMMarker.BLACK, MMMarker.WHITE, MMMarker.EMPTY)),
                    MMAssignment(guess, mutableListOf(MMMarker.BLACK, MMMarker.EMPTY, MMMarker.WHITE)),
                    MMAssignment(guess, mutableListOf(MMMarker.WHITE, MMMarker.BLACK, MMMarker.EMPTY)),
                    MMAssignment(guess, mutableListOf(MMMarker.WHITE, MMMarker.EMPTY, MMMarker.BLACK)),
                    MMAssignment(guess, mutableListOf(MMMarker.EMPTY, MMMarker.BLACK, MMMarker.WHITE)),
                    MMAssignment(guess, mutableListOf(MMMarker.EMPTY, MMMarker.WHITE, MMMarker.BLACK))
                )

                assertEquals(expected.size, actual.size)
                expected.forEach { expectedAssignment ->
                    println("expected assignment: ${expectedAssignment}")
                    assertTrue(actual.contains(expectedAssignment))
                }
            }

            it("eliminates duplicate blacks") {
                val guess = MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW))
                val assessment = MMAssessment(2, 0, 2)

                val actual = MMAssignment.createAll(guess, assessment)

                val expected = listOf(
                    MMAssignment(guess, mutableListOf(MMMarker.BLACK, MMMarker.BLACK))
                )

                assertEquals(expected.size, actual.size)
            }

            it("eliminates duplicate whites") {
                val guess = MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW))
                val assessment = MMAssessment(2, 0, 2)

                val actual = MMAssignment.createAll(guess, assessment)

                val expected = listOf(
                    MMAssignment(guess, mutableListOf(MMMarker.BLACK, MMMarker.BLACK))
                )

                assertEquals(expected.size, actual.size)
            }

            it("eliminates duplicate empties") {
                val guess = MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW))
                val assessment = MMAssessment(2, 0, 2)

                val actual = MMAssignment.createAll(guess, assessment)

                val expected = listOf(
                    MMAssignment(guess, mutableListOf(MMMarker.BLACK, MMMarker.BLACK))
                )

                assertEquals(expected.size, actual.size)
            }
        }
        describe(".createAllSolutions()") {
            describe("for all black markers") {
                it("creates all solutions") {
                    val guess = MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.GREEN))
                    val assignment = MMAssignment(guess, mutableListOf(MMMarker.BLACK, MMMarker.BLACK, MMMarker.BLACK))

                    val solutions = assignment.createAllSolutions()

                    assertEquals(1, solutions.size)
                    assertEquals(guess, solutions.first())
                }
            }
            describe("for black markers + white marker") {
                it("creates all solutions") {
                    val guess = MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.GREEN))
                    val assignment = MMAssignment(guess, mutableListOf(MMMarker.BLACK, MMMarker.BLACK, MMMarker.WHITE))

                    val solutions = assignment.createAllSolutions()

                    assertEquals(5, solutions.size)
                    assertTrue(solutions.contains(MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.BLUE))))
                    assertTrue(solutions.contains(MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.YELLOW))))
                    assertTrue(solutions.contains(MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.BLACK))))
                    assertTrue(solutions.contains(MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.WHITE))))
                    assertTrue(solutions.contains(MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.RED))))
                }
            }
            describe("for black markers + empty marker") {
                it("creates all solutions") {
                    val guess = MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.GREEN))
                    val assignment = MMAssignment(guess, mutableListOf(MMMarker.BLACK, MMMarker.BLACK, MMMarker.EMPTY))

                    val solutions = assignment.createAllSolutions()

                    assertEquals(5, solutions.size)
                    assertTrue(solutions.contains(MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.BLUE))))
                    assertTrue(solutions.contains(MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.YELLOW))))
                    assertTrue(solutions.contains(MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.BLACK))))
                    assertTrue(solutions.contains(MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.WHITE))))
                    assertTrue(solutions.contains(MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.RED))))
                }
            }
            describe("for white + black + empty markers") {
                it("creates right number of solutions") {
                    val guess = MMSet(mutableListOf(MMPeg.RED, MMPeg.YELLOW, MMPeg.GREEN))
                    val assignment = MMAssignment(guess, mutableListOf(MMMarker.WHITE, MMMarker.BLACK, MMMarker.EMPTY))

                    val solutions = assignment.createAllSolutions()
                    assertEquals(25, solutions.size)
                }
            }
        }
    }
})