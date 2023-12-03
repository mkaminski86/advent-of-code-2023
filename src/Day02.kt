private class Game(val id: Int, val draws: List<Draw>) {
  fun isPossible(bag: Map<CubeColor, Int>) =
    draws.all { it.isPossible(bag) }
  fun maxBy(color: CubeColor) = draws.maxBy { it.cubesWithCounts[color] ?: 0 }.cubesWithCounts[color]!!

}

private class Draw(val cubesWithCounts: Map<CubeColor, Int>) {
  fun isPossible(bag: Map<CubeColor, Int>) =
    cubesWithCounts.all { (color, count) -> count <= bag[color]!! }
}

private enum class CubeColor {
  red,
  green,
  blue
}

private fun parseInput(lines: List<String>): List<Game> = lines.map { line ->
  val gameId = line.substringBefore(':').substringAfter(' ').toInt()
  val draws = line.substringAfter(": ").split("; ").map { draw ->
    val cubesWithCounts = draw.split(", ").associate {
      CubeColor.valueOf(it.substringAfter(' ')) to it.substringBefore(' ').toInt()
    }
    Draw(cubesWithCounts)
  }
  Game(gameId, draws)
}

fun main() {
  fun part1(input: List<String>): Int {
    val bag = mapOf(CubeColor.red to 12, CubeColor.green to 13, CubeColor.blue to 14)
    val games = parseInput(input)
    return games.filter { it.isPossible(bag) }.sumOf { it.id }
  }

  fun part2(input: List<String>): Int {
    val games = parseInput(input)
    fun List<Int>.power() = this.reduce { acc, el -> acc * el }
    return games.map { g -> CubeColor.entries.map { color -> g.maxBy(color) }.power()
    }.sum()
  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day02_test")
  check(part1(testInput) == 8)
  check(part2(testInput) == 2286)

  val input = readInput("Day02")
  part1(input).println()
  part2(input).println()
}
