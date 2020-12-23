package rs.dusk.core

import inject
import org.koin.dsl.module
import rs.dusk.core.render.RenderSequence
import java.util.concurrent.Executor
import java.util.concurrent.Executors

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 18, 2020
 */
class GameWorker : Runnable {
	
	/**
	 * The timestamp of the end of the last cycle
	 */
	var lastCycleTime : Long = 0
	
	/**
	 * The executor.
	 */
	private val executor : Executor = Executors.newSingleThreadExecutor()
	
	private val render : RenderSequence by inject()
	
	/**
	 * If the worker has been started
	 */
	var started = false
	
	init {
		executor.execute(this)
		started = true
	}
	
	override fun run() {
		while (true) {
			val currentTime : Long = System.currentTimeMillis()
			try {
				render.render()
			} catch (e : Throwable) {
				e.printStackTrace()
			}
			sleepThread(currentTime)
		}
	}
	
	/**
	 * Handles the sleeping of the thread
	 */
	private fun sleepThread(startTime : Long) {
		lastCycleTime = System.currentTimeMillis()
		val sleepTime : Long = 600 + (startTime - lastCycleTime)
		if (sleepTime <= 0) {
			return
		}
		try {
			Thread.sleep(sleepTime)
		} catch (e : InterruptedException) {
			e.printStackTrace()
		}
	}
	
}

val gameWorkerModule = module {
	single(createdAtStart = true) { GameWorker() }
}