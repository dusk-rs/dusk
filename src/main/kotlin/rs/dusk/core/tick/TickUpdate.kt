package rs.dusk.engine.tick

import rs.dusk.core.event.Event
import rs.dusk.engine.event.EventCompanion

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since July 11, 2020
 */
object TickUpdate : Event<Unit>(), EventCompanion<TickUpdate>