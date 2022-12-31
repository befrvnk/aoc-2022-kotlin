import kotlin.math.max
import kotlin.math.min

fun List<String>.parse(): List<List<IntRange>> {
    return map { section ->
        section.split(",").map { elf ->
            elf.split("-").map { it.toInt() }.let { (first, second) -> first..second }
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.parse()
            .count { section ->
                val maxRange =
                    min(section.first().first, section.last().first)..max(section.first().last, section.last().last)
                maxRange == section.first() || maxRange == section.last()
            }
    }

    fun part2(input: List<String>): Int {
        return input.parse()
            .count { section ->
                section.first().intersect(section.last()).isNotEmpty()
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}