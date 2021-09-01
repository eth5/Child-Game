package pro.it_dev.childgame.repository

import pro.it_dev.childgame.util.Resource
import java.io.InputStream

interface IFileManager{
	suspend fun getFileNamesInPath(path: String): Resource<List<String>>
	suspend fun getInputStream(path: String): Resource<InputStream>
}