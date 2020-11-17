package americ.mastermind

import americ.mastermind.domain.MMSet
import americ.mastermind.players.*
import org.apache.commons.lang3.time.StopWatch

object MasterMind {
    @JvmStatic
    fun main(args: Array<String>) {
        val size = 4

//        runSingleGame(size)

        // averages 1200-1400 guesses/game in 1 second
//        runEverySolution(size, { size -> CodeBreakerRandomGuess(size) })

        // averages 620-660 guesses/game in 1 seconds
//        runEverySolution(size, { size -> CodeBreakerGuessTracking(size) })

        // averages 620-660 guesses/game in 1 seconds
//        runEverySolution(size, { size -> CodeBreakerDualTracking(size) })

        // averages 620-660 guesses/game in 1 seconds
        runEverySolution(size, { size -> CodeBreakerAssessmentTracking(size) })
    }

    private fun runEverySolution(size: Int, codeBreakerFactory: (Int) -> CodeBreaker) {
        val watch = StopWatch()
        watch.start()
        val codeMaker = CodeMaker(size)
        val solutions = MMSet.createAllSets(size)

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