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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AboutScreen(onDismissRequest: () -> Unit) {
	Box(
		modifier = Modifier
			.padding(10.dp)
			.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Column(
			modifier = Modifier,
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Text(
				text = "Это приложение для самых маленьких, сделанное специально обученными людьми",
				fontWeight = FontWeight.ExtraBold,
				fontSize = 20.sp,
				modifier = Modifier
					.fillMaxWidth()
					.verticalScroll(rememberScrollState())
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

