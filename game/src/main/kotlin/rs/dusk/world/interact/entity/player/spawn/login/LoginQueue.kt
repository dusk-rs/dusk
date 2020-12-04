package rs.dusk.world.interact.entity.player.spawn.login

import com.github.michaelbull.logging.InlineLogger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.koin.dsl.module
import rs.dusk.engine.entity.character.IndexAllocator
import rs.dusk.engine.entity.character.player.Player
import rs.dusk.engine.entity.character.player.PlayerSpawn
import rs.dusk.engine.entity.list.MAX_PLAYERS
import rs.dusk.engine.event.EventBus
import rs.dusk.game.model.player.PlayerIO
import java.util.*

/**
 * Keeps track of number of players online, prevents duplicate login attempts
 * Loads player save files in the background, queueing once successful
 * Each tick at the correct time, accepts the first [loginPerTickCap] players into the world.
 */
class LoginQueue(
    private val io: PlayerIO,
    private val bus: EventBus,
    private val loginPerTickCap: Int,
    private val attempts: MutableSet<String> = mutableSetOf(),
    private val collection: Queue<Pair<Player, LoginRequest>> = LinkedList(),
    private val indexer: IndexAllocator = IndexAllocator(MAX_PLAYERS)
) {
    init {
        logger.info { "Login queue worker running." }
    }

    private val mutex = Mutex()

    /**
     * Calls login for first loginPerTickCap loaded players
     */
    suspend fun tick() {
        logger.debug { "tick" }
        var count = 0
        var next = collection.poll()

        mutex.withLock {

            while (next != null) {
                logger.trace { "[next=$next]" }

                login(next.first, next.second)
                if (count++ >= loginPerTickCap) {
                    break
                }
                next = collection.poll()
            }
        }
    }

    /**
     * Accepts client and spawns player in world.
     */
    fun login(player: Player, attempt: LoginRequest) {
        logger.debug { "Request - Emit player spawn [$player, $attempt]" }
        bus.emit(PlayerSpawn(player, attempt.name, attempt.session, attempt.message))
        attempt.respond(LoginResponse.Success(player))
        logger.debug { "Complete - Respond [$player, $attempt]" }
    }

    /**
     * Check hasn't already attempted login before attempt to load in background
     */
    fun add(request: LoginRequest): Boolean {
        logger.trace { "Checking if the player was already online!" }
        if (attempts.contains(request.name)) {
            request.respond(LoginResponse.AccountOnline)
            logger.trace { "Login attempt failed [login=$request]" }
            return false
        } else {
            logger.trace { "Login attempt added [login=$request]" }
            attempts.add(request.name)
        }
        val index = indexer.obtain()
        logger.trace { "Index shall be [index=$index]" }

        val response = load(index, request)
        logger.trace { "Login attempt synced [login=$request, response=response]" }
        if (response !is LoginResponse.Success) {
            remove(request.name)
            request.respond(response)
        }
        return true
    }

    fun remove(name: String) {
        attempts.remove(name)
    }

    /**
     * Loads player save in the background.
     */
    fun load(index: Int?, attempt: LoginRequest): LoginResponse {
        if (index == null) {
            return LoginResponse.WorldFull
        }
        val player = io.load(attempt.name)
        return try {
            player.index = index
            collection.add(player to attempt)
            logger.info { "Player ${attempt.name} loaded and queued for login." }
            LoginResponse.Success(player)
        } catch (e: IllegalStateException) {
            logger.error(e) { "Error loading player $attempt" }
            LoginResponse.CouldNotCompleteLogin
        }
    }

    companion object {
        private val logger = InlineLogger()
        private val scope = CoroutineScope(newFixedThreadPoolContext(2, "LoginQueue"))
    }
}

/**
 * @author Greg Hibberd <greg@greghibberd.com>
 * @since March 31, 2020
 */
val loginQueueModule = module {
    single {
        LoginQueue(
            get(),
            get(),
            getProperty("loginPerTickCap", 1)
        )
    }
}