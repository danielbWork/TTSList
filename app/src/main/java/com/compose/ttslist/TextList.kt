package com.compose.ttslist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.*


@Composable
fun TextListItem(text: String, onItemClick: (String) -> Unit) {
	Row(
			modifier = Modifier
					.clickable(onClick = { onItemClick(text) })
					.background(MaterialTheme.colors.primaryVariant)
					.height(57.dp)
					.fillMaxWidth()
					.padding(PaddingValues(8.dp, 16.dp))
	) {
		// TODO Add tts and edit buttons
		Text(text = text, fontSize = 18.sp);
	}
}


@Composable
fun TextList(state: MutableState<TextFieldValue>) {
	val countries = getListOfCountries()// fixme
	var filteredCountries: ArrayList<String>
	LazyColumn(modifier = Modifier.fillMaxWidth()) {
		val searchedText = state.value.text
		filteredCountries = if (searchedText.isEmpty()) {
			countries
		} else {
			val resultList = ArrayList<String>()
			for (country in countries) {
				if (country.lowercase(Locale.getDefault())
								.contains(searchedText.lowercase(Locale.getDefault()))
				) {
					resultList.add(country)
				}
			}
			resultList
		}
		items(filteredCountries) { filteredCountry ->
			TextListItem(text = filteredCountry, onItemClick = { text ->
				/* Add code later */
				//TODO add on delete, on edit and on play
			})
		}
	}
}

@Preview(showBackground = true)
@Composable
fun TextListPreview() {
	val textState = remember { mutableStateOf(TextFieldValue("")) }
	TextList(state = textState)
}

fun getListOfCountries(): ArrayList<String> {
	val isoCountryCodes = Locale.getISOCountries()
	val countryListWithEmojis = ArrayList<String>()
	for (countryCode in isoCountryCodes) {
		val locale = Locale("", countryCode)
		val countryName = locale.displayCountry
		val flagOffset = 0x1F1E6
		val asciiOffset = 0x41
		val firstChar = Character.codePointAt(countryCode, 0) - asciiOffset + flagOffset
		val secondChar = Character.codePointAt(countryCode, 1) - asciiOffset + flagOffset
		val flag = (String(Character.toChars(firstChar)) + String(Character.toChars(secondChar)))
		countryListWithEmojis.add("$countryName $flag")
	}
	return countryListWithEmojis
}

@Preview(showBackground = true)
@Composable
fun TextListItemPreview() {
	TextListItem(text = "United States 🇺🇸", onItemClick = { })
}