sealed class Item {

    abstract val name: String
    abstract val size: Int

    data class File(
        override val name: String,
        override val size: Int,
    ) : Item()

    data class Directory(
        override val name: String,
        val items: MutableList<Item> = mutableListOf(),
        val parent: Directory? = null,
    ) : Item() {
        override val size: Int
            get() = items.sumOf { it.size }
    }
}

class Filesystem {
    val root = Item.Directory("/")
    private var current = root

    fun cd(name: String) {
        val nextDirectory = current.items.filterIsInstance<Item.Directory>().find { it.name == name }
        current = when (name) {
            ".." -> current.parent!!
            "/" -> root
            else -> {
                if (nextDirectory == null) {
                    val newDirectory = Item.Directory(name = name, parent = current)
                    current.items.add(newDirectory)
                    newDirectory
                } else {
                    nextDirectory
                }
            }
        }
    }

    fun dir(name: String) {
        val directory = current.items.filterIsInstance<Item.Directory>().find { it.name == name }
        if (directory == null) {
            val newDirectory = Item.Directory(name = name, parent = current)
            current.items.add(newDirectory)
        }
    }

    fun file(name: String, size: Int) {
        val file = current.items.filterIsInstance<Item.File>().find { it.name == name }
        if (file == null) {
            val newDirectory = Item.File(name, size)
            current.items.add(newDirectory)
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {

        val cd = Regex("\\$ cd (.*)")
        val dir = Regex("dir (.*)")
        val file = Regex("(\\d+) (.*)")

        val filesystem = Filesystem()

        input.forEach { line ->
            cd.find(line)?.let { filesystem.cd(it.groupValues[1]) }
            dir.find(line)?.let { filesystem.dir(it.groupValues[1]) }
            file.find(line)?.let { filesystem.file(it.groupValues[2], it.groupValues[1].toInt()) }
        }

        fun Item.Directory.directorySizes(): List<Int> {
            return listOf(size) + items.filterIsInstance<Item.Directory>()
                .flatMap { it.directorySizes() }
        }

        return filesystem.root.directorySizes().filter { it <= 100_000 }.sum()
    }

    fun part2(input: List<String>): Int {
        val cd = Regex("\\$ cd (.*)")
        val dir = Regex("dir (.*)")
        val file = Regex("(\\d+) (.*)")

        val filesystem = Filesystem()

        input.forEach { line ->
            cd.find(line)?.let { filesystem.cd(it.groupValues[1]) }
            dir.find(line)?.let { filesystem.dir(it.groupValues[1]) }
            file.find(line)?.let { filesystem.file(it.groupValues[2], it.groupValues[1].toInt()) }
        }

        fun Item.Directory.directorySizes(): List<Int> {
            return listOf(size) + items.filterIsInstance<Item.Directory>()
                .flatMap { it.directorySizes() }
        }

        val missingSpace = 30_000_000 - (70_000_000 - filesystem.root.size)
        return filesystem.root.directorySizes().filter { it >= missingSpace }.minOf { it }
    }

    // test if implementation meets criteria from the description, like:
//    check(part1(testInput) == 7)
//    check(part2(testInput) == "MCD")

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}