package pro.it_dev.childgame.presentation.android_bitmap_factory

import androidx.compose.ui.graphics.ImageBitmap

interface IBitmapFactory {
	suspend fun create(file: String):ImageBitmap
}