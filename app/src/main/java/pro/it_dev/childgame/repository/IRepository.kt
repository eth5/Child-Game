package pro.it_dev.childgame.repository

import pro.it_dev.childgame.domain.CardsKit
import pro.it_dev.childgame.util.Resource

interface IRepository {
	suspend fun getCardsKit(itemsPath: String):Resource<CardsKit>
}