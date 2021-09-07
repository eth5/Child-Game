package pro.it_dev.childgame.presentation.dialogs.alert

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*

class AlertDialogData(val text: String, val title: String = "Title",val onDismissRequest: () -> Unit, val onConfirmRequest: () -> Unit )
@Composable
fun Alert(showState: MutableState<AlertDialogData?>) {
	val dialogData by remember { showState }
	dialogData?.let {
		AlertDialog(
			onDismissRequest = it.onDismissRequest,
			dismissButton = {
				Button(onClick = it.onDismissRequest) {
					Text(text = "No")
				}
			},
			confirmButton = {
				Button(onClick = it.onConfirmRequest) {
					Text(text = "YES")
				}
			},
			title = { Text(text = it.title) },
			text = { Text(text = it.text) }
		)
	}

}