package pro.it_dev.childgame.domain.riddles

data class RiddlesGroup(
	val name: String,
	val fxPath: String,
	val riddlesList: List<Riddle>
) {
}