package pro.it_dev.childgame.presentation.screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import com.google.android.material.math.MathUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import pro.it_dev.childgame.domain.CardsKit
import pro.it_dev.childgame.domain.Item
import pro.it_dev.childgame.presentation.android_bitmap_factory.AssetsFilesBitmapFactory
import pro.it_dev.childgame.presentation.android_bitmap_factory.IBitmapFactory
import pro.it_dev.childgame.presentation.fxplayer.IFxPlayer
import pro.it_dev.childgame.repository.IRepository
import pro.it_dev.childgame.util.Resource
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
	private val repository: IRepository,
	private val fxPlayer: IFxPlayer,
	private val bitmapFactory: IBitmapFactory
	) : ViewModel() {

	private val _cards = mutableStateOf<Resource<CardsKit>>(Resource.Loading())
	val cardsKit:State<Resource<CardsKit>> get() = _cards

	val popUpMessage = mutableStateOf("")

	suspend fun loadCards(itemsPath: String){
		_cards.value = repository.getScreenData(itemsPath)

	}

	suspend fun getBitmap(file: String):ImageBitmap{
		// delay( 0.rangeTo(100L).random() )
		return bitmapFactory.create(file)
	}

	fun clickIcon(item: Item){
		if (item.fx.isNotEmpty())fxPlayer.play(item.fx.random())
	}

	fun pressQuest(){

	}
	fun pressRepeat(){

	}
	fun pressMan(){

	}
}