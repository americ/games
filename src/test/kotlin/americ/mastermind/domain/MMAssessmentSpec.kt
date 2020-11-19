package americ.mastermind.domain

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

class MMAssessmentSpec : Spek({
    describe("MMAssessment") {
        describe(".getMarkers") {
            it("handles all black") {
                assertEquals(
                    listOf(MMMarker.BLACK, MMMarker.BLACK, MMMarker.BLACK, MMMarker.BLACK),
                    MMAssessment(4, 0, 4).getMarkers()
                )
            }
            it("handles all white") {
                assertEquals(
                    listOf(MMMarker.WHITE, MMMarker.WHITE, MMMarker.WHITE, MMMarker.WHITE),
                    MMAssessment(0, 4, 4).getMarkers()
                )
            }
            it("handles all empty") {
                assertEquals(
                    listOf(MMMarker.EMPTY, MMMarker.EMPTY, MMMarker.EMPTY, MMMarker.EMPTY),
                    MMAssessment(0, 0, 4).getMarkers()
                )
            }
            it("handles mix") {
                assertEquals(
                    listOf(MMMarker.BLACK, MMMarker.WHITE, MMMarker.EMPTY, MMMarker.EMPTY),
                    MMAssessment(1, 1, 4).getMarkers()
                )
            }
        }
    }
})