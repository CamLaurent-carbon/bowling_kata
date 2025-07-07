package service

/**
 * Represents a frame in a bowling game with up to four shots.
 *
 * @property firstShot The number of pins knocked down in the first shot of the frame.
 * @property secondShot The number of pins knocked down in the second shot of the frame.
 * @property thirdShot The number of pins knocked down in the third shot, used only in the last frame.
 * @property fourthShot The number of pins knocked down in the fourth shot, used only for special cases.
 * @property isStrike Indicates whether the frame is a strike. A strike occurs if all ten pins are knocked down in the first shot.
 * @property isSpare Indicates whether the frame is a spare. A spare occurs if all ten pins are knocked down across the first two shots, excluding a strike.
 */
class Frame (
    val firstShot: Int,
    val secondShot: Int,
    val thirdShot: Int = 0,
    val fourthShot: Int = 0,
) {

    val isStrike = firstShot == 10
    val isSpare = !isStrike && firstShot + secondShot == 10
}