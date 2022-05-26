package com.ekotyoo.racana.core.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RFilledButton(
    modifier: Modifier = Modifier,
    placeholderString: String,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        modifier.fillMaxWidth(),
        colors = colors,
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(16.dp),
        enabled = enabled
    ) {
        Text(text = placeholderString, style = MaterialTheme.typography.button)
    }
}