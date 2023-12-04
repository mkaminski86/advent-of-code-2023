private class NumberWithDigitLocations {
  val number = mutableListOf<Char>()
  val locations = mutableSetOf<Point>()
  fun add(digit: Char, location: Point) {
    number.add(digit)
    this.locations.add(location)
  }

  fun isEmpty() = number.isEmpty()
  fun toInt() = number.joinToString("").toInt()
  fun isAdjacentToAny(points: Set<Point>) = locations.flatMap { it.neighbours() }.toSet().intersect(points).isNotEmpty()
  fun isAdjacentTo(point: Point) = point in locations.flatMap { it.neighbours() }.toSet()
}

private class NumbersAndSymbols(val numbers: Set<NumberWithDigitLocations>, val symbols: Set<Point>)

private fun parseEngineScheme(input: List<String>, shouldTakeSymbol: (Char) -> Boolean): NumbersAndSymbols {
  val numbers = mutableSetOf<NumberWithDigitLocations>()
  val symbols = mutableSetOf<Point>()
  input.forEachIndexed { y, row ->
    var currentNumber = NumberWithDigitLocations()
    row.forEachIndexed { x, i ->
      if (i.isDigit()) {
        currentNumber.add(i, Point(x, y))
      } else {
        if (!currentNumber.isEmpty()) {
          numbers.add(currentNumber)
          currentNumber = NumberWithDigitLocations()
        }
        if (shouldTakeSymbol(i)) {
          symbols.add(Point(x, y))
        }
      }
    }
    if (!currentNumber.isEmpty()) {
      numbers.add(currentNumber)
      currentNumber = NumberWithDigitLocations()
    }
  }
  return NumbersAndSymbols(numbers, symbols)
}

fun main() {
  fun part1(input: List<String>): Int {
    val engineScheme = parseEngineScheme(input) { i -> i != '.' }
    return engineScheme.numbers.filter { it.isAdjacentToAny(engineScheme.symbols) }.sumOf { it.toInt() }
  }

  fun part2(input: List<String>): Int {
    val engineScheme = parseEngineScheme(input) { i -> i == '*' }
    return engineScheme.symbols.sumOf { s ->
      engineScheme.numbers.filter { it.isAdjacentTo(s) }.takeIf { it.size == 2 }?.let {
        it.first().toInt() * it.last().toInt()
      } ?: 0
    }
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day03_test")
  check(part1(testInput) == 4361)
  check(part2(testInput) == 467835)

  val input = readInput("Day03")
  part1(input).println()
  part2(input).println()
}
