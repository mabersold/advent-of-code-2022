package exercises

class Day4: DailyExercise() {
    override val title: String = "Camp Cleanup"
    override val dayNumber: Int = 4
    private val input = getInput("src/main/resources/day4.txt")

    override fun runExercise() {
        println("The number that fully overlap is ${input.fullyOverlappingCount()}")
        println("The number that have any overlap is ${input.partlyOverlappingCount()}")
    }

    private fun List<String>.fullyOverlappingCount() =
        this.map { it.asPairOfRanges() }.count { it.fullyContains() }

    private fun List<String>.partlyOverlappingCount() =
        this.map { it.asPairOfRanges() }.count { it.partlyContains() }

    private fun String.asPairOfRanges() =
        Pair(this.substringBefore(",").asRange(), this.substringAfter(",").asRange())

    private fun String.asRange() =
        this.substringBefore("-").toInt()..this.substringAfter("-").toInt()

    private fun Pair<IntRange, IntRange>.fullyContains() =
        this.first.first <= this.second.first && this.first.last >= this.second.last
                || this.first.first >= this.second.first && this.first.last <= this.second.last

    private fun Pair<IntRange, IntRange>.partlyContains() =
        (this.second.contains(this.first.first) || this.second.contains(this.first.last)) ||
                this.first.contains(this.second.first) || this.first.contains(this.second.last)
}
