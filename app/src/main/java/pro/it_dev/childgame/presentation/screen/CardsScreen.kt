package pro.it_dev.childgame.presentation.screen

import android.view.Gravity
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.TextDelegate
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
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pro.it_dev.childgame.domain.CardsKit
import pro.it_dev.childgame.domain.Item
import pro.it_dev.childgame.presentation.dialogs.DialogWrapper
import pro.it_dev.childgame.presentation.dialogs.menu.Menu
import pro.it_dev.childgame.presentation.hint.popUpTest
import pro.it_dev.childgame.presentation.screen.jumping_buttons.ColorText
import pro.it_dev.childgame.presentation.util.asStateEvent
import pro.it_dev.childgame.util.Resource

@Composable
fun CardsScreen(itemsPath: String, viewModel: CardScreenViewModel = hiltViewModel()) {

	LaunchedEffect(key1 = itemsPath, block = { viewModel.loadCards(itemsPath) })

	val screenData by remember {
		viewModel.cardsKit
	}
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.TopCenter
	) {
		val headImg by produceState<ImageBitmap?>(initialValue = null) {
			value = viewModel.getBitmap(screenData.data!!.kitPath + "/bg.jpg")
		}
		if (headImg != null) Image(
			bitmap = headImg!!,
			contentDescription = null,
			modifier = Modifier.fillMaxSize(),
			contentScale = ContentScale.FillBounds
		)
		Column(
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
								viewModel.aboutDialog.value = ""
							}
						)
					}
				)
			}
			

			ScreenCardsStateWrapper(modifier = Modifier.weight(1f), cardsKit = screenData)

			BottomButtons(
				modifier = Modifier
					.fillMaxWidth()
					.requiredHeight(40.dp)
					.padding(bottom = 2.dp)
			)
		}
	}

	viewModel.aboutDialog.asStateEvent {
		DialogWrapper(
			modifier = Modifier
				.fillMaxWidth()
				.height(400.dp)
				//.fillMaxHeight(0.8f)
			,
			onDismissRequest = { it.value = null }
		) {
			// Config() { it.value = null }
			// AboutScreen { it.value = null }
			Menu()
		}
	}

	ShowMessage(messageState = viewModel.popUpMessage)
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
fun ShowMessage(messageState: MutableState<Int>) {
	var textRes by remember { messageState }
	val ctx = LocalContext.current
	if (textRes != -1) {
		LaunchedEffect(key1 = "") {
			//scaffoldState.snackbarHostState.showSnackbar(text)
			Toast.makeText(ctx, ctx.getText(textRes), Toast.LENGTH_SHORT).also {
				it.setGravity(Gravity.TOP, 0, 0)
			}.show()

			textRes = -1
		}
	}
}

@Composable
fun BottomButtons(modifier: Modifier = Modifier, viewModel: CardScreenViewModel = hiltViewModel()) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.Center
	) {
		val state by remember {
			viewModel.state
		}
		BottomIconButton(
			icon = Icons.Default.HelpOutline,
			bgColor = if (state == ScreenState.QUESTION) LocalContentColor.current.copy(
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
			bgColor = if (state == ScreenState.DEFAULT)
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
	onClick: () -> Unit
) {
	IconButton(
		modifier = modifier
			.aspectRatio(1.3f)
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
			contentDescription = "",
			tint = MaterialTheme.colors.onSecondary
		)
	}
}


@Composable
fun ScreenCardsStateWrapper(
	cardsKit: Resource<CardsKit>,
	modifier: Modifier,
	viewModel: CardScreenViewModel = hiltViewModel()
) {
	Box(
		modifier = modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		when (cardsKit) {
			is Resource.Loading -> CircularProgressIndicator()
			is Resource.Error -> Text(
				text = cardsKit.message ?: "Unknown error!",
				color = Color.Red
			)
			is Resource.Success -> {
				CardItems(
					cardsKit = cardsKit.data!!,
					imageBitmapCreator = viewModel::getBitmap,
					viewModel::clickIcon
				)
			}
		}
	}

}


@Composable
fun CardItems(
	cardsKit: CardsKit,
	imageBitmapCreator: suspend (String) -> ImageBitmap,
	onClick: (Item) -> Unit
) {
	val scope = rememberCoroutineScope()
	Row(
		modifier = Modifier.fillMaxSize(),
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
				val headImg by produceState<ImageBitmap?>(initialValue = null) {
					value = imageBitmapCreator(it.bigItem.img)
				}
				MyCard(
					modifier = Modifier
						.padding(1.dp)
						.weight(1f, fill = false)
						.aspectRatio(1f)

				) {
					if (headImg != null) {
						AnimatedOnClickImage(
							image = headImg!!,
							scope = scope,
							modifier = Modifier
								.aspectRatio(1f)
								.size(10.dp)
						) { onClick(it.bigItem) }
					}
				}

				//Spacer(modifier = Modifier.height(5.dp))

				for (i in 0..it.items.lastIndex step 2) {
					Row(
						modifier = Modifier,//.weight(1f, fill = false),
						horizontalArrangement = Arrangement.Center,
						verticalAlignment = CenterVertically
					) {
						for (offset in 0..1) {
							var scale by remember { mutableStateOf(1f) }
							val animateScale by animateFloatAsState(
								targetValue = scale,
								animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy)
							)
							MyCard(
								modifier = Modifier
									.scale(animateScale)
									.padding(1.dp)
									.aspectRatio(1f)
									.weight(1f, fill = false)
							) {
								val item by remember { derivedStateOf { it.items[i + offset] } } //todo ????
								val imageBitmap by produceState<ImageBitmap?>(initialValue = null) {
									value = imageBitmapCreator(item.img)
								}
								if (imageBitmap != null) {
									AnimatedOnClickImage(
										image = imageBitmap!!,
										scope = scope,
										modifier = Modifier
											.padding(5.dp)
											.scale(animateScale)
									) {
										scope.launch {
											scale = 1.2f
											delay(50)
											scale = 1f
										}
										onClick(item)
									}
								}
							}
						}
					}
				}
			}
		}
	}
}

@Composable
fun MyCard(
	modifier: Modifier,
	content: @Composable BoxScope.() -> Unit
) {
	Box(
		modifier = modifier
			.background(
				MaterialTheme.colors.secondary.copy(alpha = 0.8f),
				MaterialTheme.shapes.medium
			)
			.border(1.dp, Color(0xFF_3787A1), MaterialTheme.shapes.medium)
			.padding(2.dp),
		contentAlignment = Alignment.Center,
		content = content
	)
}

@Composable
fun AnimatedOnClickImage(
	image: ImageBitmap,
	scope: CoroutineScope,
	modifier: Modifier,
	animationSpec: AnimationSpec<Dp> = spring(dampingRatio = Spring.DampingRatioHighBouncy),
	onClick: () -> Unit
) {
	var offset by remember {
		mutableStateOf(500.dp)
	}
	val anim by animateDpAsState(
		targetValue = offset,
		animationSpec = animationSpec
	)

	LaunchedEffect(key1 = "") {
		offset = 0.dp
	}
	Image(
		bitmap = image,
		contentDescription = null,
		modifier = modifier
			.offset(
				anim * if (Math.random() < 0.5f) -1 else 1,
				anim * if (Math.random() < 0.5f) -1 else 1
			)
			.clickable {
				onClick()
			}
	)
}