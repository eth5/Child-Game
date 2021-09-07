package pro.it_dev.childgame.presentation.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import pro.it_dev.childgame.R
import pro.it_dev.childgame.domain.CardsKit
import pro.it_dev.childgame.domain.Item
import pro.it_dev.childgame.domain.riddles.RiddleGroupLogicHandler
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
class CardScreenViewModel @Inject constructor(
	private val repository: IRepository,
	private val fxPlayer: IFxPlayer,
	private val bitmapFactory: IBitmapFactory
	) : ViewModel() {

	var volume:Float = 1f
		private set

	private lateinit var riddles: RiddleGroupLogicHandler

	private val _cardsKit = mutableStateOf<Resource<CardsKit>>(Resource.Loading())
	val cardsKit:State<Resource<CardsKit>> get() = _cardsKit

	val popUpMessage = mutableStateOf(-1)
	private val _state = mutableStateOf(ScreenState.DEFAULT)
	val state :State<ScreenState> get() = _state

	fun setVolume(volume:Float){
		this.volume = volume
		fxPlayer.setVolume(volume)
	}

	suspend fun loadCards(itemsPath: String){
		val result = repository.getCardsKit(itemsPath)
		//todo error handler
		riddles = RiddleGroupLogicHandler(result.data!!.riddlesGroupList)
		_cardsKit.value = result
	}

	suspend fun getBitmap(file: String):ImageBitmap{
		// delay( 0.rangeTo(100L).random() )
		return bitmapFactory.create(file)
	}

	fun clickIcon(item: Item, state: State<ScreenState> = this._state){

		if (fxPlayer.isPlaying && state.value != ScreenState.MUSIC){
			popUpMessage.value = R.string.do_not_rush
			return
		}
		when(state.value){
			ScreenState.DEFAULT -> playItemFx(item = item, fxPlayer = fxPlayer)
			ScreenState.QUESTION -> riddleHandler(item = item, fxPlayer = fxPlayer)
		}
	}

	fun pressQuest(state: MutableState<ScreenState> = this._state){
		if (state.value != ScreenState.QUESTION){
			state.value = ScreenState.QUESTION
		}
		getNextRiddleGroup(riddles, fxPlayer)
	}


	fun pressRepeat(state: State<ScreenState> = this._state){
		if (fxPlayer.isPlaying || state.value != ScreenState.QUESTION) return
		fxPlayer.play(riddles.getCurrentRiddle().questionFx, null)
	}

	fun pressMan(state: MutableState<ScreenState> = this._state){
		fxPlayer.stop()
		state.value = ScreenState.DEFAULT
	}

	private fun playItemFx(item: Item, fxPlayer: IFxPlayer){
		if (item.fx.isNotEmpty())fxPlayer.play(item.fx.random(), null)
	}

	private fun getNextRiddleGroup(riddles: RiddleGroupLogicHandler, fxPlayer: IFxPlayer){

		val info = riddles.nextGroup()
		fxPlayer.play(info.startFxPath){
			fxPlayer.play(riddles.getCurrentRiddle().questionFx, null)
		}
	}

	private fun riddleHandler(item: Item, fxPlayer: IFxPlayer){
		val fx = if (riddles.checkAnswer(item.name)){
			riddles.getCurrentRiddle().answerFx
		}else{
			riddles.getGroupIngo().errorFxPath
		}
		fxPlayer.play(fx){
			if (riddles.nextRiddle()) {
				fxPlayer.play(riddles.getCurrentRiddle().questionFx, null)
			}
			else {
				getNextRiddleGroup(riddles, fxPlayer)
			}

		}
	}
}