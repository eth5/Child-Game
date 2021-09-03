package pro.it_dev.childgame.presentation.fxplayer

interface IFxPlayer {
	val isPlaying:Boolean
	fun play(file: String, onEndPlay:(()->Unit)?)
	fun setVolume(volume: Float)
	fun stop()
	fun dispose()
}