package com.ekotyoo.racana.core.composables

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.VisualTransformation
import com.ekotyoo.racana.core.theme.RacanaGray

@Composable
fun RFilledEditText(
    modifier: Modifier = Modifier,
    value: String,
    placeholderString: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions? = null,
    onValueChange: (String) -> Unit = {},
    readOnly: Boolean = false,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    onSearch: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
) {
    val focusManager = LocalFocusManager.current
    val color =
        animateColorAsState(targetValue = if (isError) MaterialTheme.colors.error else RacanaGray.copy(
            alpha = .25f))

    OutlinedTextField(
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        maxLines = maxLines,
        readOnly = readOnly,
        modifier = modifier
            .background(
                color = color.value,
                shape = MaterialTheme.shapes.small
            ),
        value = value,
        textStyle = MaterialTheme.typography.body1,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = color.value,
            unfocusedBorderColor = color.value
        ),
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions ?: KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            },
            onSearch = {
                focusManager.clearFocus()
                onSearch()
            }
        ),
        onValueChange = onValueChange,
        visualTransformation = visualTransformation,
        placeholder = { Text(text = placeholderString, style = MaterialTheme.typography.body1) },
        isError = isError,
    )
}