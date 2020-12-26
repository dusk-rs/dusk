package rs.dusk.client.update.task.player

import rs.dusk.core.tick.task.EntityTask
import rs.dusk.engine.event.Priority.PLAYER_UPDATE_FINISHED
import rs.dusk.game.entity.character.player.Player
import rs.dusk.game.entity.character.player.Players

/**
 * Resets non-persistent changes
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
class PlayerPostUpdateTask(override val entities: Players) : EntityTask<Player>(PLAYER_UPDATE_FINISHED) {

    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun runAsync(player: Player) {
        player.viewport.shift()
        player.viewport.players.update()
        player.viewport.npcs.update()
        player.movement.reset()
        player.visuals.aspects.forEach { (_, visual) ->
            visual.reset(player)
        }
    }

}