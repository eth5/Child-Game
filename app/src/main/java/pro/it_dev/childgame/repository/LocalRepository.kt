package pro.it_dev.childgame.repository

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import pro.it_dev.childgame.domain.CardsKit
import pro.it_dev.childgame.domain.Item
import pro.it_dev.childgame.domain.ItemsGroup
import pro.it_dev.childgame.domain.riddles.Riddle
import pro.it_dev.childgame.domain.riddles.RiddlesGroup
import pro.it_dev.childgame.util.Resource


class LocalRepository(private val fm: IFileManager) : IRepository {

	override suspend fun getCardsKit(itemsPath: String): Resource<CardsKit> {
		return Resource.Success(getCardsFrom(itemsPath))
	}

	private suspend fun getCardsFrom(kitPath: String): CardsKit {
		val itemFiles = fm.getFileNamesInPath("$kitPath/items").data!!.shuffled()

		return CardsKit(
			kitPath = kitPath,
			itemsGroupList = mutableListOf<ItemsGroup>().apply {
				itemFiles.forEach {
					add(
						createItemsGroupFrom("$kitPath/items/$it", fm)
					)
				}
			},
			riddlesGroupList = createKitRiddlesGroupFrom(kitPath, fm)
		)
	}

	private fun createKitRiddlesGroupFrom(
		cardKitPath: String,
		fm: IFileManager
	): List<RiddlesGroup> {
		val riddlesKitDataPath = "$cardKitPath/riddles"

		return runBlocking(Dispatchers.IO) { fm.getFileNamesInPath(riddlesKitDataPath).data!! }
			.map {
				val riddlesDataFromFile = runBlocking(Dispatchers.IO) {
					fm.getInputStream(path = "$riddlesKitDataPath/$it/title.txt").data!!.bufferedReader()
						.use { it.readLines() }
				}.toMutableList()
				val riddleGroupName = riddlesDataFromFile.removeAt(0)

				val riddlesList = mutableListOf<Riddle>()
				riddlesDataFromFile.forEach {
					riddlesList.addAll(
						createRiddlesOfItem("$cardKitPath/items/$it","riddles", fm)
					)
				}
				RiddlesGroup(
					info = RiddlesGroup.GroupInfo(
						name = riddleGroupName,
						startFxPath = "$riddlesKitDataPath/$it/title.ogg",
						errorFxPath = "$cardKitPath/wrong.ogg",
						),
					riddlesList = riddlesList
				)
			}

	}


	private fun createRiddlesOfItem(pathToItem: String, riddlesDir:String, fm: IFileManager): List<Riddle> {
		return mutableListOf<Riddle>().apply {

			val fullPath = "$pathToItem/$riddlesDir"
			val files = runBlocking (Dispatchers.IO){ fm.getFileNamesInPath(fullPath).data!! }

			files.forEach {
				add(
					Riddle(
						validAnswer = pathToItem,
						questionFx = "$fullPath/$it/q.ogg",
						answerFx = "$fullPath/$it/a.ogg"
					)
				)
			}
		}
	}

	private suspend fun createItemsGroupFrom(
		path: String,
		fm: IFileManager
	): ItemsGroup {
		val files = fm.getFileNamesInPath(path).data!!.filter { it != "head" }

		val small = mutableListOf<Item>().apply {
			files.forEach {
				val pathToItemData = "$path/$it"
				val img = fm.getInputStream("$pathToItemData/img.png").data!!.use {
					BitmapFactory.decodeStream(it)
				}.asImageBitmap()
				add(
					Item(
						name = pathToItemData,
						img = "$pathToItemData/img.png",
						fx = listOf("$pathToItemData/fx.ogg")
					)
				)
			}
		}
		val big =
			fm.getInputStream("$path/head/img.png").data!!.use { BitmapFactory.decodeStream(it) }
				.asImageBitmap()
		val fxs = fm.getFileNamesInPath("$path/head/music").data?.map { "$path/head/music/$it" }
			?: emptyList()
		return ItemsGroup(
			Item(name = "$path/head/img.png", img = "$path/head/img.png", fx = fxs),
			small
		)
	}


}