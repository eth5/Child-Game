package pro.it_dev.childgame.presentation.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun DialogWrapper(modifier: Modifier = Modifier, onDismissRequest: () -> Unit, content: @Composable ()->Unit) {
	Dialog(
		onDismissRequest = onDismissRequest,
		properties = DialogProperties(dismissOnClickOutside = true)
	) {
		Surface(
			modifier = modifier,
			border = BorderStroke(2.dp,MaterialTheme.colors.onSurface),
			shape = MaterialTheme.shapes.medium,
			elevation = 20.dp,
			color = MaterialTheme.colors.surface,
			content = content
		)
	}
}