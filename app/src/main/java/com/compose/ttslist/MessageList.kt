package com.compose.ttslist

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
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
		textWithIndex: IndexedValue<String>, isPlaying: Boolean, onPlay: (Context) -> Unit, onStop:
		() -> Unit,
		onRemove:() ->
Unit, onEdit: ()->Unit) {

	val context = LocalContext.current;

	Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
					.fillMaxWidth()
					.padding(5.dp)
	) {

		IconButton(
				onClick = {
					onRemove()
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
						onEdit()
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
						if(isPlaying) onStop() else onPlay(context)
					}
			) {
				Icon(
						painter = painterResource(id = if(isPlaying) R.drawable.ic_stop
						else R.drawable.ic_play ),
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

			items(filteredTextWithIndex) {

				MessageListItem(textWithIndex = it,
						isPlaying = it.index == viewModel.playingMessageIndex.value,
						onPlay = { context ->

								viewModel.playText(context, it.index)

						},
						onStop = {
								 viewModel.stopPlaying()
						},
						onRemove = {
							viewModel.openRemoveFromListDialog(it.index)
						},
						onEdit = {
							viewModel.openEditMessageDialog(it.index)
						}
				)
			}



	}
}