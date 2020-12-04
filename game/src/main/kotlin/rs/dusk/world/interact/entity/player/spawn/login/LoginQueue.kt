package rs.dusk.world.interact.entity.player.spawn.login

import com.github.michaelbull.logging.InlineLogger
import kotlinx.coroutines.*
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
    private val loader: PlayerIO,
    private val bus: EventBus,
    private val loginPerTickCap: Int,
    private val attempts: MutableSet<String> = mutableSetOf(),
    private val collection: Queue<Pair<Player, Login>> = LinkedList(),
    private val indexer: IndexAllocator = IndexAllocator(MAX_PLAYERS)
) {

    init {

    }

    private val load = Mutex()
    private val login = Mutex()

    /**
     * Calls login for first loginPerTickCap loaded players
     */
    fun tick() = runBlocking {
        logger.debug { "Login queue is ticking [size=${collection.size}]..." }

        login.withLock {
            logger.debug { "withLock" }

            var count = 0
            var next = collection.poll()
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
    fun login(player: Player, attempt: Login) {
        logger.debug { "Request - Emit player spawn [$player, $attempt]"}
        bus.emit(PlayerSpawn(player, attempt.name, attempt.session, attempt.data))
        attempt.respond(LoginResponse.Success(player))
        logger.debug { "Complete - Respond [$player, $attempt]"}
    }

    /**
     * Check hasn't already attempted login before attempt to load in background
     */
    fun add(login: Login): Deferred<Unit>? = runBlocking {
        logger.trace { "Created blocking task" }
        load.withLock {
            logger.trace { "Checking if the player was already online!"}
            if (attempts.contains(login.name)) {
                login.respond(LoginResponse.AccountOnline)
                logger.trace { "Login attempt failed [login=$login]" }
                return@runBlocking null
            } else {
                logger.trace { "Login attempt added [login=$login]" }
                attempts.add(login.name)
            }
            val index = indexer.obtain()
            scope.async {
                val response = load(index, login)
                logger.trace { "Login attempt synced [login=$login, response=response]" }
                if (response !is LoginResponse.Success) {
                    remove(login.name)
                    login.respond(response)
                }
            }
        }
    }

    fun remove(name: String) = runBlocking {
        load.withLock { attempts.remove(name) }
    }

    /**
     * Loads player save in the background.
     */
    suspend fun load(index: Int?, attempt: Login): LoginResponse {
        if (index == null) {
            return LoginResponse.WorldFull
        }
        try {
            val player = loader.load(attempt.name)
            player.index = index
            load.withLock { collection.add(player to attempt) }
            logger.info { "Player ${attempt.name} loaded and queued for login." }
            return LoginResponse.Success(player)
        } catch (e: IllegalStateException) {
            logger.error(e) { "Error loading player $attempt" }
            return LoginResponse.CouldNotCompleteLogin
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