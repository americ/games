package americ.mastermind.players

import americ.mastermind.domain.MMPeg
import americ.mastermind.domain.MMSet
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

class CodeMakerSpec : Spek({
    describe("CodeMaker") {

        describe(".assess()") {
            describe("for size == 1") {
                val codeMaker = CodeMaker(1)

                it("assesses a total match correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED)),
                        MMSet(listOf(MMPeg.RED))
                    )
                    assertEquals(codeMaker.size, assessment.blackCount)
                    assertEquals(0, assessment.whiteCount)
                }
                it("assesses a total mismatch correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED)),
                        MMSet(listOf(MMPeg.GREEN))
                    )
                    assertEquals(0, assessment.blackCount)
                    assertEquals(0, assessment.whiteCount)
                }
            }
            describe("for size == 2") {
                val codeMaker = CodeMaker(2)
                it("assesses a total match correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE)),
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE))
                    )
                    assertEquals(codeMaker.size, assessment.blackCount)
                    assertEquals(0, assessment.whiteCount)
                }
                it("assesses a total mismatch correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE)),
                        MMSet(listOf(MMPeg.GREEN, MMPeg.YELLOW))
                    )
                    assertEquals(0, assessment.blackCount)
                    assertEquals(0, assessment.whiteCount)
                }
                it("assesses a single color match correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE)),
                        MMSet(listOf(MMPeg.GREEN, MMPeg.RED))
                    )
                    assertEquals(0, assessment.blackCount)
                    assertEquals(1, assessment.whiteCount)
                }
                it("assesses a dual color match correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE)),
                        MMSet(listOf(MMPeg.BLUE, MMPeg.RED))
                    )
                    assertEquals(0, assessment.blackCount)
                    assertEquals(2, assessment.whiteCount)
                }
                it("assesses a single match correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE)),
                        MMSet(listOf(MMPeg.RED, MMPeg.GREEN))
                    )
                    assertEquals(1, assessment.blackCount)
                    assertEquals(0, assessment.whiteCount)
                }
            }
            describe("for size == 3") {
                val codeMaker = CodeMaker(3)

                it("assesses a total match correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE, MMPeg.GREEN)),
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE, MMPeg.GREEN))
                    )
                    assertEquals(codeMaker.size, assessment.blackCount)
                    assertEquals(0, assessment.whiteCount)
                }
                it("assesses a total mismatch correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE, MMPeg.GREEN)),
                        MMSet(listOf(MMPeg.BLACK, MMPeg.WHITE, MMPeg.YELLOW))
                    )
                    assertEquals(0, assessment.blackCount)
                    assertEquals(0, assessment.whiteCount)
                }
                it("assesses a single color match correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE, MMPeg.GREEN)),
                        MMSet(listOf(MMPeg.BLUE, MMPeg.WHITE, MMPeg.YELLOW))
                    )
                    assertEquals(0, assessment.blackCount)
                    assertEquals(1, assessment.whiteCount)
                }
                it("assesses a dual color match correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE, MMPeg.GREEN)),
                        MMSet(listOf(MMPeg.BLUE, MMPeg.WHITE, MMPeg.RED))
                    )
                    assertEquals(0, assessment.blackCount)
                    assertEquals(2, assessment.whiteCount)
                }
                it("assesses a triple color match correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE, MMPeg.GREEN)),
                        MMSet(listOf(MMPeg.BLUE, MMPeg.GREEN, MMPeg.RED))
                    )
                    assertEquals(0, assessment.blackCount)
                    assertEquals(3, assessment.whiteCount)
                }
                it("assesses a full mix correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE, MMPeg.GREEN)),
                        MMSet(listOf(MMPeg.RED, MMPeg.GREEN, MMPeg.BLACK))
                    )
                    assertEquals(1, assessment.blackCount)
                    assertEquals(1, assessment.whiteCount)
                }
                it("assesses dup solution pegs correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.RED, MMPeg.GREEN)),
                        MMSet(listOf(MMPeg.RED, MMPeg.BLACK, MMPeg.RED))
                    )
                    assertEquals(1, assessment.blackCount)
                    assertEquals(1, assessment.whiteCount)
                }
                it("assesses dup solution pegs correctly 2") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.RED, MMPeg.GREEN)),
                        MMSet(listOf(MMPeg.YELLOW, MMPeg.BLACK, MMPeg.RED))
                    )
                    assertEquals(0, assessment.blackCount)
                    assertEquals(1, assessment.whiteCount)
                }
                it("assesses dup guess pegs correctly") {
                    val assessment = codeMaker.assess(
                        MMSet(listOf(MMPeg.RED, MMPeg.BLUE, MMPeg.GREEN)),
                        MMSet(listOf(MMPeg.YELLOW, MMPeg.RED, MMPeg.RED))
                    )
                    assertEquals(0, assessment.blackCount)
                    assertEquals(1, assessment.whiteCount)
                }
            }
        }
    }
})