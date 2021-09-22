package pro.it_dev.childgame.auth

import pro.it_dev.childgame.util.Resource

interface IAuth<T> {
	suspend fun register(email:String, password:String): Resource<T>
	suspend fun login(email:String, password:String): Resource<T>
	suspend fun logOut(): Resource<T>
	suspend fun loginState():Resource<T>
}