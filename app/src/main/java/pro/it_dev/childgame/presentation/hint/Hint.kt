package pro.it_dev.childgame.presentation.hint

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupPositionProvider

@Composable
fun PopupHint(showHintState:State<String?>, onDismiss:()->Unit) {
	val hintText by remember {
		showHintState
	}
	if (hintText!=null){
		val transformOriginState = remember { mutableStateOf(TransformOrigin.Center) }
		val density = LocalDensity.current
//		val popupPositionProvider = DropdownMenuPositionProvider(
//			dropdownOffset,
//			density
//		) { parentBounds, menuBounds ->
//			transformOriginState.value = calculateTransformOrigin(parentBounds, menuBounds)
//		}
//		Popup(popupPositionProvider = PopupPositionProvider) {
//
//		}
	}
}
@Preview
@Composable
fun popUpTest(){
	val expandState = remember {
		mutableStateOf(false)
	}
	Column() {
		Button(
			onClick = {
				expandState.value = true
			}
		) {
			Text(text = "打开 DropdownMenu")
		}
		Popup(
			alignment = Alignment.TopStart,
			onDismissRequest = {
				Log.e("ccm","执行了onDismissRequest")
				expandState.value = false
			},
			offset = IntOffset(10,140),
		) {
			Column(
				modifier = Modifier
					.width(IntrinsicSize.Min)
					.shadow(
						elevation = 2.dp, shape = RoundedCornerShape(3.dp)
					)
					.background(Color.White, shape = RoundedCornerShape(3.dp))
			) {
				dropdownMenuItemTest(expandState, Icons.Filled.Favorite,"收藏")
				dropdownMenuItemTest(expandState,Icons.Filled.Edit,"编辑")
				dropdownMenuItemTest(expandState,Icons.Filled.Delete,"删除")
			}
		}
	}
}
@Composable
fun dropdownMenuItemTest(state:MutableState<Boolean>, icon: ImageVector, text:String){
	val interactionSource = remember { MutableInteractionSource() }
	val pressState = interactionSource.collectIsPressedAsState()
	val focusState = interactionSource.collectIsFocusedAsState()
	DropdownMenuItem(
		onClick = {
			state.value = false
		},
		enabled = true,
		interactionSource = interactionSource
	) {
		Icon(imageVector = icon, contentDescription = text,tint = if(pressState.value || focusState.value) Color.Red else Color.Black)
		Text(text = text,modifier = Modifier.padding(start = 10.dp),color = if(pressState.value || focusState.value) Color.Red else Color.Black)
	}
}
