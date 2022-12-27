package com.compose.ttslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.compose.ttslist.dialogs.AddMessageDialog
import com.compose.ttslist.dialogs.DeleteTaskDialog
import com.compose.ttslist.dialogs.EditMessageDialog
import com.compose.ttslist.ui.theme.TTSListTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			TTSListTheme {
				val viewModel: TTSViewModel = viewModel()

				viewModel.load(this)

				Scaffold(
						topBar = { TopBar() },
						backgroundColor = MaterialTheme.colors.background
				) {padding -> // We need to pass scaffold's inner padding to content. That's why we use Box.
					Box(modifier = Modifier.padding(padding)) {



						MainScreen()

						if(viewModel.isAddDialogOpen.value){
							AddMessageDialog(ttsViewModel = viewModel)
						}

						if(viewModel.isRemoveDialogOpen.value){
							DeleteTaskDialog(ttsViewModel = viewModel)
						}

						if(viewModel.isEditingDialogOpen.value){
							EditMessageDialog(ttsViewModel = viewModel)
						}

					}
				}

			}
		}
	}
}

@Composable
fun TopBar() {

	val viewModel: TTSViewModel = viewModel()

	TopAppBar(
			title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
			backgroundColor = MaterialTheme.colors.primary,
			contentColor = Color.White,
			actions = {
				IconButton(
					onClick = {
						viewModel.isAddDialogOpen.value = true
					}
			) {
				Icon(
						Icons.Default.Add,
						contentDescription = "Add Message",
						modifier = Modifier
								.size(24.dp)
				)
			}
			}
	)
}

@Composable
fun MainScreen() {
	val viewModel: TTSViewModel = viewModel()

	Column {
		SearchView(viewModel.textState)
		// TODO Add actual text here
		MessageList()
	}
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
	TopBar()
}



//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//	MainScreen(viewModel)
//}