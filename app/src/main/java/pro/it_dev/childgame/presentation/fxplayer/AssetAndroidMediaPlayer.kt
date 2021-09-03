package pro.it_dev.childgame.presentation.fxplayer

import android.content.Context
import android.media.MediaPlayer
import java.lang.IllegalStateException

class AssetAndroidMediaPlayer(private val context: Context): IFxPlayer {
	private val mediaPlayer: MediaPlayer = MediaPlayer().also {
		it.setOnPreparedListener { mp->
			mp.start()
		}
		it.setOnCompletionListener {
			endPlayCallback?.invoke()
		}
	}
	private var endPlayCallback:(()->Unit)? = null
	private var isDisposed = false

	override val isPlaying: Boolean
		get() = mediaPlayer.isPlaying

	override fun setVolume(volume: Float) {
		mediaPlayer.setVolume(volume,volume)
	}

	override fun play(file: String, onEndPlay:(()->Unit)?) {
		if (isDisposed) throw IllegalStateException("MediaPlayer isDisposed!")

		mediaPlayer.reset()
		endPlayCallback = onEndPlay

		context.assets.openFd(file).use {
			mediaPlayer.setDataSource(it.fileDescriptor, it.startOffset, it.declaredLength)
			mediaPlayer.prepare()
		}
	}

	override fun stop() {
		if (mediaPlayer.isPlaying) mediaPlayer.stop()
	}

	override fun dispose() {
		isDisposed = true
		mediaPlayer.reset()
		mediaPlayer.release()
	}
}