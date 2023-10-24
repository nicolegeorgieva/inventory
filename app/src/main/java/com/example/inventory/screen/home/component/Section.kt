package com.example.inventory.screen.home.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.inventory.screen.home.SectionType

@Composable
fun Section(section: SectionType, quantity: Int) {
    Text(
        text = when (section) {
            SectionType.TOBUY -> "To buy ($quantity)"
            SectionType.ENOUGH -> "Enough ($quantity)"
        }
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