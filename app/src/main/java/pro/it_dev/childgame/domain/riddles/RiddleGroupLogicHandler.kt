package pro.it_dev.childgame.domain.riddles

import pro.it_dev.childgame.util.RandomListWrapper

class RiddleGroupLogicHandler(riddles:List<RiddlesGroup>) {
	private val list = RandomListWrapper(riddles)

	private var currentRiddleGroup: RiddlesGroup = list.getCurrent()
	set(value) {
		field = value
		currentRiddlesList = RandomListWrapper(field.riddlesList)
	}
	init {
		currentRiddleGroup = list.getCurrent()
	}
	private lateinit var currentRiddlesList:RandomListWrapper<Riddle>

	fun getGroupIngo(): RiddlesGroup.GroupInfo = currentRiddleGroup.info

	fun nextGroup(): RiddlesGroup.GroupInfo {
		list.next()
		currentRiddleGroup = list.getCurrent()
		return getGroupIngo()
	}

	fun getCurrentRiddle():Riddle = currentRiddlesList.getCurrent()
	fun nextRiddle(): Boolean  = currentRiddlesList.next()

	fun checkAnswer(answer: String): Boolean{
		return answer == getCurrentRiddle().validAnswer
	}
}