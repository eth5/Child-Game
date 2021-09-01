package pro.it_dev.childgame.domain

class CardsKit(
	val kitPath: String,
	val list: List<ItemsGroup>,
) {
	enum class State{
		DEFAULT,
		QUESTION,
		MUSIC
	}
	var state = State.DEFAULT

}