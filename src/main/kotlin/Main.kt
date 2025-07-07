package org.example

import service.Game
import kotlin.random.Random

/*
    I add a skill level system for check strike and spare correctly.
    And I add a game of 10 players with random skill level.
*/
fun main() {
    val players = (1..10).map { Random.nextInt(0, 11) }
    val scores = mutableMapOf<Int, List<Int>>()

    players.forEachIndexed { index, skillLevel ->
        val game = Game(skillLevel)
        game.start()
        scores[index] = listOf(skillLevel, game.score())
    }

    println("Final Rankings:")
    scores.entries.sortedByDescending { it.value[1] }
        .forEachIndexed { index, entry ->
            println("${index + 1}. Player ${entry.key + 1} (skill level: ${entry.value[0]}) - Score: ${entry.value[1]}")
        }

    val winner = scores.entries.maxByOrNull { it.value[1] }
    println("🎉 Congratulations to Player ${winner?.key?.plus(1)} for winning with a score of ${winner?.value?.get(1)}! 🎉")
}