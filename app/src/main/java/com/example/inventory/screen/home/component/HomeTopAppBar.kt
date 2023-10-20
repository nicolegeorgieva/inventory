package com.example.inventory.screen.home.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.inventory.R
import com.example.inventory.ui.theme.InventoryTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    name: String?,
    navController: NavController?
) {
    TopAppBar(
        title = {
            GreetingMessage(name)
        },
        actions = {
            MoreMenuButton(navController)
        }
    )
}

@Composable
private fun GreetingMessage(name: String?) {
    Text(
        text = if (name == null) {
            stringResource(R.string.home_generic_greeting)
        } else {
            stringResource(R.string.home_personalized_greeting, name)
        },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun MoreMenuButton(navController: NavController?) {
    IconButton(
        onClick = {
            navController?.navigate("moreMenu")
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = stringResource(R.string.home_menu)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTopAppBarNamePreview() {
    InventoryTheme {
        HomeTopAppBar(
            name = "Amy",
            navController = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTopAppBarNoNamePreview() {
    InventoryTheme {
        HomeTopAppBar(
            name = null,
            navController = null
        )
    }
}