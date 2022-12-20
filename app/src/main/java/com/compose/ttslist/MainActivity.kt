package com.compose.ttslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.compose.ttslist.ui.theme.TTSListTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			TTSListTheme {

				Scaffold(
						topBar = { TopBar() },
						backgroundColor = MaterialTheme.colors.background
				) {padding -> // We need to pass scaffold's inner padding to content. That's why we use Box.
					Box(modifier = Modifier.padding(padding)) {
						MainScreen()
					}
				}

			}
		}
	}
}

@Composable
fun TopBar() {
	// TODO add a Add button
	TopAppBar(
			title = { Text(text = stringResource(R.string.app_name), fontSize = 18.sp) },
			backgroundColor = MaterialTheme.colors.primary,
			contentColor = Color.White
	)
}

@Composable
fun MainScreen() {
	val textState = remember { mutableStateOf(TextFieldValue("")) }
	val textList = remember { mutableListOf("a", "sadnjisdansjad asmkdasd asmidomsado sdad", "b") }

	Column {
		SearchView(textState)
		// TODO Add actual text here
		TextList(state = textState, textList)
	}
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
	TopBar()
}



@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
	MainScreen()
}