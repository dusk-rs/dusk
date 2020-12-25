package rs.dusk.game.entity.character.player.data.delay

sealed class Delay(val ticks: Int) {
    object Eat : Delay(3)
    object Drink : Delay(3)
    object ComboFood : Delay(3)
    object DoorSlam : Delay(10)
    object RequestAssist : Delay(16)
}