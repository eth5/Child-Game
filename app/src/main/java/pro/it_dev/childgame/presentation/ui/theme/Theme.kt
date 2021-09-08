package pro.it_dev.childgame.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
	primary = Purple200,
	primaryVariant = Purple700,
	secondary = Teal200
)

private val LightColorPalette = lightColors(
	primary = Purple500,
	primaryVariant = Purple700,

	//secondary = Color(0xFF_BFBF30),// Teal200,
	secondary = Color(0xFF_C0EDFC),// Teal200,
	onSecondary = Color(0xFF_3787A1),

	background = Color(0xFF_AAE5F8),
	onBackground = Color(0xFF_3787A1),

	surface = Color(0xFF_AAE5F8),
	onSurface = Color(0xFF_3787A1),
	/* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ChildGameTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
	val colors = if (darkTheme) {
//		DarkColorPalette
		LightColorPalette
	} else {
		LightColorPalette
	}

	MaterialTheme(
		colors = colors,
		typography = Typography,
		shapes = Shapes,
		content = content
	)
}