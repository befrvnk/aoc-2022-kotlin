enum class Hand(val points: Int) {
    Rock(1), Paper(2), Scissor(3)
}

fun handFromValue(value: String): Hand {
    return when (value) {
        "A", "X" -> Hand.Rock
        "B", "Y" -> Hand.Paper
        "C", "Z" -> Hand.Scissor
        else -> throw Exception("Unsupported value $value")
    }
}

fun resultFromValue(value: String): Result {
    return when (value) {
        "X" -> Result.LOSS
        "Y" -> Result.DRAW
        "Z" -> Result.WIN
        else -> throw Exception("Unsupported value $value")
    }
}

fun Hand.forResult(result: Result): Hand {
    return when (result) {
        Result.DRAW -> this
        Result.LOSS -> when (this) {
            Hand.Rock -> Hand.Scissor
            Hand.Paper -> Hand.Rock
            Hand.Scissor -> Hand.Paper
        }
        Result.WIN -> when (this) {
            Hand.Rock -> Hand.Paper
            Hand.Paper -> Hand.Scissor
            Hand.Scissor -> Hand.Rock
        }
    }
}

data class Round(
    val opponent: Hand,
    val own: Hand,
) {
    private fun result(): Result {
        return when {
            opponent == Hand.Rock && own == Hand.Scissor -> Result.LOSS
            opponent == Hand.Paper && own == Hand.Rock -> Result.LOSS
            opponent == Hand.Scissor && own == Hand.Paper -> Result.LOSS
            opponent == own -> Result.DRAW
            else -> Result.WIN
        }
    }
    fun points(): Int {
        return own.points + result().points
    }
}

enum class Result(val points: Int) {
    LOSS(0), DRAW(3), WIN(6)
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line ->
            Round(
                opponent = handFromValue(line[0].toString()),
                own = handFromValue(line[2].toString()),
            )
        }.sumOf { it.points() }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val opponentHand = handFromValue(line[0].toString())
            val result = resultFromValue(line[2].toString())
            val ownHand = opponentHand.forResult(result)
            Round(
                opponent = opponentHand,
                own = ownHand,
            )
        }.sumOf { it.points() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}