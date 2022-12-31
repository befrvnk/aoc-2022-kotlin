data class Movement(
    val count: Int,
    val from: Int,
    val to: Int,
)

fun main() {
    fun part1(input: List<String>): String {
        val separatorLineIndex = input.indexOfFirst { it.isEmpty() }
        val stackLines = input.subList(0, separatorLineIndex - 1)
        val stackNames = input[separatorLineIndex - 1]
        val stacks = (1..stackNames.length step 4).map { index ->
            stackLines.reversed()
                .mapNotNull { line ->
                    line.getOrNull(index)?.takeUnless { it.isWhitespace() }?.toString()
                }
                .toMutableList()
        }

        val regex = Regex("move (\\d+) from (\\d+) to (\\d+)")
        val movementLines = input.subList(separatorLineIndex + 1, input.size)
        val movements = movementLines.mapNotNull {
            regex.find(it)?.destructured?.let { (count, from, to) ->
                Movement(count.toInt(), from.toInt(), to.toInt())
            }
        }

        movements.forEach { movement ->
            repeat(movement.count) {
                val removed = stacks[movement.from - 1].removeLast()
                stacks[movement.to - 1].add(removed)
            }
        }

        return stacks.joinToString(separator = "") { it.last() }
    }

    fun part2(input: List<String>): String {
        val separatorLineIndex = input.indexOfFirst { it.isEmpty() }
        val stackLines = input.subList(0, separatorLineIndex - 1)
        val stackNames = input[separatorLineIndex - 1]
        val stacks = (1..stackNames.length step 4).map { index ->
            stackLines.reversed()
                .mapNotNull { line ->
                    line.getOrNull(index)?.takeUnless { it.isWhitespace() }?.toString()
                }
                .toMutableList()
        }

        val regex = Regex("move (\\d+) from (\\d+) to (\\d+)")
        val movementLines = input.subList(separatorLineIndex + 1, input.size)
        val movements = movementLines.mapNotNull {
            regex.find(it)?.destructured?.let { (count, from, to) ->
                Movement(count.toInt(), from.toInt(), to.toInt())
            }
        }

        movements.forEach { movement ->
            val removed = (1..movement.count).map { stacks[movement.from - 1].removeLast() }.reversed()
            stacks[movement.to - 1].addAll(removed)
        }

        return stacks.joinToString(separator = "") { it.last() }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == "CMZ")
    check(part2(testInput) == "MCD")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}