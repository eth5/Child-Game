package pro.it_dev.childgame.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pro.it_dev.childgame.presentation.screen.auth.AuthScreen
import pro.it_dev.childgame.presentation.screen.cards.CardsScreen

@Composable
fun Navigation() {
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = Screen.CardsScreen.route){
		composable(route = Screen.CardsScreen.route){
			CardsScreen(navController = navController,"default_")
		}
		composable(route = Screen.AuthScreen.route){
			AuthScreen (navController = navController)
		}
	}
}