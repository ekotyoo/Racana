package com.ekotyoo.racana.core.composables

import androidx.compose.animation.animateColorAsState
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
    trailingIcon: @Composable (() -> Unit)? = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false
) {
    val focusManager = LocalFocusManager.current
    val color =
        animateColorAsState(targetValue = if (isError) MaterialTheme.colors.error else MaterialTheme.colors.primary)

    OutlinedTextField(
        singleLine = true,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        modifier = modifier
            .border(
                width = 2.dp,
                color = color.value,
                shape = MaterialTheme.shapes.small
            )
            .background(MaterialTheme.colors.primary.copy(alpha = .05f)),
        value = value,
        textStyle = MaterialTheme.typography.body1,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
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
        isError = isError
    )
}