package exercises

class Day5: DailyExercise() {
    override val title: String = "Supply Stacks"
    override val dayNumber: Int = 5
    private val input = getInputAsString("src/main/resources/day5.txt")

    override fun runExercise() {
        println("The crates on top of each stack are ${getStacksWithBasicCrane()}")
        println("The crates on top of the stack with the advanced crane are ${getStacksWithAdvancedCrane()}")
    }

    private fun getStacksWithBasicCrane(): String {
        val stacks = input.getInitialStacks()
        input.getCrateMovingCommands().forEach { command -> stacks.doCommand(command, true) }
        return stacks.getTopOfEachStack()
    }

    private fun getStacksWithAdvancedCrane(): String {
        val stacks = input.getInitialStacks()
        input.getCrateMovingCommands().forEach { command -> stacks.doCommand(command) }
        return stacks.getTopOfEachStack()
    }

    private fun String.getCrateMovingCommands() =
        this.substringAfter("\n\n").split("\n").map { commandString -> commandString.asCommand() }

    private fun String.getInitialStacks(): List<MutableList<Char>> {
        val stackInfo = this.substringBefore("\n\n").split("\n")
        val numberOfStacks = stackInfo.last().filter { c -> c != ' ' }.length

        val stacks = mutableListOf<MutableList<Char>>()
        repeat(numberOfStacks) {
            stacks.add(mutableListOf())
        }

        stackInfo.dropLast(1).forEach {
            it.getStackDetails().forEach { detail ->
                stacks[detail.key].add(detail.value)
            }
        }

        return stacks
    }

    private fun String.getStackDetails(): Map<Int, Char> {
        val infoMap = mutableMapOf<Int, Char>()
        for (i in 1..this.length step 4) {
            if (this[i] != ' ') {
                val stackNumber = i / 4
                infoMap[stackNumber] = this[i]
            }
        }
        return infoMap
    }

    private fun String.asCommand() =
        Command(this.substringBefore("from").getNumber(), this.substringAfter("from").substringBefore("to").getNumber() - 1, this.substringAfter("to").getNumber() - 1)

    private fun List<MutableList<Char>>.doCommand(command: Command, reverse: Boolean = false) {
        val toBeMoved = this[command.from].take(command.times)
        repeat(command.times) { this[command.from].removeFirst() }
        this[command.to].addAll(0, if (reverse) toBeMoved.reversed() else toBeMoved)
    }

    private fun String.getNumber() = this.filter { c -> c in '0'..'9' }.toInt()

    private fun List<MutableList<Char>>.getTopOfEachStack() = this.map { it.first() }.joinToString("")

    data class Command(val times: Int, val from: Int, val to: Int)
}