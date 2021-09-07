package pro.it_dev.childgame.presentation.util

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun <T> EditField(
	stateValue: State<T>,
	modifier: Modifier = Modifier,
	adapter: (T) -> String = { it.toString() },
	label: @Composable (() -> Unit)? = null,
	fontSize: TextUnit = 18.sp,
	maxLines: Int = 1,
	keyboardOptions: KeyboardOptions = KeyboardOptions(),
	onChangeSetter: (String) -> Unit
) {
	val stateValue by remember { stateValue }
	var borderSize by remember { mutableStateOf(1.dp) }

	TextField(
		value = adapter(stateValue),
		label = label,
		onValueChange = { onChangeSetter(it) },
		modifier = modifier
			.padding(horizontal = 2.dp, vertical = 2.dp)
			.border(borderSize, MaterialTheme.colors.secondary, CircleShape)
			.shadow(2.dp, CircleShape)
			.background(color = MaterialTheme.colors.background, CircleShape)
			.onFocusChanged {
				borderSize = if (it.isFocused) {
					3.dp
				} else {
					1.dp
				}
			},
		maxLines = maxLines,
		textStyle = TextStyle(
			fontSize = fontSize
		),
		keyboardOptions = keyboardOptions
	)
}