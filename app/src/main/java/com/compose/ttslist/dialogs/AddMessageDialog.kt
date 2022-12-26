package com.compose.ttslist.dialogs

import android.R
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.compose.ttslist.TTSViewModel
import kotlinx.coroutines.delay

/**
 * Dialog asking user for a message
 * @param ttsViewModel The view model used to add to the list
 */
@Composable
fun AddMessageDialog(
		ttsViewModel: TTSViewModel
) {
	var newMessage by remember { mutableStateOf(TextFieldValue("")) }
	val context = LocalContext.current
	BaseDialog(title = "Add Message",ttsViewModel.isAddDialogOpen, onSubmit = {
		if (newMessage.text.trim().isNotEmpty()) {

			ttsViewModel.addToList(newMessage.text.trim(), context)
		}
	}, submitText = "Done") {

		// Used to have the TextField be focused when dialog is opned
		val focusRequester = remember { FocusRequester() }

		LaunchedEffect(key1 = Unit) {
			delay(200)
			focusRequester.requestFocus()
		}

		TextField(
				value = newMessage,
				onValueChange = {
					newMessage = it
				},
				modifier = Modifier
						.fillMaxWidth()
						.border(
								BorderStroke(
										width = 2.dp,
										color = colorResource(
												id = R.color.darker_gray
										)
								),
								shape = RoundedCornerShape(50)
						)
						.verticalScroll(state = rememberScrollState(), enabled = true)
						.heightIn(
								min = 50.dp,
								max = 100.dp
						)
						.focusRequester(focusRequester)
						.wrapContentHeight(),
				colors = TextFieldDefaults.textFieldColors(
						backgroundColor = Color.Transparent,
						focusedIndicatorColor = Color.Transparent,
						unfocusedIndicatorColor = Color.Transparent
				),
				keyboardOptions = KeyboardOptions(
						keyboardType = KeyboardType.Text,
						capitalization = KeyboardCapitalization.Sentences
				),
				placeholder = { Text(text = "New TTS Message") },
				leadingIcon = {
					Icon(
							imageVector = Icons.Filled.Edit,
							contentDescription = "Edit icon",
							tint = MaterialTheme.colors.secondary,
							modifier = Modifier
									.width(20.dp)
									.height(20.dp)
					)
				},

				)
	}
}
