package exercises

import java.io.File

abstract class DailyExercise {
    protected abstract val title: String
    protected abstract val dayNumber: Int

    abstract fun runExercise()

    private fun displayTitle() {
        println("\n--- Day $dayNumber: $title ---")
    }

    fun run() {
        displayTitle()
        runExercise()
    }

    protected fun getInput(filename: String): List<String> = File(filename).readLines()

    protected fun getInputAsString(filename: String) = File(filename).readText()

    protected fun getInputAsGrid(filename: String) = getInput(filename).map { it.asIntList() }

    private fun String.asIntList() = this.map { c -> c.digitToInt() }
}