package com.compose.ttslist.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.compose.ttslist.TTSViewModel

/**
 * Dialog used to ask user if they want to delete a task
 * @param ttsViewModel The view model used to remove from the list
 */
@Composable
fun DeleteTaskDialog(
		ttsViewModel: TTSViewModel,
) {
	val context = LocalContext.current
	val messageToDelete = ttsViewModel.getTextList()[ttsViewModel.messageToRemoveIndex]

	BaseDialog(title = "Remove Message", isDialogOpen = ttsViewModel.isRemoveDialogOpen, onSubmit = {

		ttsViewModel.removeFromList(context)

	}, submitText = "Remove") {
		Column(
				modifier = Modifier.padding(10.dp),
		) {

			// Input field for text
			Text(
					text = "Are you sure you want to delete ${messageToDelete.trim()}?",
					style = MaterialTheme.typography.body1
			)
		}
	}

}