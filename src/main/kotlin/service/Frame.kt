package service

class Frame (
    val firstShot: Int,
    val secondShot: Int,
    val thirdShot: Int = 0,
    val fourthShot: Int = 0,
) {

    val isStrike = firstShot == 10
    val isSpare = !isStrike && firstShot + secondShot == 10
}