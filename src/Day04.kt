import kotlin.math.pow

private fun parseNumbers(numbersPart: String) = numbersPart.split(' ').filter { it.isNotEmpty() }
  .map { it.toInt() }

private fun getMatch(input: String) = input.substringAfter(": ").let {
  val winningNumbers = parseNumbers(it.substringBefore(" | "))
  val myNumbers = parseNumbers(it.substringAfter(" | "))
  winningNumbers.intersect(myNumbers.toSet()).size
}

fun main() {
  fun part1(input: List<String>) = input.map { getMatch(it) }.filter { it != 0 }.sumOf { 2.0.pow(it - 1) }.toInt()

  fun part2(input: List<String>): Int {
    val matchesForEachCard = input.map { getMatch(it) }
    val countOfEachCard = IntArray(matchesForEachCard.size) { 1 }
    matchesForEachCard.forEachIndexed { index, matchingNumbers ->
      for (i in 1..matchingNumbers) {
        countOfEachCard[index + i] += countOfEachCard[index]
      }
    }

    return countOfEachCard.sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day04_test")
  check(part1(testInput) == 13)
  check(part2(testInput) == 30)

  val input = readInput("Day04")
  part1(input).println()
  part2(input).println()
}
