package pro.it_dev.childgame.presentation.util

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

class ToastMessageData(val text: String, val length: Int = Toast.LENGTH_LONG)
@Composable
fun ToastMessage(toastMessageDataState: MutableState<ToastMessageData?>) {
	val message by remember { toastMessageDataState }
	var toastState = remember <Toast?>{
		null
	}
	if (message != null) {
		val ctx = LocalContext.current
		LaunchedEffect(message) {
			toastState?.cancel()
			val toast = Toast.makeText(ctx, message?.text  ?: "error msg", message!!.length)
			toast.show()
			toastState = toast
			toastMessageDataState.value = null
		}
	}
}