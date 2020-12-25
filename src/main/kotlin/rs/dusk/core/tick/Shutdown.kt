package rs.dusk.engine.tick

import rs.dusk.core.event.Event
import rs.dusk.engine.event.EventCompanion

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 28, 2020
 */
object Shutdown : Event<Unit>(), EventCompanion<Shutdown>