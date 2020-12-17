package rs.dusk.network.codec

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since December 17, 2020
 */
sealed class NetworkResponseCode(val opcode: Int) {
	object DataChange : NetworkResponseCode(0)
	object Delay : NetworkResponseCode(1)//Video advertisement
	object Successful : NetworkResponseCode(2)
	object InvalidCredentials : NetworkResponseCode(3)
	object AccountDisabled : NetworkResponseCode(4)
	object AccountOnline : NetworkResponseCode(5)
	object GameUpdated : NetworkResponseCode(6)
	object WorldFull : NetworkResponseCode(7)
	object LoginServerOffline : NetworkResponseCode(8)
	object LoginLimitExceeded : NetworkResponseCode(9)//too many connections from your address
	object BadSessionId : NetworkResponseCode(10)
	object LoginServerRejectedSession : NetworkResponseCode(11)//Extremely common insecure password
	object MembersAccountRequired : NetworkResponseCode(12)
	object CouldNotCompleteLogin : NetworkResponseCode(13)
	object ServerBeingUpdated : NetworkResponseCode(14)
	object Reconnecting : NetworkResponseCode(15)//Error connecting
	object LoginAttemptsExceeded : NetworkResponseCode(16)//Too many incorrect login's from your address
	object MembersOnlyArea : NetworkResponseCode(17)
	object InvalidLoginServer : NetworkResponseCode(20)
	object TransferringProfile : NetworkResponseCode(21)//Error connecting
}