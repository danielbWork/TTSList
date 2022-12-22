package com.compose.ttslist

import android.content.Context
import android.speech.tts.TextToSpeech
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import java.util.Locale

/**
 * View model handling all the tts stuff
 */
class TTSViewModel: ViewModel() {

	val textState = mutableStateOf(TextFieldValue(""))
	val textList = mutableListOf("a", "sadnjisdansjad asmkdasd asmidomsado sdad",
			"bob says hi").toMutableStateList()

	val isAddDialogOpen = mutableStateOf(false)

	private  var  textToSpeech:TextToSpeech? = null

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

}