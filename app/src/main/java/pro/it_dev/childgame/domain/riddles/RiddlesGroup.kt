package pro.it_dev.childgame.domain.riddles

data class RiddlesGroup(
	val info:GroupInfo,
	val riddlesList: List<Riddle>
) {
	data class GroupInfo(val name:String, val startFxPath: String, val errorFxPath:String)
}