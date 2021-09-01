package pro.it_dev.childgame.repository

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.asImageBitmap
import pro.it_dev.childgame.domain.CardsKit
import pro.it_dev.childgame.domain.Item
import pro.it_dev.childgame.domain.ItemsGroup
import pro.it_dev.childgame.util.Resource


class LocalRepository(private val fm: IFileManager) : IRepository {

	override suspend fun getScreenData(itemsPath: String): Resource<CardsKit> {
		return Resource.Success(getCardsFrom(itemsPath))
	}

	private suspend fun getCardsFrom(kitPath: String): CardsKit {
		val itemFiles = fm.getFileNamesInPath("$kitPath/items").data!!

		return CardsKit(
			kitPath,
			mutableListOf<ItemsGroup>().apply {
				itemFiles.forEach {
					add(createVerticalLineItemsFromPath("$kitPath/items/$it", fm))
				}
			}
		)
	}

	private suspend fun createVerticalLineItemsFromPath(
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