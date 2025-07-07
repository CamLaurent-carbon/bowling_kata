package service

import kotlin.random.Random

class Game(
    private val skillLevel: Int = 0
) {
    init {
        require(skillLevel in 0..10) { "Skill level must be between 0 and 10" }
    }

    val totalFrame = 10
    val frameHistory = mutableListOf<Frame>()

    fun start() {
        for (turn in 0..totalFrame) {
            if (totalFrame == turn) {
                frameHistory.add(lastTurn())
            } else {
                frameHistory.add(classicTurn())
            }
        }
    }

    fun score(): Int {
        var totalScore = 0
        for (i in 0..totalFrame) {
            val frame = frameHistory.getOrNull(i) ?: continue
            totalScore += frame.firstShot + frame.secondShot + calculateBonus(i)

            if (i == totalFrame - 1) {
                totalScore += frame.thirdShot + frame.fourthShot
            }
        }
        return totalScore
    }

    private fun classicTurn(): Frame {
        val firstShot = roll()
        if (firstShot == 10) {
            return Frame(
                firstShot = 10,
                secondShot = 0
            )
        }
        val secondShot = roll(10 - firstShot)
        return Frame(
            firstShot = firstShot,
            secondShot = secondShot
        )
    }

    private fun lastTurn(): Frame {
        val lastFrame = classicTurn()
        if (lastFrame.isSpare) {
            return Frame(
                firstShot = lastFrame.firstShot,
                secondShot = lastFrame.secondShot,
                thirdShot = roll()
            )
        } else if (lastFrame.isStrike) {
            return Frame(
                firstShot = lastFrame.firstShot,
                secondShot = lastFrame.secondShot,
                thirdShot = roll(),
                fourthShot = roll()
            )
        }
        return lastFrame
    }

    private fun roll(pins: Int = 10): Int {
        // if you a 10 of skill you have 70% of chance to strike the rest of pins
        val strikeChance = (skillLevel * 7.0) / 100.0
        return if (Random.nextDouble() < strikeChance) {
            pins
        } else {
            Random.nextInt(pins)
        }
    }

    private fun calculateBonus(frameIndex: Int): Int {
        if (frameIndex >= frameHistory.size) return 0

        val frame = frameHistory[frameIndex]
        return when {
            frame.isStrike -> {
                val nextFrame = frameHistory.getOrNull(frameIndex + 1)
                if (nextFrame != null) {
                    nextFrame.firstShot + if (nextFrame.isStrike && frameIndex < totalFrame - 1) {
                        frameHistory.getOrNull(frameIndex + 2)?.firstShot ?: 0
                    } else {
                        nextFrame.secondShot
                    }
                } else 0
            }

            frame.isSpare -> frameHistory.getOrNull(frameIndex + 1)?.firstShot ?: 0
            else -> 0
        }
    }
}