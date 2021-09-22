package pro.it_dev.childgame.presentation.navigation

sealed class Screen(val route:String){
    object CardsScreen:Screen("card_screen")
    object AuthScreen:Screen("auth_screen")

    fun withArgs(vararg args:String):String{
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}
