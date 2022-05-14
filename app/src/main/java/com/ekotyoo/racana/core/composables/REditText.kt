package com.ekotyoo.racana.core.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun REditText(
    modifier: Modifier = Modifier,
    value: String,
    placeholderString: String,
    leadingIcon: @Composable (() -> Unit)?,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        singleLine = true,
        leadingIcon = leadingIcon,
        modifier = modifier
            .border(
                width = 2.dp,
                color = if (isError) MaterialTheme.colors.error else MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.small
            )
            .background(MaterialTheme.colors.primary.copy(alpha = .05f)),
        value = value,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
        }),
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        placeholder = { Text(text = placeholderString) },
        isError = isError
    )
}