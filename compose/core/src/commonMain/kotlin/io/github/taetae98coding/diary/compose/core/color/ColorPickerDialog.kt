package io.github.taetae98coding.diary.compose.core.color

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
public fun ColorPickerDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Surface(
            shape = DatePickerDefaults.shape,
            color = DatePickerDefaults.colors().containerColor,
        ) {
            Column {
                content()
                ButtonRow(
                    confirmButton = confirmButton,
                    modifier = Modifier.fillMaxWidth()
                        .padding(PaddingValues(bottom = 8.dp, end = 6.dp)),
                )
            }
        }
    }
}

@Composable
private fun ButtonRow(
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End,
    ) {
        confirmButton()
    }
}
