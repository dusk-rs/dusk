package rs.dusk.network

import org.koin.dsl.module

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @Since December 17, 2020
 */
class NetworkInitializer {
	
	/**
	 * Binds the network
	 */
	fun bind() {
	
	}
	
}

val networkModule = module {
	single { NetworkInitializer() }
}