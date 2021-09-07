package pro.it_dev.childgame.presentation.dialogs.about

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AboutDialog(onDismissRequest: () -> Unit) {
	Dialog(
		onDismissRequest = onDismissRequest,
		properties = DialogProperties(dismissOnClickOutside = true)
	) {
		Box(
			modifier = Modifier
				.background(MaterialTheme.colors.background, MaterialTheme.shapes.medium)
				.border(1.dp, MaterialTheme.colors.secondary, MaterialTheme.shapes.medium)
				.padding(20.dp)
				.fillMaxWidth(1f)
				.fillMaxHeight(0.7f)
			,
			contentAlignment = Alignment.Center
		) {
			Column(
				modifier = Modifier,
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center
			) {
				Text(
					text = buildString {
						0.rangeTo(100).forEach {
							append( "http://it-dev.pro " )
						}
					},
					modifier = Modifier
						.fillMaxWidth()
						.verticalScroll(rememberScrollState())
						.background(Color.Red)
						.weight(1f)
					)
				TextButton(
					onClick = onDismissRequest
				) {
					Text(text = "OK, BRO")
				}
			}

		}
	}
}

