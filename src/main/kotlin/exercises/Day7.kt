package exercises

class Day7: DailyExercise() {
    override val title: String = "No Space Left On Device"
    override val dayNumber: Int = 7
    private val input = getInput("src/main/resources/day7.txt")

    private val stack = arrayListOf<Directory>()
    private val root = Directory("/")
    private val sizes = arrayListOf<Int>()

    override fun runExercise() {
        input.forEach { terminalEntry ->
            when (terminalEntry.commandType()) {
                TerminalOutputType.PUSH_DIRECTORY -> {
                    stack.push(terminalEntry)
                }
                TerminalOutputType.POP_DIRECTORY -> {
                    sizes.add(stack.currentDirectory().size)
                    stack.removeFirst()
                }
                TerminalOutputType.NEW_DIR -> {
                    stack.currentDirectory().children.add(Directory(terminalEntry.directoryName()))
                }
                TerminalOutputType.FILE -> {
                    stack.currentDirectory().fileSizes.add(terminalEntry.fileSize())
                }
                else -> {} // No-op
            }
        }

        println("The sum of sizes of all directories with 100000 or fewer bytes is ${sizes.filter { it <= 100000 }.sum()}")
        val used = root.size
        val available = 70000000 - used
        val needed = 30000000 - available
        val sizeOfDirectoryToDelete = sizes.filter { it >= needed }.minOf { it }
        println("The directory we need to delete has a size of $sizeOfDirectoryToDelete")
    }

    private fun String.commandType() =
        when {
            this == "$ cd .." -> TerminalOutputType.POP_DIRECTORY
            this.startsWith("$ cd") -> TerminalOutputType.PUSH_DIRECTORY
            this.startsWith("$ ls") -> TerminalOutputType.LIST
            this.startsWith("dir") -> TerminalOutputType.NEW_DIR
            else -> TerminalOutputType.FILE
        }

    private fun String.destination() = this.substringAfter("$ cd ")

    private fun String.directoryName() = this.substringAfter("dir ")

    private fun String.fileSize() = this.substringBefore(" ").toInt()

    private fun ArrayList<Directory>.currentDirectory() = this.first()

    enum class TerminalOutputType {
        POP_DIRECTORY,
        PUSH_DIRECTORY,
        LIST,
        NEW_DIR,
        FILE
    }

    data class Directory(val name: String, val children: ArrayList<Directory> = arrayListOf()) {
        val fileSizes: MutableList<Int> = mutableListOf()

        val size: Int
            get() = fileSizes.sum() + children.sumOf { it.size }

        override fun toString(): String = name
    }

    private fun ArrayList<Directory>.push(terminalEntry: String) {
        val nodeToPush = if (terminalEntry.destination() == "/") {
            root
        } else {
            this.currentDirectory().children.find { it.name == terminalEntry.destination() } ?: throw RuntimeException("directory not found")
        }

        this.add(0, nodeToPush)
    }
}