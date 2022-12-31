fun Char.priority() = if (this.code <= 'Z'.code) this.code - 'A'.code + 27 else this.code - 'a'.code + 1

fun main() {
    fun part1(input: List<String>): Int {
        var totalPriority = 0
        input.forEach { rucksack ->
            val leftCompartment = rucksack.substring(0 until rucksack.length / 2)
            val rightCompartment = rucksack.substring((rucksack.length / 2) until rucksack.length)
            check(leftCompartment + rightCompartment == rucksack)

            val doubleItems = mutableListOf<Char>()
            leftCompartment.toCharArray().forEach { item ->
                if (rightCompartment.contains(item)) {
                    doubleItems += item
                }
            }

            doubleItems.distinct().forEach { item ->
                totalPriority += item.priority()
            }
        }
        return totalPriority
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).map { (elf1, elf2, elf3) ->
            elf1.first { it in elf2 && it in elf3 }
        }.sumOf { it.priority() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 157)
    check(part2(testInput) == 70)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}