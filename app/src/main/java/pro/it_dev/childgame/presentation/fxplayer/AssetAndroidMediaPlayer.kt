package pro.it_dev.childgame.presentation.fxplayer

import android.content.Context
import android.media.MediaPlayer
import java.lang.IllegalStateException

class AssetAndroidMediaPlayer(private val context: Context): IFxPlayer {
	private val mediaPlayer: MediaPlayer = MediaPlayer().also {
		it.setOnPreparedListener { mp->
			mp.start()
		}
	}
	private var isDisposed = false
	override fun play(file: String) {
		if (isDisposed) throw IllegalStateException("MediaPlayer isDisposed!")
		mediaPlayer.reset()
		context.assets.openFd(file).use {
			mediaPlayer.setDataSource(it.fileDescriptor, it.startOffset, it.declaredLength)
			mediaPlayer.prepare()
		}
	}

	override fun stop() {
		mediaPlayer.stop()
	}

	override fun dispose() {
		isDisposed = true
		mediaPlayer.reset()
		mediaPlayer.release()
	}
}