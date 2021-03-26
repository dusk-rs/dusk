package rs.dusk.network.rs.session

import io.netty.channel.Channel
import rs.dusk.core.network.model.session.Session

/**
 * @author Tyluur <itstyluur@icloud.com>
 * @since April 09, 2020
 */
class GameSession(channel: Channel) : Session(channel)