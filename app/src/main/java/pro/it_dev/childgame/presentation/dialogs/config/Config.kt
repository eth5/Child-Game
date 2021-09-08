package pro.it_dev.childgame.presentation.dialogs.config

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Config(viewModel: ConfigViewModel = hiltViewModel(), onDismissRequest: () -> Unit) {
	Box(modifier = Modifier.padding(10.dp)){
		Text(text = "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF")
	}

}