package pro.it_dev.childgame.auth.firebase

import pro.it_dev.childgame.auth.User
import pro.it_dev.childgame.auth.IAuth
import pro.it_dev.childgame.util.Resource

class NoGoogleAuthFirebase(): IAuth<User> {
	private val msg = "Need google services"
	override suspend fun register(email: String, passwd: String): Resource<User> {
		return Resource.Error(msg)
	}

	override suspend fun login(email: String, passwd: String): Resource<User> {
		return Resource.Error(msg)
	}

	override suspend fun logOut(): Resource<User> {
		return Resource.Error(msg)
	}

	override suspend fun loginState(): Resource<User> {
		return Resource.Error(msg)
	}

}