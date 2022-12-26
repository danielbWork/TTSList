package com.compose.ttslist

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import java.io.File
import java.util.Locale
import kotlin.streams.toList

const val MESSAGE_LIST_FILENAME = "messages.txt"


/**
 * View model handling all the tts stuff
 */
class TTSViewModel: ViewModel() {


	val textState = mutableStateOf(TextFieldValue(""))
	private val textList = mutableListOf<String>().toMutableStateList()


	val isAddDialogOpen = mutableStateOf(false)

	val isRemoveDialogOpen = mutableStateOf(false)
	var messageToRemoveIndex = 0

	private  var  textToSpeech:TextToSpeech? = null


	/**
	 * Get immutable version of the text list
	 */
	fun getTextList() = textList.toList()

	// region Open Dialogs

	fun openRemoveFromListDialog(index: Int) {
		messageToRemoveIndex = index
		isRemoveDialogOpen.value = true
	}

	// endregion

	// region Update List

	/**
	 * Adds the text to the end of the list and saves it
	 * @param text The text added to the list
	 * @param context The context used to save the list
	 */
	fun addToList(text: String,context: Context) {
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
	fun editListItem(text: String,context: Context) {
		textList[0] = text // fixme
		save(context)
	}

	// endregion

	// region File

	/**
	 * Load the text
	 */
	fun load(context: Context){

		val file = File(context.filesDir, MESSAGE_LIST_FILENAME)

		// Creates the file for the first time
		if(!file.exists()){
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

	// TODO add updater for icon

	/**
	 * Plays the text, closing any text played before.
	 *
	 * @param context The context the text was pulled from
	 * @param textValue The text to play
	 */
	fun playText(context:Context, textValue: String){
		if(textToSpeech === null){
			textToSpeech = TextToSpeech(context){

				if (it == TextToSpeech.SUCCESS) {
					textToSpeech?.let { txtToSpeech ->
						txtToSpeech.setSpeechRate(1.0f)
						txtToSpeech.speak(
								textValue,
								TextToSpeech.QUEUE_FLUSH,
								null,
								null
						)
					}
				}

			}

			return;
		}

		textToSpeech?.stop()
		textToSpeech?.speak(textValue, TextToSpeech.QUEUE_FLUSH, null, null)
	}

	/**
	 * Whether or not the text to speech is working
	 */
	fun isPlaying() = textToSpeech?.isSpeaking ?: false


	// endregion
}