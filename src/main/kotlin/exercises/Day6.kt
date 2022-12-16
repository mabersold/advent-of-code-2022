package exercises

class Day6: DailyExercise() {
    override val title: String = "Tuning Trouble"
    override val dayNumber: Int = 6
    private val input = getInputAsString("src/main/resources/day6.txt")

    override fun runExercise() {
        println("The start-of-packet marker appears after character ${getMarkerFromSequence(4)}")
        println("The start-of-message marker appears after character ${getMarkerFromSequence(14)}")
    }

    private fun getMarkerFromSequence(sequenceSize: Int): Int {
        // 1. Create a map with all the frequencies for the initial window of characters from the input
        val charFrequencyMap = input.createInitialMap(sequenceSize)

        for (i in sequenceSize until input.length) {
            // 2. Determine if we have identified the position of the string where there are only unique characters, and return it if so
            if (charFrequencyMap.keys.size == sequenceSize) {
                return i
            }

            // 3. Shift the window by bumping the earliest char off the map, and adding the newest one
            val charToRemove = input[i - sequenceSize]
            val charToAdd = input[i]
            charFrequencyMap.shiftWindow(charToRemove, charToAdd)
        }

        // This is just here to keep the compiler happy
        return input.length
    }

    private fun String.createInitialMap(sequenceSize: Int): HashMap<Char, Int> {
        val charFrequencyMap = hashMapOf<Char, Int>()
        this.substring(0 until sequenceSize).forEach { c ->
            charFrequencyMap.inc(c)
        }
        return charFrequencyMap
    }
    
    private fun HashMap<Char, Int>.shiftWindow(charToRemove: Char, charToAdd: Char) {
        this.dec(charToRemove)
        this.inc(charToAdd)
    }

    /**
     * Increment a character's frequency in the hashmap, or set it to 1 if not present
     */
    private fun HashMap<Char, Int>.inc(c: Char) {
        this[c] = this[c]?.plus(1) ?: 1
    }

    /**
     * Decrement a character's frequency in the hashmap, or remove it if it is at 1
     */
    private fun HashMap<Char, Int>.dec(c: Char) {
        if (this[c] == 1) {
            this.remove(c)
        } else {
            this[c] = this[c]?.minus(1)!!
        }
    }
}