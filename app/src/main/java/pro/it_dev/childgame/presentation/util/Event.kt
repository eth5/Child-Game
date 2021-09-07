package pro.it_dev.childgame.presentation.util

import android.annotation.SuppressLint
import androidx.compose.runtime.*

@Composable
fun <T>Event(eventsState:MutableState<T?>,handler:(T)->Unit) {
	val event by remember {
		eventsState
	}
	if (event != null){
		LaunchedEffect(event){
			val saveEvent = event!!
			eventsState.value = null
			handler(saveEvent)
		}
	}
}
@SuppressLint("ComposableNaming")
@Composable
fun <T>MutableState<T?>.asEvent(handler:(T)->Unit) {
	val event by remember {
		this
	}
	if (event != null){
		LaunchedEffect(event){
			val saveEvent = event!!
			this@asEvent.value = null
			handler(saveEvent)
		}
	}
}