package pro.it_dev.childgame.util

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.IOException

fun AssetManager.fileToBitmap(fileName: String): Bitmap? {
	return try {
		with(open(fileName)) {
			BitmapFactory.decodeStream(this)
		}
	} catch (e: IOException) {
		null
	}
}