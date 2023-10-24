package com.example.inventory.screen.home.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.inventory.R
import com.example.inventory.ui.theme.InventoryTheme

@Composable
fun InventoryItemRow(
    itemName: String,
    quantity: String,
    image: String?,
    onAddQuantity: () -> Unit,
    onRemoveQuantity: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .border(
                border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.onBackground),
                shape = RoundedCornerShape(4.dp)
            ),
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ItemImage(image = image ?: "")

            ItemName(itemName = itemName)

            AddRemoveQuantity(
                onRemoveQuantity = onRemoveQuantity,
                quantity = quantity,
                onAddQuantity = onAddQuantity
            )
        }
    }
}

@Composable
private fun ItemImage(
    image: String
) {
    val modifier = Modifier
        .size(64.dp)
        .background(MaterialTheme.colorScheme.background)
        .border(
            border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.onBackground),
            shape = RoundedCornerShape(
                topStart = 4.dp,
                topEnd = 0.dp,
                bottomStart = 4.dp,
                bottomEnd = 0.dp
            )
        )
        .padding(4.dp)

    if (image.isNotBlank()) {
        RemoteImage(image = image, modifier = modifier)
    } else {
        DefaultImage(modifier = modifier)
    }
}

@Composable
private fun RemoteImage(
    image: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier,
        model = image,
        contentDescription = stringResource(R.string.item_image)
    )
}

@Composable
private fun DefaultImage(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier,
        painter = painterResource(id = R.drawable.baseline_inventory_24),
        contentDescription = stringResource(R.string.item_image),
        tint = MaterialTheme.colorScheme.primary
    )
}

@Composable
private fun RowScope.ItemName(itemName: String) {
    Text(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .weight(1f),
        text = itemName
    )
}

@Composable
private fun AddRemoveQuantity(
    onRemoveQuantity: () -> Unit,
    quantity: String,
    onAddQuantity: () -> Unit
) {
    RemoveQuantityButton(onClick = onRemoveQuantity)

    Quantity(quantity = quantity)

    AddQuantityButton(onClick = onAddQuantity)
}

@Composable
private fun AddQuantityButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Filled.AddCircle,
            contentDescription = stringResource(R.string.add)
        )
    }
}

@Composable
private fun Quantity(quantity: String) {
    Text(
        modifier = Modifier.widthIn(min = 24.dp),
        textAlign = TextAlign.Center,
        text = quantity
    )
}

@Composable
private fun RemoveQuantityButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_remove_circle_24),
            contentDescription = stringResource(R.string.remove)
        )
    }
}

@Composable
private fun SectionDivider(title: String, color: Color) {
    Text(
        text = title,
        color = color
    )
}

@Preview(showBackground = true)
@Composable
private fun InventoryItemRowPreview() {
    InventoryTheme {
        InventoryItemRow(
            itemName = "Water bottles",
            quantity = "4",
            image = "",
            onAddQuantity = {},
            onRemoveQuantity = {}
        )
    }
}