package pro.it_dev.childgame.presentation.dialogs.menu

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(

): ViewModel() {
	class MenuItem(val text:String, val action:()->Unit)

	private val _menuItems = mutableStateOf<List<MenuItem>>(
		listOf(
			MenuItem("Громкость") { },
			MenuItem("Item 1") { },
			MenuItem("Item 2") { },
			MenuItem("Карточки") { },
			MenuItem("Авторизация") { },
			MenuItem("Авторы") { },
		)
	)
	val menuItems:State<List<MenuItem>> get() = _menuItems
}