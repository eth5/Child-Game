package pro.it_dev.childgame.presentation.dialogs.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pro.it_dev.childgame.presentation.screen.jumping_buttons.ColorText
import pro.it_dev.childgame.presentation.util.AnimationByOffsetInBox

@Composable
fun Menu(navController: NavController? = null, viewModel: MenuViewModel = hiltViewModel()) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(5.dp),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			ColorText(text = "Настройки",fontSize = 30.sp)
			val menuItems by remember { viewModel.menuItems }
			Column(
				modifier = Modifier
					.weight(1f)
					.verticalScroll(rememberScrollState())
			) {
				menuItems.forEachIndexed { index, menuItem ->
					AnimationByOffsetInBox(start = (-500).dp, target = 0.dp,delay = 100L*index) {

						TextButton(
							onClick = menuItem.action,
							shape = MaterialTheme.shapes.small,
							border = BorderStroke(1.dp,MaterialTheme.colors.onBackground),
							modifier = Modifier
								.padding(3.dp)
								.fillMaxWidth()
						) {
							ColorText(text = menuItem.text,fontSize = 20.sp,factor = 1.0f)
						}

					}
				}
			}
		}
}