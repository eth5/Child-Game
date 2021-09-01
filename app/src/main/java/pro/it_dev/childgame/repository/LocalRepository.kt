package pro.it_dev.childgame.repository

import android.content.res.AssetManager
import androidx.compose.ui.graphics.asImageBitmap
import pro.it_dev.childgame.domain.CardsKit
import pro.it_dev.childgame.domain.Item
import pro.it_dev.childgame.domain.ItemsGroup
import pro.it_dev.childgame.util.Resource
import pro.it_dev.childgame.util.fileToBitmap

//todo переписать AssetManager на абстракцию
class LocalRepository(private val am: AssetManager) : IRepository {

	override fun getScreenData(itemsPath: String): Resource<CardsKit> {
		return Resource.Success(getCardsFrom(itemsPath))
	}

	private fun getCardsFrom(kitPath: String): CardsKit {

		val itemFiles = am.list("$kitPath/items") ?: throw java.lang.NullPointerException(kitPath)

		return CardsKit(
			kitPath,
			mutableListOf<ItemsGroup>().apply {
				itemFiles.forEach {
					add( createVerticalLineItemsFromPath("$kitPath/items/$it", am) )
				}
			}
		)
	}

	private fun createVerticalLineItemsFromPath(
		path: String,
		am: AssetManager
	): ItemsGroup {
		val files = am.list(path)!!.filter { it != "head" }

		val small = mutableListOf<Item>().apply {
			files.forEach {
				val pathToItemData = "$path/$it"
				val img = am.fileToBitmap("$pathToItemData/img.png")?.asImageBitmap()
					?: throw NullPointerException(pathToItemData)
				// add( Item(name  = pathToItemData, img = img, fx = "$pathToItemData/fx.ogg"))
				add( Item(name  = pathToItemData, img = "$pathToItemData/img.png", fx = listOf("$pathToItemData/fx.ogg")))
			}
		}
		val big = am.fileToBitmap("$path/head/img.png")?.asImageBitmap()
			?: throw NullPointerException("$path/head/img.png")
		val fxs = am.list("$path/head/music")?.map { "$path/head/music/$it" } ?: emptyList()
		return ItemsGroup(Item(name = "$path/head/img.png", img = "$path/head/img.png", fx = fxs), small)
	}


}