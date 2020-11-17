package americ.mastermind

import americ.mastermind.players.CodeBreaker
import americ.mastermind.players.CodeBreakerRandomGuess
import americ.mastermind.players.CodeBreakerGuessTracking
import americ.mastermind.players.CodeMaker

object MasterMind {
    @JvmStatic
    fun main(args: Array<String>) {
        val size = 4

//        runSingleGame(size)

        // averages 1200-1400 guesses/game
//        runEverySolution(size, { size -> CodeBreakerRandomGuess(size) })

        // averages 620-660 guesses/game
        runEverySolution(size, { size -> CodeBreakerGuessTracking(size) })
    }

    private fun runEverySolution(size: Int, codeBreakerFactory: (Int) -> CodeBreaker) {
        val codeMaker = CodeMaker(size)
        val solutions = codeMaker.createAllSolutions()

        val guessCounts = mutableListOf<Int>()
        var solutionCount = 0
        solutions.forEach { solution ->
            println("Solution #${solutionCount + 1}")
            val codeBreaker = codeBreakerFactory(size)

            var guessCount = 0
            do {
                val guess = codeBreaker.getGuess()
                val assessment = codeMaker.assess(solution, guess)
                guessCount++
            } while (assessment.blackCount != size)

            println("Game over in $guessCount guesses")
            guessCounts.add(guessCount)
            solutionCount++
        }

        val average = guessCounts.sum() / solutionCount
        val min = guessCounts.min()
        val max = guessCounts.max()
        println("Average guesses: ${average}")
        println("Min guesses: ${min}")
        println("Max guesses: ${max}")
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