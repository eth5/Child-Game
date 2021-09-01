package pro.it_dev.childgame.presentation.android_bitmap_factory

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import pro.it_dev.childgame.repository.IFileManager

class AssetsFilesBitmapFactory(private val am: IFileManager):IBitmapFactory {
	override suspend fun create(file: String): ImageBitmap {
		return  am.getInputStream(file).data!!.use {
			BitmapFactory.decodeStream(it)
		}.asImageBitmap()
	}
}