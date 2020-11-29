package rs.dusk.engine.entity.character.combat

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import rs.dusk.engine.entity.character.Character
import java.util.concurrent.TimeUnit

/**
 * @author David Schlachter <davidschlachter96@gmail.com>
 * @nickname Javatar
 * @date 11/27/20 8:55 PM
 **/
class CombatHandler {
    val jobs: MutableList<Job> by lazy { mutableListOf() }
    val focusedTarget: MutableStateFlow<CombatTarget> by lazy { MutableStateFlow(NO_TARGET) }
    val eventHandler = CombatEventBus()

    /**
     * Starts a interval with a dynamic delay and emits all combat events, eg poison, damage, veng etc
     * This function should NOT be called in cycle, has the dynamic interval with the strategy
     * @param strategy The combat strategy
     * @see CombatStrategy
     */

    suspend fun attack(strategy: CombatStrategy) {

        dynamicInterval(strategy.nextAttackTime(), TimeUnit.MILLISECONDS) { strategy.nextAttackTime() }
            .collect { eventHandler.sendEvents(*strategy.createDamageEvent().toTypedArray()) }

    }

    /**
     * Binds the given target and subscribes that target to the attackers event bus
     * @param target The target
     */

    fun bind(target: Character) {

        if (focusedTarget.value === NO_TARGET) {
            focusedTarget.value = CombatTarget(target)
            eventHandler.events.onEach {
                it.apply(target)
            }.launchIn(CoroutineScope(Dispatchers.Unconfined)).also { jobs.add(it) }
        }

    }

    /**
     * Unbinds the focused target and disposes (cancels) all subscriptions
     */

    fun unbind() {
        focusedTarget.value = NO_TARGET
        jobs.forEach { it.cancel() }
    }

    companion object {
        val NO_TARGET = CombatTarget()
    }
}

infix fun CombatHandler.attack(character: Character): suspend (CombatStrategy) -> Unit {
    bind(character)
    return { attack(it) }
}

suspend infix fun (suspend (CombatStrategy) -> Unit).with(strategy: CombatStrategy) {
    this(strategy)
}