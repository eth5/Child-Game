package pro.it_dev.childgame.presentation.util

import android.annotation.SuppressLint
import androidx.compose.runtime.*
import kotlinx.coroutines.launch


@SuppressLint("ComposableNaming")
@Composable
fun <T>MutableState<T?>.asStateEvent(handler: @Composable (MutableState<T?>)->Unit) {
	val event by remember {
		this
	}
	if (event != null){
		handler(this)
	}
}