package pro.it_dev.childgame.presentation.screen

import android.graphics.ColorSpace
import android.view.Gravity
import android.widget.Toast
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.colorspace.ColorSpaces
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pro.it_dev.childgame.domain.CardsKit
import pro.it_dev.childgame.domain.Item
import pro.it_dev.childgame.util.Resource
import pro.it_dev.childgame.util.fileToBitmap

@Composable
fun CardsScreen(itemsPath: String, viewModel: MainScreenViewModel = hiltViewModel()) {

	LaunchedEffect(key1 = itemsPath, block = { viewModel.loadCards(itemsPath) })

	val screenData by remember {
		viewModel.cardsKit
	}
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.TopCenter
	) {

		Text(
			text = buildAnnotatedString {
				var color = Color.Blue
				"АЗБУКВАРИК".forEach {
					color = if (color == Color.Red) Color.Blue else Color.Red
					withStyle(style = SpanStyle(color = color)) {
						append(it)
					}

				}
			},
			fontSize = 35.sp,
			fontWeight = FontWeight.Bold,
			modifier = Modifier.border(1.dp, Color.Red)
		)

		Column(horizontalAlignment = Alignment.CenterHorizontally) {
			Spacer(modifier = Modifier.height(20.dp))
			Box(modifier = Modifier.weight(1f)) {
				ScreenCardsStateWrapper(cardsKit = screenData)
			}

			BottomButtons(
				modifier = Modifier
					.fillMaxWidth()
					.requiredHeight(40.dp)
					.padding(bottom = 2.dp)
			)
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
fun BottomButtons(modifier: Modifier = Modifier, viewModel: MainScreenViewModel = hiltViewModel()) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.Center
	) {
		val state by remember {
			viewModel.state
		}
		IconButton(
			modifier = Modifier
				.aspectRatio(1.3f)
				.weight(1f, false)
				.border(2.dp, color = Color.Black, MaterialTheme.shapes.medium)
				.background(
					if (state == ScreenState.QUESTION) LocalContentColor.current.copy(
						alpha = LocalContentAlpha.current,
						red = 0f,
						green = 0.4f,
						blue = 0f
					) else MaterialTheme.colors.secondary,
					MaterialTheme.shapes.medium
				),
			onClick = viewModel::pressQuest
		) {
			Icon(
				imageVector = Icons.Default.HelpOutline,
				modifier = Modifier.fillMaxSize(),
				contentDescription = "",
				tint = MaterialTheme.colors.onSecondary
			)
		}
		Spacer(modifier = Modifier.width(50.dp))
		IconButton(
			modifier = Modifier
				.aspectRatio(1.3f)
				.weight(1f, false)
				.border(2.dp, color = Color.Black, MaterialTheme.shapes.medium)
				.background(MaterialTheme.colors.secondary, MaterialTheme.shapes.medium),
			onClick = viewModel::pressRepeat
		) {
			Icon(
				imageVector = Icons.Default.Replay,
				modifier = Modifier.fillMaxSize(),
				contentDescription = "",
				tint = MaterialTheme.colors.onSecondary
			)
		}

		Spacer(modifier = Modifier.width(50.dp))
		IconButton(
			modifier = Modifier
				.aspectRatio(1.3f)
				.weight(1f, false)
				.border(2.dp, color = Color.Black, MaterialTheme.shapes.medium)
				.background(
					color = if (state == ScreenState.DEFAULT)
						LocalContentColor.current.copy(
							alpha = LocalContentAlpha.current,
							red = 0f,
							green = 0.4f,
							blue = 0f
						)
					else
						MaterialTheme.colors.secondary,
					shape = MaterialTheme.shapes.medium
				),
			onClick = viewModel::pressMan
		) {
			Icon(
				imageVector = Icons.Default.InsertEmoticon,
				modifier = Modifier.fillMaxSize(),
				contentDescription = "",
				tint = MaterialTheme.colors.onSecondary
			)
		}
	}
}


@Composable
fun ScreenCardsStateWrapper(
	cardsKit: Resource<CardsKit>,
	viewModel: MainScreenViewModel = hiltViewModel()
) {
	when (cardsKit) {
		is Resource.Loading -> {
			Box(
				modifier = Modifier.fillMaxSize(),
				contentAlignment = Alignment.Center
			) { CircularProgressIndicator() }
		}
		is Resource.Error -> {
			Box(
				modifier = Modifier.fillMaxSize(),
				contentAlignment = Alignment.Center
			) { Text(text = cardsKit.message ?: "Unknown error!", color = Color.Red) }
		}
		is Resource.Success -> {
			CardItems(
				cardsKit = cardsKit.data!!,
				imageBitmapCreator = viewModel::getBitmap,
				viewModel::clickIcon
			)
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
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Row(
			modifier = Modifier
		) {
			cardsKit.itemsGroupList.forEach {
				Column(
					modifier = Modifier
						.weight(1f, fill = false)
						.padding(1.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center
				) {
					val headImg by produceState<ImageBitmap?>(initialValue = null) {
						value = imageBitmapCreator(it.bigItem.img)
					}
					if (headImg != null) {
						AnimatedOnClickImage(
							image = headImg!!,
							scope = scope,
							modifier = Modifier
								.aspectRatio(1f)
								.size(10.dp)
						) { onClick(it.bigItem) }
					}

					Spacer(modifier = Modifier.height(5.dp))

					for (i in 0..it.items.lastIndex step 2) {
						Row(
							modifier = Modifier
								.padding(1.dp)
								.weight(1f, fill = false),
							horizontalArrangement = Arrangement.Center,
							verticalAlignment = CenterVertically
						) {
							for (offset in 0..1) {
								MyCard(
									modifier = Modifier
										//.requiredSize(50.dp)
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
										) { onClick(item) }
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
			.background(MaterialTheme.colors.secondary, MaterialTheme.shapes.medium)
			.border(2.dp, Color.Black, MaterialTheme.shapes.medium)
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
		animationSpec = animationSpec// spring(dampingRatio = Spring.DampingRatioHighBouncy)
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
				scope.launch {
					offset = 5.dp
					delay(100)
					offset = 0.dp
				}
			}
	)
}