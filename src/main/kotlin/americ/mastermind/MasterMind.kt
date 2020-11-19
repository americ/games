package americ.mastermind

import americ.mastermind.domain.MMSet
import americ.mastermind.players.*
import org.apache.commons.lang3.time.StopWatch

object MasterMind {
    @JvmStatic
    fun main(args: Array<String>) {
        val size = 4

//        runSingleGame(size)

        // averages 1200-1400 guesses/game, max=7000-12000 in 1 second
//        runEverySolution(size, { size -> CodeBreakerRandomGuess(size) })

        // averages 620-660 guesses/game, max=1294-1296 in 1 seconds
//        runEverySolution(size, { size -> CodeBreakerGuessTracking(size) })

        // averages 620-660 guesses/game, max=1295-1296 in 1 seconds
//        runEverySolution(size, { size -> CodeBreakerDualTracking(size) })

        // averages 620-660 guesses/game, max=1296 in 1 seconds
//        runEverySolution(size, { size -> CodeBreakerAssessmentTracking(size) })

        // averages exactly 233 guesses/game, max=909 in 1 seconds
//        runEverySolution(size, { size -> CodeBreakerBlankRule(size) })

        // averages exactly 5 guesses/game, max=9 in 1 seconds
//        runEverySolution(size, { size -> CodeBreakerFullAssessment(size) })

        // WITH possibleSolutions AS A LIST:
        // - averages 7 guesses/game, max=12 in 165 seconds
        // AFTER possibleSolutions toSet():
        // - averages 7 guesses/game, max=12 in 13 seconds
        // AFTER also eliminating duplicate assignments with a set
        // - averages 7 guesses/game, max=12 in 3 seconds
        runEverySolution(size, { size -> CodeBreakerAssessmentAssignment(size) })


        /*
        Future breakers:
        2) 4 marker rule - when assessment produces 1-4 white markers and no black markers,
                remove solutions from the solution space that are not permutations
                of the guess pegs.
        3) similarity
            3a) based on assessment, calculate a 0-10 similarity rating to indicate how
                similar the solution must be to that guess. Calculate a similarity rating for
                the guess and all solutions in the solution space. Use this rating to order the
                solutions from best to worst. Use the best on the next guess.
            3b) like 3b, but on successive assessments calculate the similarity rating for all
                solutions against all existing guess-assessments, and take the average, then
                rank them by that.
         4) marker-peg assignments - use the assessment to make all possible assignments of
                markers to pegs. For each assignment generate all possible solutions. Remove
                from the solution space any solution that's not in the generated possible
                solutions.
          5) like #4, marker-peg assignments (CodeBreakerAssessmentAssignment), but treat
                the assignment holistically. Any peg assigned to EMPTY should limit the
                values of all other pegs to avoid that peg color. Likewise, any peg assigned
                to white should require that the peg color appear in some other solution
                peg.

        Other Changes:
        - remove unused fields on all breakers
         */
    }

    private fun runEverySolution(size: Int, codeBreakerFactory: (Int) -> CodeBreaker) {
        val watch = StopWatch()
        watch.start()
        val codeMaker = CodeMaker(size)
        var solutions = MMSet.createAllSets(size)
        solutions = solutions.shuffled()

        val guessCounts = mutableListOf<Int>()
        var solutionCount = 0
        solutions.forEach { solution ->
            println("Solution #${solutionCount + 1}")
            val codeBreaker = codeBreakerFactory(size)

            var guessCount = 0
            do {
                val guess = codeBreaker.getGuess()
                val assessment = codeMaker.assess(solution, guess)
                codeBreaker.reportAssessment(assessment)
                guessCount++
            } while (assessment.blackCount != size)

            println("Game over in $guessCount guesses")
            guessCounts.add(guessCount)
            solutionCount++
        }

        watch.stop()

        val average = guessCounts.sum() / solutionCount
        val min = guessCounts.min()
        val max = guessCounts.max()
        println("Average guesses: ${average}")
        println("Min guesses: ${min}")
        println("Max guesses: ${max}")

        println("Elapsed seconds: ${watch.time / 1000}")
    }

    private fun runSingleGame(size: Int) {
        val codeMaker = CodeMaker(size)
        val solution = codeMaker.createSolution()

        val codeBreaker = CodeBreakerRandomGuess(size)

        var guesses = 0
        do {
            println("Guess #${guesses + 1}")
            val guess = codeBreaker.getGuess()
            val assessment = codeMaker.assess(solution, guess)
            guesses++
        } while (assessment.blackCount != size)

        println("Game over in $guesses guesses")
    }
}