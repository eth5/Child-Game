package pro.it_dev.childgame.presentation.screen.jumping_buttons

import androidx.compose.foundation.border
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColorText(text:String, modifier: Modifier = Modifier, color1:Color = Color.Blue, color2: Color = Color.Red, fontSize: TextUnit = 30.sp, factor:Float = 1.5f){
	if (text.isEmpty() ||text.length < 2) return
	val firstLetter = remember { text.first() }
	val lastLetter = remember { text.last() }
	val centerText = remember { text.dropLast(1).drop(1) }
	val buildText = remember {
		buildAnnotatedString {

			var color = color1
			withStyle(style = SpanStyle(color = color,fontSize = fontSize * factor )) {
				append(firstLetter)
			}
			centerText.forEach {
				color = if (color == color1) color2 else color1
				withStyle(style = SpanStyle(color = color)) {
					append(it)
				}
			}
			withStyle(style = SpanStyle(color = if (color == color1) color2 else color1,fontSize = fontSize * factor)) {
				append(lastLetter)
			}
		}
	}
	Text(
		text = buildText,
		fontSize = fontSize,
		fontWeight = FontWeight.Bold,
		modifier = modifier
	)
}