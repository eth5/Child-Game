package pro.it_dev.childgame.util

class RandomListWrapper<T>(private val list: List<T>){
	private var shuffledList = list.shuffled()
	private var currentIndex = 0

	fun getCurrent():T = shuffledList[currentIndex]

	fun next():Boolean{
		currentIndex++
		return if (currentIndex > shuffledList.lastIndex) {
			currentIndex = 0
			shuffledList = list.shuffled()
			false
		}else {
			true
		}
	}

}