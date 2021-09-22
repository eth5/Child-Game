package pro.it_dev.childgame.presentation.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pro.it_dev.childgame.presentation.util.ToastMessage
import pro.it_dev.childgame.util.Resource

@Composable
fun AuthScreen(navController: NavController, viewModel: AuthScreenViewModel = hiltViewModel()) {
	Box(
		modifier = Modifier
			.background(MaterialTheme.colors.background, MaterialTheme.shapes.medium)
			.border(1.dp, MaterialTheme.colors.secondary, MaterialTheme.shapes.medium)
			.padding(20.dp)
			.fillMaxWidth(1f)
		//.fillMaxHeight(0.8f)
		,
		contentAlignment = Alignment.Center
	) {
		val loginState by viewModel.loginState
		when(loginState){
			is Resource.Error -> EditVields(viewModel)
			is Resource.Loading -> CircularProgressIndicator()
			is Resource.Success -> navController.popBackStack()
		}
	}
}

@Composable
fun EditVields(viewModel: AuthScreenViewModel) {

	var email by remember {
		mutableStateOf("test@test.ru")
	}
	var passworld by rememberSaveable{
		mutableStateOf("testtest")
	}
	Column(
		modifier = Modifier//.fillMaxSize()
		,
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		TextField(
			value = email,
			onValueChange = { email = it },
			label = { Text(text = "Email") }
		)
		var passwordVisibility by remember{ mutableStateOf(true)}
		TextField(
			value = passworld,
			visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
			onValueChange = { passworld = it },
			label = { Text(text = "Password") },
			trailingIcon = {
				val image = if (passwordVisibility)
					Icons.Filled.Visibility
				else Icons.Filled.VisibilityOff

				IconButton(onClick = {
					passwordVisibility = !passwordVisibility
				}) {
					Icon(imageVector  = image, "")
				}
			}
		)
		Spacer(modifier = Modifier.height(20.dp))
		Row {
			TextButton(
				onClick = { viewModel.login(email = email,passwd = passworld) },
				modifier = Modifier.weight(1f)
			) {
				Text(text = "Login")
			}
			TextButton(
				onClick = { viewModel.create(email = email,passwd = passworld) },
				modifier = Modifier.weight(1f)
			) {
				Text(text = "Register")
			}
			TextButton(
				onClick = { /*TODO*/ },
				modifier = Modifier.weight(1f)
			) {
				Text(text = "Logout")
			}

		}
	}
	ToastMessage(viewModel.toastMessage)
}


