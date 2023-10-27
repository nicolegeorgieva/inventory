package com.example.inventory.screen.addoreditinventoryitem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.inventory.R

@Composable
fun ItemImage(
    imagePath: String?,
    link: String?,
    onLinkValueChange: (String) -> Unit,
    onAddLinkImageClick: (String) -> Unit,
    onRemoveImageClick: () -> Unit
) {
    if (imagePath.isNullOrBlank()) {
        AddImageSection(
            linkValue = link ?: "",
            onLinkValueChange = onLinkValueChange,
            onAddLinkImageClick = onAddLinkImageClick
        )
    } else {
        ImageFromPath(
            path = imagePath,
            onRemoveImageClick = onRemoveImageClick
        )
    }
}

@Composable
private fun AddImageSection(
    linkValue: String,
    onLinkValueChange: (String) -> Unit,
    onAddLinkImageClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.add_image_label))

        Spacer(modifier = Modifier.height(6.dp))

        AddImageFromLink(
            linkValue = linkValue,
            onLinkValueChange = onLinkValueChange,
            onAddLinkImage = onAddLinkImageClick
        )
    }
}

@Composable
private fun AddImageFromLink(
    linkValue: String,
    onLinkValueChange: (String) -> Unit,
    onAddLinkImage: (String) -> Unit
) {
    InputRow(
        input = linkValue,
        onInputChange = onLinkValueChange,
        imeAction = ImeAction.Done,
        label = stringResource(R.string.image_url_label)
    )

    if (linkValue.isNotBlank()) {
        Spacer(modifier = Modifier.height(8.dp))

        AddImageUrlButton(onAddLinkImage = {
            onAddLinkImage(linkValue)
        })
    }
}

@Composable
private fun AddImageUrlButton(onAddLinkImage: () -> Unit) {
    Button(
        onClick = onAddLinkImage,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    ) {
        Text(stringResource(R.string.add))
    }
}

@Composable
private fun ImageFromPath(path: String, onRemoveImageClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.Top
    ) {
        AsyncImage(
            modifier = Modifier.size(196.dp),
            model = path,
            contentDescription = stringResource(R.string.item_image)
        )

        FilledIconButton(
            onClick = onRemoveImageClick,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = stringResource(R.string.add_inventory_item_remove_image)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ItemImagePreview() {
    ItemImage(
        imagePath = null,
        link = "",
        onLinkValueChange = {},
        onAddLinkImageClick = {},
        onRemoveImageClick = {}
    )
}