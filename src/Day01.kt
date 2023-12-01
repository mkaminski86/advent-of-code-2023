fun turnIntoTwoDigitNumber(tenths: Int, ones: Int) = tenths * 10 + ones

fun main() {
  fun part1(input: List<String>) = input.map { it.toCharArray().filter { c -> c.isDigit() } }
    .sumOf { turnIntoTwoDigitNumber(it.first().digitToInt(), it.last().digitToInt()) }

  fun part2(input: List<String>): Int {
    val wordToDigit = mapOf(
      "one" to 1,
      "two" to 2,
      "three" to 3,
      "four" to 4,
      "five" to 5,
      "six" to 6,
      "seven" to 7,
      "eight" to 8,
      "nine" to 9,
      "ten" to 10,
    )

    fun mapToInt(digit: String): Int = digit.toIntOrNull()
      ?: wordToDigit[digit]
      ?: throw Error("Could not map $digit to string")


    fun findDigits(input: String): List<String> {
      val regex = wordToDigit.keys.joinToString("|").plus("|[1-9]").toRegex()
      var startIndex = 0
      val foundDigits = mutableListOf<String>()
      while (true) {
        val foundDigit = regex.find(input, startIndex) ?: break
        val r = foundDigit.range
        //eightwo -> eight, two but eight1two should skip 1 after matching it
        startIndex = if (r.first == r.last) r.last + 1 else r.last
        foundDigits += foundDigit.value
      }
      return foundDigits.toList()
    }

    return input.map { findDigits(it) }
      .sumOf { turnIntoTwoDigitNumber(mapToInt(it.first()), mapToInt(it.last())) }

  }

  val input = readInput("Day01")
  part1(input).println()
  part2(input).println()
}
