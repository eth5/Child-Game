package pro.it_dev.childgame.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import pro.it_dev.childgame.domain.CardsKit
import pro.it_dev.childgame.domain.Item
import pro.it_dev.childgame.util.Resource
import pro.it_dev.childgame.util.fileToBitmap

@Composable
fun MainScreen(itemsPath:String, viewModel: MainScreenViewModel = hiltViewModel()) {




	val scaffoldState = rememberScaffoldState()
	Scaffold(
		scaffoldState = scaffoldState,
		backgroundColor = Color(0xFF_162055),
		modifier = Modifier
			.fillMaxSize()
	) {
		LaunchedEffect(key1 = itemsPath, block = {viewModel.loadCards(itemsPath)})

		val screenData by remember {
			viewModel.cardsKit
		}
		Box(
			modifier = Modifier.fillMaxSize(),
			contentAlignment = Alignment.TopCenter
		) {
			val image by rememberUpdatedState(
				newValue = LocalContext.current.assets.fileToBitmap("img/logo.png")!!
					.asImageBitmap()
			)
			Image(
				bitmap = image,
				contentDescription = "logo",
				contentScale = ContentScale.FillBounds,
				modifier = Modifier
					.padding(top = 4.dp)
					.size(150.dp, 30.dp)
			)
			Column(horizontalAlignment = Alignment.CenterHorizontally) {

				Box(modifier = Modifier.weight(1f)) {
					ScreenDataStateWrapper(cardsKit = screenData)
				}

				BottomButtons(
					modifier = Modifier
						.fillMaxWidth()
						.requiredHeight(35.dp)
						.padding(bottom = 2.dp)
				)
			}
		}
		ShowMessage(messageState = viewModel.popUpMessage, scaffoldState = scaffoldState)

	}
}
@Composable
fun ShowMessage(messageState: MutableState<String>, scaffoldState: ScaffoldState) {
	var text by remember { messageState }
	if (text.isNotEmpty()){
		LaunchedEffect(key1 = "") {
			scaffoldState.snackbarHostState.showSnackbar(text)
			text = ""
		}
	}

}
@Composable
fun BottomButtons(modifier: Modifier = Modifier, viewModel: MainScreenViewModel = hiltViewModel()) {
	Row(
		modifier = modifier,
		horizontalArrangement = Arrangement.Center
	) {
		val quest by rememberUpdatedState(
			newValue = LocalContext.current.assets.fileToBitmap("img/vopros.png")!!.asImageBitmap()
		)
		Image(
			bitmap = quest,
			contentDescription = null,
			modifier = Modifier
				.weight(1f)
				.clickable(onClick = viewModel::pressQuest)
		)
		val repeat by rememberUpdatedState(
			newValue = LocalContext.current.assets.fileToBitmap("img/repeat.png")!!.asImageBitmap()
		)
		Image(
			bitmap = repeat,
			contentDescription = null,
			modifier = Modifier
				.weight(1f)
				.clickable(onClick = viewModel::pressQuest)
		)
		val man by rememberUpdatedState(
			newValue = LocalContext.current.assets.fileToBitmap("img/chuvak.png")!!.asImageBitmap()
		)
		Image(
			bitmap = man, contentDescription = null, modifier = Modifier
				.weight(1f)
				.clickable(onClick = viewModel::pressMan)
		)
	}
}


@Composable
fun ScreenDataStateWrapper(cardsKit: Resource<CardsKit>, viewModel: MainScreenViewModel = hiltViewModel()) {
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
			CardItems(cardsKit = cardsKit.data!!, imageBitmapCreator = viewModel::getBitmap, viewModel::clickIcon)
		}
	}
}

@Composable
fun CardItems(cardsKit: CardsKit, imageBitmapCreator:suspend (String)->ImageBitmap, onClick: (Item) -> Unit) {
	Column(
		modifier = Modifier.fillMaxSize(),
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Row(
			modifier = Modifier
				.border(1.dp, Color.Red)
		) {
			cardsKit.list.forEach {
				Column(
					modifier = Modifier
						.weight(1f, fill = false)
						.padding(2.dp),
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center
				) {
					val headImg by produceState<ImageBitmap?>(initialValue = null){ value = imageBitmapCreator(it.bigItem.img) }
					if(headImg!=null){
						Image(
							bitmap = headImg!!,
							contentDescription = null,
							modifier = Modifier
								.aspectRatio(1f)
								.clickable { onClick(it.bigItem) }
						)
					}

					Spacer(modifier = Modifier.height(5.dp))

					for (i in 0..it.items.lastIndex step 2) {
						Row(
							modifier = Modifier
								.padding(1.dp)
								.weight(1f, fill = false),
							horizontalArrangement = Arrangement.Center
						) {
							for (offset in 0..1) {
								val item by remember { derivedStateOf { it.items[i + offset] } } //todo ????
								val imageBitmap by produceState<ImageBitmap?>(initialValue = null){ value = imageBitmapCreator(item.img) }
								if (imageBitmap!=null){
									Image(
										bitmap = imageBitmap!!,
										contentDescription = null,
										modifier = Modifier
											.weight(1f, fill = false)
											.clickable { onClick(item) }
									)
								}

							}
						}
					}
				}
			}
		}
	}
}