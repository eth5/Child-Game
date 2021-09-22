package pro.it_dev.childgame.presentation.util

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

class ToastMessageData(val text: String, val length: Int = Toast.LENGTH_LONG)
@Composable
fun ToastMessage(message: MutableState<ToastMessageData?>) {
	var toastState = remember <Toast?>{ null }
	if (message.value != null) {
		val ctx = LocalContext.current
		SideEffect {
			toastState?.cancel()
			val toast = Toast.makeText(ctx, message.value?.text  ?: "error msg", message.value?.length ?: Toast.LENGTH_LONG)
			toastState = toast
			toast.show()
			message.value = null
		}
	}
}