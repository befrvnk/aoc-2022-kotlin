fun List<String>.elves(): List<Int> {
    val elf = mutableListOf<Int>()
    val elves = mutableListOf<List<Int>>()
    forEach { line ->
        if (line == "") {
            elves.add(elf.toList())
            elf.clear()
        } else {
            elf.add(line.toInt())
        }
    }
    elves.add(elf.toList())
    return elves.map { it.sum() }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.elves().max()
    }

    fun part2(input: List<String>): Int {
        return input.elves().sortedDescending().take(3).sum()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)
    check(part2(testInput) == 45000)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
