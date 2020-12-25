package rs.dusk.game.entity.character.player

import inject
import rs.dusk.client.ui.InterfaceOptions
import rs.dusk.client.ui.PlayerInterfaceIO
import rs.dusk.client.ui.detail.InterfaceDetails
import rs.dusk.core.map.Tile
import rs.dusk.core.network.session.Session
import rs.dusk.engine.client.ui.InterfaceManager
import rs.dusk.engine.client.ui.dialogue.Dialogues
import rs.dusk.engine.entity.character.player.skill.Experience
import rs.dusk.engine.entity.character.player.skill.Levels
import rs.dusk.engine.event.EventBus
import rs.dusk.game.container.ContainerDefinitions
import rs.dusk.game.entity.character.Character
import rs.dusk.game.entity.character.player.data.PlayerGameFrame
import rs.dusk.game.entity.character.player.data.Requests
import rs.dusk.game.entity.character.player.data.delay.Delays

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
data class Player(val username : String, var session : Session, override var tile : Tile) : Character() {
	
	private val eventBus : EventBus by inject()
	
	private val containerDefinitions : ContainerDefinitions by inject()
	
	private val interfaceDetails : InterfaceDetails by inject()
	
	private val interfaceIO = PlayerInterfaceIO(this, eventBus)
	
	val gameFrame = PlayerGameFrame()
	
	val interfaces = InterfaceManager(interfaceIO, interfaceDetails, gameFrame)
	
	var interfaceOptions = InterfaceOptions(this, interfaceDetails, containerDefinitions)
	
	val dialogues : Dialogues = Dialogues()
	
	val requests : Requests = Requests(this)
	
	val delays : Delays = Delays()
	
	val experience : Experience = Experience()
	
	val levels : Levels = Levels(experience)
	
	var removed = false
	
}