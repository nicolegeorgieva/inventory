package com.example.inventory.screen.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.inventory.R
import com.example.inventory.ui.theme.InventoryTheme

@Composable
fun EmptyInventory() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmptyInventoryMessage()

        LottieAnimation()
    }
}

@Composable
private fun EmptyInventoryMessage() {
    Text(
        text = stringResource(R.string.home_empty_inventory),
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun LottieAnimation() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.empty_inventory)
    )

    LottieAnimation(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f),
        composition = composition,
        iterations = Int.MAX_VALUE
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyInventoryPreview() {
    InventoryTheme {
        EmptyInventory()
    }
}