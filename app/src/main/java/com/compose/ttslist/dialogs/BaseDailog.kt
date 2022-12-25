package com.compose.ttslist.dialogs

import android.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

/**
 * Basic dialog ui used for the app
 *
 * @param title The name displayed in the dialog
 * @param isDialogOpen Whether or not the dialog should be displayed
 * @param onDismiss Called to close the dialog, because of how tha dialog api works, this is
 * in charge of actually closing the dialog
 * @param onSubmit Called when the user submits the dialog
 * @param submitText The text for the submit button
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BaseDialog(
		title: String, isDialogOpen: MutableState<Boolean>, onDismiss: (() -> Unit)?= null, onSubmit: (() -> Unit)? = null,
		submitText: String = "Submit", content: @Composable
		() -> Unit
) {


	// usePlatformDefaultWidth is used to fix bug where text field took over screen and height
	// stayed the same, this is on google
	Dialog(
			properties = DialogProperties(
					dismissOnBackPress = true, dismissOnClickOutside =
			false, usePlatformDefaultWidth = false
			),
			onDismissRequest = {

				if(onDismiss !== null){
					onDismiss()
				}

				isDialogOpen.value = false
							   },
	) {
		Surface(
				shape = RoundedCornerShape(16.dp),
				color = MaterialTheme.colors.background,
				modifier = Modifier.padding(horizontal = 20.dp)
		) {

			Box(
					contentAlignment = Alignment.TopCenter
			) {
				Column(
						modifier = Modifier
								.padding(20.dp), verticalArrangement = Arrangement.Top
				) {
					Row(
							modifier = Modifier.fillMaxWidth(),
							horizontalArrangement = Arrangement.SpaceBetween,
							verticalAlignment = Alignment.CenterVertically,

							) {
						Text(
								text = title,
								style = TextStyle(
										fontSize = 24.sp,
										fontFamily = FontFamily.Default,
										fontWeight = FontWeight.Bold
								)
						)

						Icon(imageVector = Icons.Filled.Close,
								contentDescription = "Close Dialog",
								tint = colorResource(R.color.darker_gray),
								modifier = Modifier
										.width(30.dp)
										.height(30.dp)
										//close the dialog to assign the value false when Cancel button is clicked
										.clickable {
											if(onDismiss !== null){
												onDismiss()
											}
											isDialogOpen.value = false
										})
					}

					Spacer(modifier = Modifier.height(20.dp))

					content()


						Spacer(modifier = Modifier.height(20.dp))
						Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
							Button(

									shape = RoundedCornerShape(50.dp),
									modifier = Modifier
											.fillMaxWidth()
											.defaultMinSize(minHeight = 50.dp),
									onClick = {
										if(onSubmit !== null){
											onSubmit()
										}
										isDialogOpen.value = false
									},
							) {
								Text(text = submitText)
							}
						}


				}
			}

		}

	}

}