import exercises.Day1
import exercises.Day2
import exercises.Day3
import exercises.Day4
import exercises.Day5

fun main() {
    val exercises = listOf(Day1(), Day2(), Day3(), Day4(), Day5())

    exercises.forEach { exercise ->
        exercise.run()
    }
}