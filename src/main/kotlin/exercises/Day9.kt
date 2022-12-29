package exercises

import kotlin.math.absoluteValue

class Day9: DailyExercise() {
    override val title: String = "Rope Bridge"
    override val dayNumber: Int = 9
    private val input = getInput("src/main/resources/day9.txt")

    override fun runExercise() {
        val knotsPart1 = Array(2) { intArrayOf(0, 0) }
        val knotsPart2 = Array(10) { intArrayOf(0, 0) }

        val positionsVisitedPart1 = hashSetOf<Pair<Int, Int>>()
        val positionsVisitedPart2 = hashSetOf<Pair<Int, Int>>()

        input.forEach {
            positionsVisitedPart1.addAll(knotsPart1.move(it))
            positionsVisitedPart2.addAll(knotsPart2.move(it))
        }

        println("The number of unique positions visited in part 1 is ${positionsVisitedPart1.size}")
        println("The number of unique positions visited in part 2 is ${positionsVisitedPart2.size}")
    }

    private fun Array<IntArray>.move(motion: String): Set<Pair<Int, Int>> {
        val direction = motion.substringBefore(" ")
        val number = motion.substringAfter(" ").toInt()
        val tailPositionsVisited = hashSetOf<Pair<Int, Int>>()

        repeat(number) {
            this.moveRope(direction)
            tailPositionsVisited.add(Pair(this.last()[0], this.last()[1]))
        }

        return tailPositionsVisited
    }

    private fun Array<IntArray>.moveRope(direction: String) {
        // Move the head
        when (direction) {
            "R" -> this.first()[1]++
            "L" -> this.first()[1]--
            "U" -> this.first()[0]++
            "D" -> this.first()[0]--
        }

        // Move the followers
        for (i in 1 until this.size) {
            this[i].moveToward(this[i - 1])
        }
    }

    /**
     * Moves a knot toward the knot that it is following.
     */
    private fun IntArray.moveToward(leader: IntArray) {
        val xDiff = leader[0] - this[0]
        val yDiff = leader[1] - this[1]

        // If head and tail are still touching, just return without moving the tail
        if (xDiff.absoluteValue <= 1 && yDiff.absoluteValue <= 1) {
            return
        }

        // Otherwise, move the knot in the direction of the leading knot
        when {
            xDiff == 0 -> this[1] += yDiff.delta()
            yDiff == 0 -> this[0] += xDiff.delta()
            else -> this[0] += xDiff.delta().also { this[1] += yDiff.delta() }
        }
    }

    private fun Int.delta() = this.coerceAtLeast(-1).coerceAtMost(1)
}