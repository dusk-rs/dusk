package rs.dusk.game.world

import org.koin.dsl.module

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
class World {

}

val worldModule = module {
	single { World() }
}