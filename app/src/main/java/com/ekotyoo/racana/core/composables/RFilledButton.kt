package com.ekotyoo.racana.core.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RFilledButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    placeholderString: String
) {
    Button(
        onClick = onClick,
        modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(text = placeholderString)
    }
}