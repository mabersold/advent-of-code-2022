package exercises

import kotlin.math.absoluteValue

class Day10: DailyExercise() {
    override val title: String = "Cathode-Ray Tube"
    override val dayNumber: Int = 10
    private val input = getInput("src/main/resources/day10.txt")

    override fun runExercise() {
        val registerValues = mutableListOf(1)

        input.forEach { instruction ->
            val priorStrength = registerValues.last()

            registerValues.add(priorStrength)
            if (instruction.startsWith("addx")) {
                val registerModifier = instruction.substringAfter("addx ").toInt()
                registerValues.add(priorStrength + registerModifier)
            }
        }

        val sumOfSignalStrengths = intArrayOf(20, 60, 100, 140, 180, 220).sumOf { cycle -> registerValues.getSignalStrengthAt(cycle) }

        println("The sum of signal strengths is $sumOfSignalStrengths")

        registerValues.mapIndexed { pixel, value ->
            (value - (pixel % 40)).absoluteValue <= 1
        }.windowed(40, 40, false).forEach { row ->
            row.forEach { pixel ->
                print(if(pixel) '#' else ' ')
            }
            println()
        }
    }

    private fun MutableList<Int>.getSignalStrengthAt(cycleNumber: Int): Int {
        return this[cycleNumber - 1] * cycleNumber
    }
}