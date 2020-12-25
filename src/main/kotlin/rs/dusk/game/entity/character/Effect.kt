package rs.dusk.game.entity.character

import get
import rs.dusk.engine.task.Task
import rs.dusk.engine.task.TaskExecutor

abstract class Effect(val effectType : String) {
	
	private var task : Task? = null
	
	fun removeSelf(character : Character, ticks : Int) {
		val executor : TaskExecutor = get()
		task = rs.dusk.core.task.delay(ticks) {
			character.effects.remove(this)
		}
	}
	
	open fun immune(character : Character) : Boolean {
		return false
	}
	
	open fun onStart(character : Character) {
	}
	
	open fun onFinish(character : Character) {
		task?.cancel()
	}
}