package com.compose.ttslist

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import java.util.*


@Composable
fun MessageListItem(
		textWithIndex: IndexedValue<String>, onPlay: (Context, String) -> Unit, onRemove:(Int) ->
Unit, onEdit: (Int)->Unit) {

	val context = LocalContext.current;

	Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
// fixme					.background(MaterialTheme.colors.primaryVariant)
					.fillMaxWidth()
					.padding(5.dp)
	) {

		// TODO maybe update icon colors

		IconButton(
				onClick = {
					onRemove(textWithIndex.index)
				}
		) {
			Icon(
					Icons.Default.Close,
					contentDescription = "",
					modifier = Modifier
							.size(24.dp)
			)
		}
		Text(text = textWithIndex.value, fontSize = 18.sp, modifier = Modifier.fillMaxWidth(0.7f))

		Row {
			Spacer(modifier = Modifier.weight(1f))
			IconButton(
					onClick = {
						onEdit(textWithIndex.index)
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
						onPlay(context, textWithIndex.value)
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
fun MessageList() {

	val viewModel: TTSViewModel = viewModel()
	val state = viewModel.textState
	val textValues = viewModel.getTextList()

	if (textValues.isEmpty()) {

		Box(modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 0.dp).fillMaxHeight()) {
			Button(

					shape = RoundedCornerShape(50.dp),
					modifier = Modifier
							.fillMaxWidth()
							.defaultMinSize(minHeight = 50.dp).align(Alignment.Center),
					onClick = {
						viewModel.isAddDialogOpen.value = true
					},
			) {
				Text(text = "Add Message")
			}
		}

		return
	}

	LazyColumn(modifier = Modifier.fillMaxWidth()) {
		val searchedText = state.value.text

			val filteredTextWithIndex = textValues.withIndex().toList().filter { it.value.lowercase(Locale
					.getDefault())
					.contains(searchedText.lowercase(Locale.getDefault())) }
			 ArrayList<String>()

			// TODO handle editing

			items(filteredTextWithIndex) {

				MessageListItem(textWithIndex = it,
						onPlay = { context, text ->

								viewModel.playText(context, text)

						},
						onRemove = { index ->
							viewModel.openRemoveFromListDialog(index)
						},
						onEdit = { index ->
							viewModel.openEditMessageDialog(index)
						}
				)
			}



	}
}

//@Preview(showBackground = true)
//@Composable
//fun TextListPreview() {
//	val textState = remember { mutableStateOf(TextFieldValue("")) }
//	val textList = remember { mutableListOf("a", "sadnjisdansjad asmkdasd asmidomsado sdad", "b") }
//
//	TextList()
//}

//@Preview(showBackground = true)
//@Composable
//fun TextListItemPreview() {
//	MessageListItem(text = "United States 🇺🇸", onPlay = { context: Context, s: String -> })
//}