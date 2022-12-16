import exercises.*

fun main() {
    val exercises = listOf(Day1(), Day2(), Day3(), Day4(), Day5(), Day6())

    exercises.forEach { exercise ->
        exercise.run()
    }
}