package pro.it_dev.childgame.repository

import android.content.res.AssetManager
import pro.it_dev.childgame.util.Resource
import java.io.InputStream

class AssetFileManager(private val am: AssetManager): IFileManager {

	override suspend fun getFileNamesInPath(path: String): Resource<List<String>> {
		val list = am.list(path)?.toList() ?: emptyList() //todo need error handle
		return Resource.Success(list)
	}

	override suspend fun getInputStream(path: String): Resource<InputStream> {
		val inputStream = am.open(path) //todo need error handle
		return Resource.Success(inputStream)
	}

}