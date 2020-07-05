import rs.dusk.engine.event.priority
import rs.dusk.engine.event.then
import rs.dusk.engine.model.entity.Registered
import rs.dusk.engine.model.entity.Unregistered
import rs.dusk.engine.model.entity.index.Character
import rs.dusk.engine.model.entity.index.Moved
import rs.dusk.engine.model.entity.index.npc.NPC
import rs.dusk.engine.model.entity.index.npc.NPCs
import rs.dusk.engine.model.entity.index.player.Player
import rs.dusk.engine.model.entity.index.player.PlayerMoveType
import rs.dusk.engine.model.entity.index.player.Players
import rs.dusk.engine.model.entity.index.update.visual.player.*
import rs.dusk.utility.inject

val players: Players by inject()
val npcs: NPCs by inject()

Registered priority 9 then {
    when (entity) {
        is Player -> {
            players.add(entity)
            entity.viewport.players.add(entity)
            entity.temporaryMoveType = PlayerMoveType.None
            entity.movementType = PlayerMoveType.None
            entity.flagMovementType()
            entity.flagTemporaryMoveType()
            entity.face()
        }
    }
}

Unregistered priority 9 then {
    when (entity) {
        is Player -> players.remove(entity)
    }
}

Moved priority 9 where { entity is Character } then {
    when(entity) {
        is Player -> players.update(from, to, entity)
        is NPC -> npcs.update(from, to, entity)
    }
}