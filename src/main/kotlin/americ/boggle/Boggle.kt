package americ.boggle

import java.io.File
import kotlin.random.Random

fun main(args: Array<String>) {
    doIt()
}

/*
  This code simulates the Boggle game. One Boggle board is randomly generated, then solved to see what words can be
  built in that board.

  The dictionary used is the official Scrabble dictionary.
  Boggle board size is 4x4.

  Assumptions:
  - q's are always followed by u's. This applies both to the dictionary and words in the boggle board.
        Based on this assumption:
        - "qu" is replaced in every dictionary word with "u"
        - the "qu" boggle cube is replaced with "q"
*/

val sidesPerCube = 6
val minimumBoggleWordLength = 3
val boggleBoardWidth = 4
//val boggleBoardHeight = 4

class BoggleCube(
    val letters: List<Char>
)

data class BoggleCell(
    val letter: Char,
    val x: Int,
    val y: Int
)

class BoggleBoard(
    val cells: List<BoggleCell>
) {
    private val indexRange = cells.indices

    fun getNeighbors(cell: BoggleCell): List<BoggleCell> {
        return listOf(
            Pair(-1, -1),
            Pair(0, -1),
            Pair(1, -1),
            Pair(-1, 0),
            Pair(1, 0),
            Pair(-1, 1),
            Pair(0, 1),
            Pair(1, 1)
        ).mapNotNull { get(cell.x + it.first, cell.y + it.second) }
    }

    private fun get(x: Int, y: Int): BoggleCell? {
        val i = boggleBoardWidth * y + x
        return if (indexRange.contains(i)) {
            cells[i]
        } else {
            null
        }
    }
}

data class BoggleWord(
    val cells: List<BoggleCell>
) {
    override fun toString(): String {
        return String(cells.map { it.letter }.toCharArray())
    }
}

data class TrieNode(
    val children: MutableMap<Char, TrieNode> = mutableMapOf<Char, TrieNode>(),
    var lastLetter: Boolean = false
)

class Dictionary {
    val root = TrieNode()
    var nodeCount = 1

    enum class LookupResult { NotFound, CompleteWordFound, IncompleteWordFound }

    fun insert(word: String) {
        val finalWord = word.uppercase().replace("QU", "Q")
        var currentNode = root
        finalWord.forEachIndexed { i, letter ->
            var nextNode = currentNode.children[letter]
            if (nextNode == null) {
                nextNode = TrieNode()
                nodeCount++
                currentNode.children[letter] = nextNode
            }
            currentNode = nextNode
            if (i == finalWord.length - 1) {
                currentNode.lastLetter = true
            }
        }
    }

    fun lookup(word: List<Char>): LookupResult {
        return internalLookup(emptyList(), word, root)
    }

    private fun internalLookup(prefix: List<Char>, suffix: List<Char>, node: TrieNode): LookupResult {
        val letter = suffix.first()
        val childNode = node.children[letter]
        return if (childNode == null) {
            LookupResult.NotFound
        } else {
            if (suffix.size == 1) {
                if (childNode.lastLetter) {
                    LookupResult.CompleteWordFound
                } else {
                    LookupResult.IncompleteWordFound
                }
            } else {
                internalLookup(prefix + letter, suffix.drop(1), childNode)
            }
        }
    }
}

fun createCubes(): List<BoggleCube> {
    // hard code the construction of cubes that match the config of real boggle cubes
    return listOf(
        BoggleCube(listOf('A', 'E', 'E', 'N', 'G', 'A')),
        BoggleCube(listOf('E', 'E', 'W', 'H', 'G', 'N')),
        BoggleCube(listOf('T', 'U', 'O', 'C', 'M', 'I')),
        BoggleCube(listOf('M', 'I', 'H', 'N', 'U', 'Q')),
        BoggleCube(listOf('T', 'Y', 'I', 'D', 'S', 'T')),
        BoggleCube(listOf('E', 'V', 'D', 'R', 'Y', 'L')),
        BoggleCube(listOf('D', 'L', 'I', 'E', 'R', 'X')),
        BoggleCube(listOf('R', 'N', 'L', 'N', 'H', 'Z')),
        BoggleCube(listOf('T', 'H', 'W', 'V', 'E', 'R')),
        BoggleCube(listOf('Y', 'T', 'E', 'R', 'T', 'L')),
        BoggleCube(listOf('A', 'J', 'O', 'O', 'B', 'B')),
        BoggleCube(listOf('P', 'O', 'A', 'C', 'S', 'H')),
        BoggleCube(listOf('N', 'S', 'E', 'E', 'U', 'I')),
        BoggleCube(listOf('E', 'I', 'S', 'S', 'O', 'T')),
        BoggleCube(listOf('A', 'T', 'O', 'O', 'W', 'T')),
        BoggleCube(listOf('K', 'A', 'F', 'P', 'S', 'F'))
    )
}

fun generateBoard(cubes: List<BoggleCube>): BoggleBoard {
    val randomOrderedCubes = cubes.toMutableList()
    randomOrderedCubes.shuffle()
    return BoggleBoard(
        randomOrderedCubes.mapIndexed { i, cube ->
            val letterFacingUp = cube.letters[Random.nextInt(0, sidesPerCube)]
            BoggleCell(letterFacingUp, i % boggleBoardWidth, i / boggleBoardWidth)
        }
    )
}

fun loadDictionary(): Dictionary {
    val words: List<String> = File("dictionary.txt").readLines()
    println("  Dictionary file contains ${words.size} words")
    val dictionary = Dictionary()
    words.forEach { word ->
        if (word.length >= minimumBoggleWordLength) {
            dictionary.insert(word)
        }
    }
    println("  Dictionary has ${dictionary.nodeCount} nodes")
    return dictionary
}

fun displayBoard(board: BoggleBoard) {
    board.cells.forEach { cell ->
        if (cell.x == 0) {
            println()
            print("  ")
        }
        print(cell.letter + " ")
    }
    println()
    println()
}

fun doIt() {
    println("Creating cubes")
    val cubes = createCubes()
    println("Creating board")
    val board = generateBoard(cubes)
    displayBoard(board)
    println("Loading dictionary")
    val dictionary = loadDictionary()
    val wordsFound = findWords(board, dictionary)
    println("Found ${wordsFound.size} words")
    val wordsFormatted = wordsFound.joinToString()
    println("  $wordsFormatted")
}

fun findWords(board: BoggleBoard, dictionary: Dictionary): List<BoggleWord> {
    val wordsFoundSoFar = mutableSetOf<BoggleWord>()
    board.cells.forEach { cell ->
        explore(cell, emptyList(), wordsFoundSoFar, dictionary, board)
    }
    val wordList = wordsFoundSoFar.toMutableList()
    wordList.sortBy { it.toString() }
    return wordList.distinctBy { it.toString() }
}

fun explore(
    cell: BoggleCell,
    inputWord: List<BoggleCell>,
    wordsFoundSoFar: MutableSet<BoggleWord>,
    dictionary: Dictionary,
    board: BoggleBoard
) {
    val workingWord = inputWord + cell
    val result = dictionary.lookup(workingWord.map { it.letter })
    when (result) {
        Dictionary.LookupResult.NotFound -> return
        Dictionary.LookupResult.CompleteWordFound -> {
            wordsFoundSoFar.add(BoggleWord(workingWord))
            return
        }

        Dictionary.LookupResult.IncompleteWordFound -> {
            val neighbors = board.getNeighbors(cell)
            val goodNeighbors = neighbors.filter { !workingWord.contains(it) }
            goodNeighbors.forEach { childCell ->
                explore(childCell, workingWord, wordsFoundSoFar, dictionary, board)
            }
        }
    }
}



