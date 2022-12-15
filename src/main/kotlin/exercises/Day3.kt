package exercises

class Day3 : DailyExercise() {
    override val title = "Rucksack Reorganization"
    override val dayNumber = 3

    private val input = getInput("src/main/resources/day3.txt")

    override fun runExercise() {
        println("The sum of priorities is ${part1()}")
        println("The sum of badge priorities is ${part2()}")
    }

    private fun part1(): Int =
        input.sumOf {
            listOf(it.substring(0..it.length / 2), it.substring(it.length / 2))
                .getPriorityOfCommonItem()
        }

    private fun part2(): Int = input.chunked(3)
        .sumOf { it.getPriorityOfCommonItem() }

    private fun Char.priority(): Int =
        when (this) {
            in 'a'..'z' -> (this - 'a') + 1
            else -> (this - 'A') + 27
        }

    private fun List<String>.getPriorityOfCommonItem(): Int =
        this.map { it.toSet() }
            .reduce{ left, right -> left intersect right}
            .first()
            .priority()
}







