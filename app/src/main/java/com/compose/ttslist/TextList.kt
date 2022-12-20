package com.compose.ttslist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*


@Composable
fun TextListItem(text: String, onItemClick: (String) -> Unit) {
	Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
					.clickable(onClick = { onItemClick(text) })
// fixme					.background(MaterialTheme.colors.primaryVariant)
					.fillMaxWidth()
					.padding(5.dp)
	) {

		// TODO maybe update icon colors

		IconButton(
				onClick = {
					// TODO Add remove
				}
		) {
			Icon(
					Icons.Default.Close,
					contentDescription = "",
					modifier = Modifier
							.size(24.dp)
			)
		}
		// TODO Add tts and edit buttons
		Text(text = text, fontSize = 18.sp, modifier = Modifier.fillMaxWidth(0.7f))

		Row {
			Spacer(modifier = Modifier.weight(1f))
			IconButton(
					onClick = {
						// TODO Edit
					}
			) {
				Icon(
						Icons.Default.Edit,
						contentDescription = "",
						modifier = Modifier
								.size(24.dp))
			}

			IconButton(
					onClick = {
						// TODO Play
					}
			) {
				Icon(
						painter = painterResource(id = R.drawable.ic_play),
						contentDescription = "",
						modifier = Modifier
								.size(24.dp)
				)
			}
		}

	}
}


@Composable
fun TextList(state: MutableState<TextFieldValue>, textValues: MutableList<String>) {
	LazyColumn(modifier = Modifier.fillMaxWidth()) {
		val searchedText = state.value.text
		if (textValues.isEmpty()) {
			// TODO Add empty code



		} else {
			val filteredText = textValues.filter { it.lowercase().lowercase(Locale.getDefault())
					.contains(searchedText.lowercase(Locale.getDefault())) }
			 ArrayList<String>()

			items(filteredText) {

				TextListItem(text = it,
						onItemClick = { text ->
							/* Add code later */
							//TODO add on delete, on edit and on play
						})
			}

		}

	}
}

@Preview(showBackground = true)
@Composable
fun TextListPreview() {
	val textState = remember { mutableStateOf(TextFieldValue("")) }
	val textList = remember { mutableListOf("a", "sadnjisdansjad asmkdasd asmidomsado sdad", "b") }

	TextList(state = textState, textList)
}

@Preview(showBackground = true)
@Composable
fun TextListItemPreview() {
	TextListItem(text = "United States 🇺🇸", onItemClick = { })
}