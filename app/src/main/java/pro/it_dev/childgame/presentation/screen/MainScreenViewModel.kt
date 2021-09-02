package pro.it_dev.childgame.presentation.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.it_dev.childgame.domain.CardsKit
import pro.it_dev.childgame.domain.Item
import pro.it_dev.childgame.presentation.android_bitmap_factory.IBitmapFactory
import pro.it_dev.childgame.presentation.fxplayer.IFxPlayer
import pro.it_dev.childgame.repository.IRepository
import pro.it_dev.childgame.util.Resource
import javax.inject.Inject

enum class ScreenState{
	DEFAULT,
	QUESTION,
	MUSIC
}

@HiltViewModel
class MainScreenViewModel @Inject constructor(
	private val repository: IRepository,
	private val fxPlayer: IFxPlayer,
	private val bitmapFactory: IBitmapFactory
	) : ViewModel() {

	private val _cardsKit = mutableStateOf<Resource<CardsKit>>(Resource.Loading())
	val cardsKit:State<Resource<CardsKit>> get() = _cardsKit

	val popUpMessage = mutableStateOf("")

	val state = mutableStateOf(ScreenState.DEFAULT)

	suspend fun loadCards(itemsPath: String){
		_cardsKit.value = repository.getCardsKit(itemsPath)

	}

	suspend fun getBitmap(file: String):ImageBitmap{
		// delay( 0.rangeTo(100L).random() )
		return bitmapFactory.create(file)
	}

	fun clickIcon(item: Item){
		if (item.fx.isNotEmpty())fxPlayer.play(item.fx.random())
	}

	fun pressQuest(){
		val riddles = cardsKit.value.data!!.riddlesGroupList.random()
		fxPlayer.play(riddles.fxPath)
	}
	fun pressRepeat(){

	}
	fun pressMan(){

	}
}