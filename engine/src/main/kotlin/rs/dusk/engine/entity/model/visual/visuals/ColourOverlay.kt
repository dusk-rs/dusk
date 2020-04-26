package rs.dusk.engine.entity.model.visual.visuals

import rs.dusk.engine.entity.model.NPC
import rs.dusk.engine.entity.model.Player
import rs.dusk.engine.entity.model.visual.Visual

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since April 25, 2020
 */
data class ColourOverlay(
    var delay: Int = 0,
    var duration: Int = 0,
    var colour: Int = 0
) : Visual

const val PLAYER_COLOUR_OVERLAY_MASK = 0x20000

const val NPC_COLOUR_OVERLAY_MASK = 0x200

fun Player.flagColourOverlay() = visuals.flag(PLAYER_COLOUR_OVERLAY_MASK)

fun NPC.flagColourOverlay() = visuals.flag(NPC_COLOUR_OVERLAY_MASK)

fun Player.getColourOverlay() = visuals.getOrPut(PLAYER_COLOUR_OVERLAY_MASK) { ColourOverlay() }

fun NPC.getColourOverlay() = visuals.getOrPut(NPC_COLOUR_OVERLAY_MASK) { ColourOverlay() }

fun Player.setColourOverlay(delay: Int = 0, duration: Int = 0, colour: Int = 0) {
    setColourOverlay(getColourOverlay(), delay, duration, colour)
    flagColourOverlay()
}

fun NPC.setColourOverlay(delay: Int = 0, duration: Int = 0, colour: Int = 0) {
    setColourOverlay(getColourOverlay(), delay, duration, colour)
    flagColourOverlay()
}

private fun setColourOverlay(overlay: ColourOverlay, delay: Int, duration: Int, colour: Int) {
    overlay.delay = delay
    overlay.duration = duration
    overlay.colour = colour
}

