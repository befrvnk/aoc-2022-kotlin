fun String.isMarker(): Boolean {
    return all { char -> this.count(char) == 1 }
}

fun String.count(char: Char): Int {
    return count { it == char }
}

fun main() {
    fun part1(input: List<String>): Int {
        val firstLine = input.first()
        (0..firstLine.length - 4).forEach { index ->
            val potentialMarker = firstLine.substring(index, index + 4)
            if (potentialMarker.isMarker()) return index + 4
        }
        throw Exception("Marker was not found")
    }

    fun part2(input: List<String>): Int {
        val firstLine = input.first()
        (0..firstLine.length - 14).forEach { index ->
            val potentialMarker = firstLine.substring(index, index + 14)
            if (potentialMarker.isMarker()) return index + 14
        }
        throw Exception("Marker was not found")
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 7)
//    check(part2(testInput) == "MCD")

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}