package rs.dusk.engine.entity.character.combat

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 9:30 PM
 **/
class CombatEventBus {

    /**
     * Event bus, damage events, poison events etc are sent through this event bus
     */

    val events: MutableSharedFlow<CombatEvent> by lazy { MutableSharedFlow() }

    fun sendIntervalEvent(combatEvent: CombatEvent, delay: Long, unit: TimeUnit): Job {
        return interval(delay, unit)
            .onEach { events.emit(combatEvent) }
            .launchIn(CoroutineScope(SupervisorJob()))
    }

    suspend fun sendEvent(combatEvent: CombatEvent) {
        events.emit(combatEvent)
    }

    suspend fun sendEvents(vararg events: CombatEvent) {
        this.events.emitAll(events.asFlow())
    }

    fun sendBlockingEvent(combatEvent: CombatEvent) = runBlocking {
        events.emit(combatEvent)
    }

}

fun dynamicInterval(startTimeInMillis: Long, unit: TimeUnit, nextDelay: () -> Long = { startTimeInMillis }) = flow {
    var counter: Long = 0
    while(true) {
        delay(
            when (unit) {
                TimeUnit.MICROSECONDS -> nextDelay() / 1000
                TimeUnit.NANOSECONDS -> nextDelay() / 1_000_000
                TimeUnit.SECONDS -> nextDelay() * 1000
                TimeUnit.MINUTES -> 60 * nextDelay() * 1000
                TimeUnit.HOURS -> 60 * 60 * nextDelay() * 1000
                TimeUnit.DAYS -> 24 * 60 * 60 * nextDelay() * 1000
                else -> nextDelay()
            }
        )
        emit(counter++)
    }
}

fun interval(timeInMillis: Long, unit: TimeUnit): Flow<Long> = flow {

    var counter: Long = 0

    val delayTime = when (unit) {
        TimeUnit.MICROSECONDS -> timeInMillis / 1000
        TimeUnit.NANOSECONDS -> timeInMillis / 1_000_000
        TimeUnit.SECONDS -> timeInMillis * 1000
        TimeUnit.MINUTES -> 60 * timeInMillis * 1000
        TimeUnit.HOURS -> 60 * 60 * timeInMillis * 1000
        TimeUnit.DAYS -> 24 * 60 * 60 * timeInMillis * 1000
        else -> timeInMillis
    }

    while (true) {

        delay(delayTime)
        emit(counter++)

    }

}