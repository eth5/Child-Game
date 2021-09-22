package pro.it_dev.childgame.presentation.screen.auth

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import pro.it_dev.childgame.auth.IAuth
import pro.it_dev.childgame.auth.User
import pro.it_dev.childgame.presentation.util.ToastMessageData
import pro.it_dev.childgame.util.Resource
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
	private val auth: IAuth<User>
):ViewModel() {

	val toastMessage = mutableStateOf<ToastMessageData?>(null)

	private val _loginState = mutableStateOf<Resource<User>>(Resource.Loading())
	val loginState: State<Resource<User>> get() = _loginState
	init {
		viewModelScope.launch {
			_loginState.value = auth.loginState()
		}
	}

	fun login(email:String,passwd:String){
		if (_loginState.value is Resource.Loading) return
		_loginState.value = Resource.Loading()

		viewModelScope.launch {
			_loginState.value = viewModelScope.async { auth.login(email, passwd) }.await()
			if (_loginState.value is Resource.Error) toastMessage.value = ToastMessageData(_loginState.value.message ?: "null")
		}
	}

	fun create(email:String,passwd:String){
		if (_loginState.value is Resource.Loading) return
		_loginState.value = Resource.Loading()

		viewModelScope.launch {
			_loginState.value = viewModelScope.async { auth.register(email, passwd) }.await()
		}
	}

}