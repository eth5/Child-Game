package pro.it_dev.childgame.presentation.android_bitmap_factory

import android.content.res.AssetManager
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import pro.it_dev.childgame.util.fileToBitmap

//todo replace am to abstraction
class AssetsFilesBitmapFactory(private val am: AssetManager):IBitmapFactory {

	override suspend fun create(file: String): ImageBitmap {
		return am.fileToBitmap(file)?.asImageBitmap() ?: throw NullPointerException(file)
	}
}