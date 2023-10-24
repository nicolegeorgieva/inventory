package com.example.inventory.screen.home.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.R
import com.example.inventory.screen.home.SectionType

@Composable
fun Section(section: SectionType, quantity: Int) {
    Text(
        text = when (section) {
            SectionType.TOBUY -> stringResource(R.string.home_to_buy, quantity)
            SectionType.ENOUGH -> stringResource(R.string.home_enough, quantity)
        },
        color = when (section) {
            SectionType.TOBUY -> if (quantity > 0) {
                MaterialTheme.colorScheme.error
            } else {
                MaterialTheme.colorScheme.onBackground
            }

            SectionType.ENOUGH -> MaterialTheme.colorScheme.onBackground
        },
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.Bold
    )
}

@Preview(showBackground = true)
@Composable
private fun ToBuySectionPreview() {
    Section(section = SectionType.TOBUY, quantity = 5)
}

@Preview(showBackground = true)
@Composable
private fun EnoughSectionPreview() {
    Section(section = SectionType.ENOUGH, quantity = 8)
}