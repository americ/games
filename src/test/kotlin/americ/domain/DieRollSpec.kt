package americ.domain

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DieRollSpec : Spek({
    describe("DieRoll") {
        describe("getOtherRolls") {
            it("returns other rolls") {
                val otherRolls = DieRoll(3).getOtherRolls()
                assertTrue(otherRolls.contains(DieRoll(1)))
                assertTrue(otherRolls.contains(DieRoll(2)))
                assertFalse(otherRolls.contains(DieRoll(3)))
                assertTrue(otherRolls.contains(DieRoll(4)))
                assertTrue(otherRolls.contains(DieRoll(5)))
                assertTrue(otherRolls.contains(DieRoll(6)))
            }
        }
    }
})