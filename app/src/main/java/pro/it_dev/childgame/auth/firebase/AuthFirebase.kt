package pro.it_dev.childgame.auth.firebase

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.tasks.await
import pro.it_dev.childgame.auth.User
import pro.it_dev.childgame.auth.IAuth
import pro.it_dev.childgame.util.Resource

class AuthFirebase(private val instance:FirebaseAuth): IAuth<User> {
	private val scope = MainScope()
	override suspend fun register(email: String, password: String): Resource<User> {
		TODO("Not yet implemented")
	}

	override suspend fun login(email: String, password: String): Resource<User> {
		try {
			val result = instance.signInWithEmailAndPassword(email, password).await()
			return if (result.user != null) {
				val fbUser = instance.currentUser ?: return Resource.Error("Mega Error 1")
				Resource.Success(User(fbUser.email ?: return Resource.Error("Mega Error 2"))) //xD
			}else
				return Resource.Error("Error")


		}catch (e: Exception){
			return Resource.Error(e.message)
		}
	}

	override suspend fun logOut(): Resource<User> {
		instance.signOut()
		return loginState()
	}

	override suspend fun loginState(): Resource<User> {
		val fbUser = instance.currentUser ?: return Resource.Error("Not Logged")
		val email = fbUser.email ?: return Resource.Error("Email is null!!!")
		return Resource.Success( User(email = email) )
	}

}