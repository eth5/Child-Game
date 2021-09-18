package pro.it_dev.childgame.presentation.screen

import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.InsertEmoticon
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pro.it_dev.childgame.domain.CardsKit
import pro.it_dev.childgame.domain.Item
import pro.it_dev.childgame.presentation.dialogs.DialogWrapper
import pro.it_dev.childgame.presentation.dialogs.menu.Menu
import pro.it_dev.childgame.presentation.screen.jumping_buttons.ColorText
import pro.it_dev.childgame.util.Resource

@Composable
fun CardsScreen(itemsPath: String, viewModel: CardScreenViewModel = hiltViewModel()) {
	LaunchedEffect(key1 = itemsPath, block = { viewModel.loadCards(itemsPath) }) //todo переделать
	val cardsKit by remember {
		viewModel.cardsKit
	}
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.TopCenter
	) {
		ScreenCardsStateWrapper(cardsKit = cardsKit)
	}

	ShowMenuDialog(show = viewModel.showMenu)
	ShowMessage(message = viewModel.popUpMessage)
}

@Composable
fun ScreenCardsStateWrapper(
	cardsKit: Resource<CardsKit>,
	viewModel: CardScreenViewModel = hiltViewModel()
) {
	when (cardsKit) {
		is Resource.Loading -> CircularProgressIndicator()
		is Resource.Error -> Text(
			text = cardsKit.message ?: "Unknown error!",
			color = Color.Red
		)
		is Resource.Success -> {
			CardKitScope(
				cardsKit = cardsKit.data!!,
				viewModel = viewModel
			)
		}
	}
}

@Composable
fun CardKitScope(
	cardsKit: CardsKit,
	viewModel: CardScreenViewModel
) {
	val scope = rememberCoroutineScope()
	BackGroundImage(path = cardsKit.kitPath + "/bg.jpg", viewModel = viewModel)
	Column(
		modifier = Modifier
			.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Box(
			modifier = Modifier
		) {
			ColorText(
				text = "АЗБУКВАРИК",
				modifier = Modifier.pointerInput(Unit) {
					detectTapGestures(
						onLongPress = {
							viewModel.onLongPressToLogo()
						}
					)
				}
			)
		}
		Row(
			modifier = Modifier
				.weight(1f)
				.fillMaxSize(),
			horizontalArrangement = Arrangement.Center,
			verticalAlignment = Alignment.CenterVertically
		) {
			cardsKit.itemsGroupList.forEach {
				Column(
					modifier = Modifier
						.fillMaxHeight()
						.weight(1f, true)
						.padding(1.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center
				) {
					CardScope(
						item = it.bigItem,
						modifier = Modifier.weight(1f,false),
						scope = scope,
						imageBitmapCreator = viewModel::getBitmap,
						onClick = viewModel::clickIcon
					)
					for (i in 0..it.items.lastIndex step 2) {
						Row(
							modifier = Modifier,
							horizontalArrangement = Arrangement.Center,
							verticalAlignment = CenterVertically
						) {
							for (offset in 0..1) {
								CardScope(
									item = it.items[i + offset],
									modifier = Modifier.weight(1f, fill = false),
									scope = scope,
									imageBitmapCreator = viewModel::getBitmap,
									onClick = viewModel::clickIcon
								)
							}
						}
					}
				}
			}
		}
		BottomButtons(
			modifier = Modifier
				.fillMaxWidth()
				.requiredHeight(40.dp)
				.padding(bottom = 2.dp)
		)
	}

}


@Composable
fun BackGroundImage(path:String, viewModel: CardScreenViewModel) {
	val bgImage by produceState<ImageBitmap?>(initialValue = null) {
		value = viewModel.getBitmap(path)
	}
	if (bgImage != null) Image(
		bitmap = bgImage!!,
		contentDescription = null,
		modifier = Modifier.fillMaxSize(),
		contentScale = ContentScale.FillBounds
	)
}



@Composable
fun VolumeBar(volume: Float, onVolumeChanged: (Float) -> Unit, modifier: Modifier) {
	Box() {

	}
	IconButton(onClick = { /*TODO*/ }) {
		Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "")
	}
	var volume by remember { mutableStateOf(volume) }
	Slider(
		value = volume,
		modifier = modifier,
		valueRange = 0f..1f,
		onValueChange = {
			onVolumeChanged(it)
			volume = it
		}
	)
}

@Composable
fun ShowMenuDialog(show:MutableState<Boolean>) {
	if (show.value){
		DialogWrapper(
			modifier = Modifier
				.fillMaxWidth()
				.height(400.dp),
			onDismissRequest = { show.value = false }
		) {
			Menu()
		}
	}
}

@Composable
fun ShowMessage(message: MutableState<Int>) {
	val toast = remember { mutableStateOf<Toast?>(null) }
	if (message.value != -1) {
		val ctx = LocalContext.current
		SideEffect {
			toast.value?.cancel()
			val toastInstance = Toast.makeText(ctx, ctx.getText(message.value), Toast.LENGTH_SHORT)
			toast.value = toastInstance
			toastInstance.show()
			message.value = -1
		}
	}
}

@Composable
fun BottomButtons(modifier: Modifier = Modifier, viewModel: CardScreenViewModel = hiltViewModel()) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.Center
	) {
		val screenState by viewModel.state
		BottomIconButton(
			icon = Icons.Default.HelpOutline,
			bgColor = if (screenState == ScreenState.QUESTION) LocalContentColor.current.copy(
				alpha = LocalContentAlpha.current,
				red = 0f,
				green = 0.7f,
				blue = 0f
			) else MaterialTheme.colors.secondary,
			modifier = Modifier.weight(1f, false),
			onClick = viewModel::pressQuest
		)
		Spacer(modifier = Modifier.width(50.dp))
		BottomIconButton(
			icon = Icons.Default.Replay,
			bgColor = MaterialTheme.colors.secondary,
			modifier = Modifier.weight(1f, false),
			onClick = viewModel::pressRepeat
		)
		Spacer(modifier = Modifier.width(50.dp))
		BottomIconButton(
			icon = Icons.Default.InsertEmoticon,
			bgColor = if (screenState == ScreenState.DEFAULT)
				LocalContentColor.current.copy(
					alpha = LocalContentAlpha.current,
					red = 0f,
					green = 0.7f,
					blue = 0f
				)
			else
				MaterialTheme.colors.secondary,
			modifier = Modifier.weight(1f, false),
			onClick = viewModel::pressMan
		)
	}
}

@Composable
fun BottomIconButton(
	icon: ImageVector,
	bgColor: Color,
	modifier: Modifier = Modifier,
	contentDescription:String? = null,
	onClick: () -> Unit
) {
	IconButton(
		modifier = modifier
			.aspectRatio(1.5f)
			.border(2.dp, color = Color(0xFF_3787A1), MaterialTheme.shapes.medium)
			.background(
				color = bgColor,
				shape = MaterialTheme.shapes.medium
			),
		onClick = onClick
	) {
		Icon(
			imageVector = icon,
			modifier = Modifier
				.fillMaxSize()
				.padding(2.dp),
			contentDescription = contentDescription,
			tint = MaterialTheme.colors.onSecondary
		)
	}
}




@Composable
fun CardScope(item:Item, modifier: Modifier, scope:CoroutineScope, imageBitmapCreator: suspend (String) -> ImageBitmap, onClick: (Item) -> Unit) {
	var scale by remember { mutableStateOf(1f) }
	val animateScale by animateFloatAsState(
		targetValue = scale,
		animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
	)
	CardSurface(
		modifier = modifier
			.scale(animateScale)
			.padding(1.dp)
			.aspectRatio(1f)
	) {
		val imageBitmap by produceState<ImageBitmap?>(initialValue = null) {
			value = imageBitmapCreator(item.img)
		}
		if (imageBitmap != null) {
			Image(
				bitmap = imageBitmap!!,
				contentDescription = null,
				modifier = animationModifier(Modifier)
					.fillMaxSize()
					.clickable {
						scope.launch {
							scale = 1.2f
							delay(50)
							scale = 1f
						}
						onClick(item)
					}
			)

		}
	}
}



@Composable
fun CardSurface(
	modifier: Modifier,
	content: @Composable () -> Unit
) {
	val color = remember {Color(0xFF_3787A1)}//todo перенести
	Surface(
		modifier = modifier
			.padding(0.dp),
		elevation = 10.dp,
		color = MaterialTheme.colors.secondary.copy(alpha = 0.8f),
		border = BorderStroke(1.dp,color),
		shape = MaterialTheme.shapes.medium,
		content = content
	)
}


@Composable
fun animationModifier(
	modifier: Modifier,
	animationSpec: AnimationSpec<Dp> = spring(dampingRatio = Spring.DampingRatioHighBouncy),
):Modifier {
	var offset by remember {
		mutableStateOf(500.dp)
	}
	val anim by animateDpAsState(
		targetValue = offset,
		animationSpec = animationSpec
	)
	LaunchedEffect(Unit) {
		offset = 0.dp
	}
	return modifier.offset(
		anim * if (Math.random() < 0.5f) -1 else 1,
		anim * if (Math.random() < 0.5f) -1 else 1
	)
}