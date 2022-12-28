package com.compose.ttslist

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import java.io.File

const val MESSAGE_LIST_FILENAME = "messages.txt"


/**
 * View model handling all the tts stuff
 */
class TTSViewModel : ViewModel() {


	val textState = mutableStateOf(TextFieldValue(""))
	private val textList = mutableListOf<String>().toMutableStateList()


	val isAddDialogOpen = mutableStateOf(false)

	val isRemoveDialogOpen = mutableStateOf(false)
	var messageToRemoveIndex = 0

	val isEditingDialogOpen = mutableStateOf(false)
	var messageToEditIndex = 0

	var playingMessageIndex = mutableStateOf(-1)

	var textToSpeech: TextToSpeech? = null


	/**
	 * Get immutable version of the text list
	 */
	fun getTextList() = textList.toList()

	// region Open Dialogs

	/**
	 * Opens the dialog to confirm if user wants to delete the message
	 * @param index The index of the message in the list
	 */
	fun openRemoveFromListDialog(index: Int) {
		messageToRemoveIndex = index
		isRemoveDialogOpen.value = true
	}

	/**
	 * Opens the dialog to enable editing the message
	 * @param index The index of the message in the list
	 */
	fun openEditMessageDialog(index: Int) {
		messageToEditIndex = index
		isEditingDialogOpen.value = true
	}

	// endregion

	// region Update List

	/**
	 * Adds the text to the end of the list and saves it
	 * @param text The text added to the list
	 * @param context The context used to save the list
	 */
	fun addToList(text: String, context: Context) {
		textList.add(text)
		save(context)
	}

	/**
	 * Removes the message in given index from the list
	 * @param context The context used to save the list
	 */
	fun removeFromList(context: Context) {
		textList.removeAt(messageToRemoveIndex)
		save(context)
	}


	/**
	 * Edits the text in the list
	 * @param text The new text for the item
	 * @param context The context used to save the list
	 */
	fun editListItem(text: String, context: Context) {
		textList[messageToEditIndex] = text
		save(context)
	}

	// endregion

	// region File

	/**
	 * Load the text
	 */
	fun load(context: Context) {

		this.textList.clear()

		val file = File(context.filesDir, MESSAGE_LIST_FILENAME)

		// Creates the file for the first time
		if (!file.exists()) {
			save(context)
			return
		}
		context.openFileInput(MESSAGE_LIST_FILENAME).bufferedReader().readLines().also {
			textList.addAll(it)
		}

	}

	/**
	 * Saves the current state of the list
	 */
	private fun save(context: Context) {

		context.openFileOutput(MESSAGE_LIST_FILENAME, Context.MODE_PRIVATE).use {
			it.write(textList.joinToString("\n").toByteArray())
		}
	}

	// endregion

	// region Audio

	/**
	 * Plays the text, closing any text played before.
	 *
	 * @param context The context the text was pulled from
	 * @param messageIndex The index of the message to play
	 */
	fun playText(context: Context, messageIndex: Int) {

		playingMessageIndex.value = messageIndex

		if (textToSpeech === null) {
			textToSpeech = TextToSpeech(context) {

				if (it == TextToSpeech.SUCCESS) {
					textToSpeech?.let { textToSpeech ->

						textToSpeech.setOnUtteranceProgressListener(
								object : UtteranceProgressListener() {

									var currentUtteranceId: String? = null

									override fun onStart(utteranceId: String?) {
										println(utteranceId)
										currentUtteranceId = utteranceId
									}

									override fun onDone(utteranceId: String?) {
										println(currentUtteranceId)
										println(utteranceId)
										if (currentUtteranceId == utteranceId) {
											currentUtteranceId = null
											playingMessageIndex.value = -1
										}
									}

									@Deprecated("Deprecated in Java")
									override fun onError(utteranceId: String?) {
									}

								})

						textToSpeech.setSpeechRate(1.0f)
						textToSpeech.speak(
								textList[playingMessageIndex.value],
								TextToSpeech.QUEUE_FLUSH,
								null,
								"${playingMessageIndex.value}"
						)
					}
				}

			}

			return;
		}

		textToSpeech?.speak(
				textList[playingMessageIndex.value], TextToSpeech.QUEUE_FLUSH, null,
				"${playingMessageIndex.value}"
		)
	}

	/**
	 * Stops playing the text to speech
	 */
	fun stopPlaying() {
		textToSpeech?.stop()
	}


	// endregion
}