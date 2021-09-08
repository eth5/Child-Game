package pro.it_dev.childgame.presentation.util

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
private fun AnimatedBoxesContent(contents: List<@Composable() () -> Unit>) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier
			.fillMaxWidth(0.35f)
			.padding(10.dp)
	) {
		var index = 0
		contents.forEach {
			var offset by remember {
				mutableStateOf((-200).dp)
			}
			val animateDp by animateDpAsState(
				targetValue = offset,
				animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy)
			)
			LaunchedEffect(Unit) { index++;delay(100L * index);offset = 0.dp }
			Box(modifier = Modifier
				.offset(x = animateDp)
				.fillMaxWidth()
				.padding(2.dp)
				.border(1.dp, Color.LightGray, RectangleShape)
				.background(Color.Black, RectangleShape)
			) {
				it()
			}
		}

	}
}

@Composable
fun AnimationByOffsetInBox(
	start: Dp,
	target:Dp,
	animationSpec: AnimationSpec<Dp> = spring(dampingRatio = Spring.DampingRatioLowBouncy),
	delay:Long = 0L,
	finishedListener: ((Dp) -> Unit)? = null,
	content: @Composable BoxScope.() -> Unit
) {
	var offset by remember { mutableStateOf(start) }
	val animateDp by animateDpAsState(
		targetValue = offset,
		animationSpec = animationSpec,
		finishedListener = finishedListener
	)
	LaunchedEffect(Unit) { delay(delay);offset = target }
	Box(modifier = Modifier
		.offset(x = animateDp),
		content = content
	)
}