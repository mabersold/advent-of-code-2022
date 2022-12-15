package exercises

import java.util.*

class Day1 : DailyExercise() {
    override val title = "Calorie Counting"
    override val dayNumber = 1

    private val input = getInputAsString("src/main/resources/day1.txt")
    private val priorityQueue = PriorityQueue<Int>(Collections.reverseOrder())

    override fun runExercise() {
        println("The most calories from a single elf is ${part1()}")
        println("The most calories from the top three elves is ${part2()}")
    }

    private fun part1(): Int {
        prepareData()
        return priorityQueue.peek()
    }

    private fun part2(): Int {
        prepareData()
        return priorityQueue.remove() + priorityQueue.remove() + priorityQueue.remove()
    }

    private fun prepareData() {
        val elvesWithSnacks = input.split("\n\n")

        elvesWithSnacks.forEach { elf ->
            val snacks = elf.split("\n")
            val calorieSum = snacks.sumOf { it.toInt() }
            priorityQueue.add(calorieSum)
        }
    }
}