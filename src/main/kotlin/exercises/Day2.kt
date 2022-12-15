package exercises

class Day2: DailyExercise() {
    enum class Move {
        ROCK,
        PAPER,
        SCISSORS
    }

    override val title = "Rock Paper Scissors"
    override val dayNumber = 2
    private val input = getInput("src/main/resources/day2.txt")

    override fun runExercise() {
        println("The total for part 1 is ${part1()}")
        println("The total for part 2 is ${part2()}")
    }

    private fun part1() =
        input.sumOf {
            it.getMoves().getOutcome()
        }

    private fun part2() =
        input.sumOf {
            it.getMovesByDesiredOutcome().getOutcome()
        }

    private fun String.getMoves(): Pair<Move, Move> =
        Pair(this[0].getMove(), this[2].getMove())

    private fun String.getMovesByDesiredOutcome(): Pair<Move, Move> =
        Pair(this[0].getMove(), this.getMoveByDesiredOutcome())

    private fun String.getMoveByDesiredOutcome(): Move =
        when (this[2]) {
            'Y' -> this[0].getMove()
            'X' -> when(this[0].getMove()) {
                Move.ROCK -> Move.SCISSORS
                Move.SCISSORS -> Move.PAPER
                Move.PAPER -> Move.ROCK
            } else -> {
                when(this[0].getMove()) {
                    Move.ROCK -> Move.PAPER
                    Move.SCISSORS -> Move.ROCK
                    Move.PAPER -> Move.SCISSORS
                }
            }
        }

    private fun Pair<Move, Move>.getOutcome() =
        this.matchResults() + this.second.pointsFromDecision()

    private fun Pair<Move, Move>.matchResults() =
        if (this.first == this.second) {
            3
        } else {
            when (this.first) {
                Move.ROCK -> if (this.second == Move.PAPER) 6 else 0
                Move.PAPER -> if (this.second == Move.SCISSORS) 6 else 0
                Move.SCISSORS -> if (this.second == Move.ROCK) 6 else 0
            }
        }

    private fun Char.getMove(): Move =
        when (this) {
            'A', 'X' -> Move.ROCK
            'B', 'Y' -> Move.PAPER
            else -> Move.SCISSORS
        }

    private fun Move.pointsFromDecision(): Int =
        when(this) {
            Move.ROCK -> 1
            Move.PAPER -> 2
            Move.SCISSORS -> 3
        }
}