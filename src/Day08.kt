import java.lang.Integer.min

fun List<List<Int>>.isVisible(x: Int, y: Int): Boolean {
    val maxXSize = this.first().size
    return when {
        x == 0 || x == maxXSize - 1 -> true
        y == 0 || y == size - 1 -> true
        else -> {
            val tree = this[x][y]
            val left = (0 until x).all { this[it][y] < tree }
            val right = (min(x + 1, maxXSize - 1) until maxXSize).all { this[it][y] < tree }
            val top = (0 until y).all { this[x][it] < tree }
            val bottom = (min(y + 1, size - 1) until size).all { this[x][it] < tree }
            top || right || bottom || left
        }
    }
}

fun List<List<Int>>.scenicScore(x: Int, y: Int): Int {
    val height = this[x][y]
    val maxYSize = this.first().size
    val top = (0 until x).reversed().indexOfFirst { this[it][y] >= height }.takeIf { it != -1 }?.plus(1) ?: x
    val bottom = (min(x + 1, size - 1) until size).indexOfFirst { this[it][y] >= height }.takeIf { it != -1 }?.plus(1) ?: (size - x - 1)
    val left = (0 until y).reversed().indexOfFirst { this[x][it] >= height }.takeIf { it != -1 }?.plus(1) ?: y
    val right = (min(y + 1, maxYSize - 1) until maxYSize).indexOfFirst { this[x][it] >= height }.takeIf { it != -1 }?.plus(1) ?: (maxYSize - y - 1)

    return top * right * bottom * left
}

fun main() {
    fun part1(input: List<String>): Int {
        val forest = input.map { row -> row.toList().map { it.toString().toInt() } }
        val visibleTrees = forest.mapIndexed { x, row ->
            List(row.size) { y -> if (forest.isVisible(x, y)) 1 else 0 }.sum()
        }
            .sum()
        return visibleTrees
    }

    fun part2(input: List<String>): Int {
        val forest = input.map { row -> row.toList().map { it.toString().toInt() } }
        val maxScenicScore = forest.mapIndexed { x, row ->
            List(row.size) { y -> forest.scenicScore(x, y) }.max()
        }.max()
        return maxScenicScore
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    println(part1(testInput))
    println(part2(testInput))
//    check(part1(testInput) == 7)
//    check(part2(testInput) == "MCD")

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}