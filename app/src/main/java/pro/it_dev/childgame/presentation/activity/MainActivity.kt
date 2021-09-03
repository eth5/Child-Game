package pro.it_dev.childgame.presentation.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import pro.it_dev.childgame.presentation.screen.CardsScreen
import pro.it_dev.childgame.presentation.ui.theme.ChildGameTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			ChildGameTheme {
				Surface(
					color = MaterialTheme.colors.background,
					modifier = Modifier.fillMaxSize()
				) {

					CardsScreen("default_")
				}
			}
		}
	}
}

