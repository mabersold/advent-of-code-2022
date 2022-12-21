package exercises

/**
 * Maybe not my most efficient exercise, but it did the job.
 */
class Day8: DailyExercise() {
    override val title = "Treetop Tree House"
    override val dayNumber = 8
    private val input = getInputAsGrid("src/main/resources/day8.txt")

    /**
     * This exercise represents each tree as an object. It has references to its neighboring trees. We set up the tree
     * objects and set their references in the first two steps.
     *
     * Each tree has a function that determines whether it is visible from a certain direction, and how many trees you
     * can see from it from a certain direction.
     *
     * After building the grid, you just need to call those functions on all the inner trees to determine which ones
     * are visible, and which one has the highest scenic score.
     */
    override fun runExercise() {
        // Step 1: Map the input into a grid of trees. Only their height will be defined.
        val forest = input.map { row -> row.map { Tree(it) } }

        // Step 2: Iterate through the trees and set up their relationships to each other, such that each tree
        // has references to its neighbor.
        forest.connectTrees()

        // Step 3: Retrieve the inner trees and count how many of them are visible, then add the number of outer trees, which
        // are visible by default
        val totalVisible = forest.innerTrees().count { it.visible } + (forest.size * 4 - 4)

        // Step 4: Find the highest scenic score of all the inner trees (the outer trees will have a score of 0, so we ignore them
        val highestScenicScore = forest.innerTrees().maxOf { it.scenicScore }

        println("There are $totalVisible total trees visible")
        println("The highest scenic score of any tree is $highestScenicScore")
    }

    /**
     * This sets up the relationships between the trees
     */
    private fun List<List<Tree>>.connectTrees() {
        for (row in this) {
            row.windowed(2, 1) {
                it[0].neighbors[Direction.RIGHT]= it[1]
                it[1].neighbors[Direction.LEFT] = it[0]
            }
        }

        for (i in this.indices) {
            this.map { it[i] }.windowed(2, 1) {
                it[0].neighbors[Direction.DOWN]= it[1]
                it[1].neighbors[Direction.UP] = it[0]
            }
        }
    }

    /**
     * Convenience function to retrieve the inner trees from a forest
     */
    private fun List<List<Tree>>.innerTrees() = this.subList(1, this.size - 1).flatMap { it.subList(1, this.size - 1) }

    class Tree(
        private val height: Int
    ) {
        val neighbors = hashMapOf<Direction, Tree>()
        val visible get() = visibleFrom(Direction.LEFT) || visibleFrom(Direction.RIGHT) || visibleFrom(Direction.UP) || visibleFrom(Direction.DOWN)
        val scenicScore get() = treesInViewFrom(Direction.DOWN) * treesInViewFrom(Direction.UP) * treesInViewFrom(Direction.LEFT) * treesInViewFrom(Direction.RIGHT)

        /**
         * Recursive function to count the number of trees visible in any direction from a given tree
         */
        private fun treesInViewFrom(direction: Direction, height: Int = this.height): Int {
            val neighbor = neighbors[direction] ?: return 0
            return if (neighbor.height >= height) {
                1
            } else {
                1 + neighbor.treesInViewFrom(direction, height)
            }
        }

        /**
         * Determines whether the tree is visible from a given direction
         */
        private fun visibleFrom(direction: Direction): Boolean {
            val neighbor = neighbors[direction]
            val highestNeighbor = neighbor?.getHighestNeighbor(direction) ?: 0
            return this.height > highestNeighbor
        }

        /**
         * Determines what the tree's tallest neighbor is in a certain direction
         */
        private fun getHighestNeighbor(direction: Direction): Int {
            val neighbor = neighbors[direction]
            return neighbor?.let {
                maxOf(it.getHighestNeighbor(direction), this.height)
            } ?: this.height
        }

        override fun toString(): String {
            return "Height: $height, Left: ${neighbors[Direction.LEFT]?.height}, Right: ${neighbors[Direction.RIGHT]?.height}, Top: ${neighbors[Direction.UP]?.height}, Bottom: ${neighbors[Direction.DOWN]?.height}"
        }
    }

    enum class Direction {
        UP,
        LEFT,
        DOWN,
        RIGHT
    }
}