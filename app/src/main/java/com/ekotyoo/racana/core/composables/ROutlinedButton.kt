package com.ekotyoo.racana.core.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ROutlinedButton(
    modifier: Modifier = Modifier,
    placeholderString: String,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.primary,
                shape = MaterialTheme.shapes.small
            ),
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(16.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.background)
    ) {
        Text(
            text = placeholderString,
            color = MaterialTheme.colors.primary
        )
    }
}