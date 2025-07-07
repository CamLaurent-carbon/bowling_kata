package service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * Test class for validating the functionality of the Game class.
 *
 * This class contains unit tests to verify correct behavior and edge cases
 * for the Game class, including initialization, gameplay mechanics, and scoring logic.
 *
 * Each test method targets a specific aspect of the Game class and ensures
 * that it operates as expected under various conditions.
 */
class GameTest {
    @Test
    fun `should initialize game with valid skill level`() {
        assertDoesNotThrow { Game(5) }
    }

    @Test
    fun `should throw exception for invalid skill level`() {
        assertThrows<IllegalArgumentException> { Game(11) }
        assertThrows<IllegalArgumentException> { Game(-1) }
    }

    @Test
    fun `should create correct number of frames when game starts`() {
        val game = Game(5)
        game.start()
        assertEquals(11, game.frameHistory.size)
    }

    @Test
    fun `should calculate score correctly`() {
        val game = Game(5)
        game.frameHistory.add(Frame(10, 0)) // Strike
        game.frameHistory.add(Frame(5, 5))  // Spare
        game.frameHistory.add(Frame(3, 4))  // Open frame
        assertEquals(40, game.score())
    }

    @Test
    fun `should calculate complete game score correctly`() {
        val game = Game(5)
        game.frameHistory.add(Frame(10, 0)) // Strike = 20
        game.frameHistory.add(Frame(7, 3))  // Spare = 19
        game.frameHistory.add(Frame(9, 0))  // Open = 9
        game.frameHistory.add(Frame(10, 0)) // Strike = 18
        game.frameHistory.add(Frame(0, 8))  // Open = 8
        game.frameHistory.add(Frame(10, 0)) // Strike = 29
        game.frameHistory.add(Frame(10, 0)) // Strike = 20
        game.frameHistory.add(Frame(9, 1))  // Spare = 15
        game.frameHistory.add(Frame(5, 5))  // Spare = 20
        game.frameHistory.add(Frame(10, 0, 7, 2)) // Last frame = 19
        assertEquals(177, game.score())
    }
}