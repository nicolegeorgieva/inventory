package com.example.inventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.inventory.screen.addinventoryitem.AddInventoryItemScreen
import com.example.inventory.screen.home.HomeScreen
import com.example.inventory.screen.moremenu.MoreMenuScreen
import com.example.inventory.ui.theme.InventoryTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InventoryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    LaunchedEffect(Unit) {
                        navigator.navigationEvents.collectLatest { event ->
                            when (event) {
                                NavigationEvent.Back -> navController.popBackStack()
                                is NavigationEvent.Route -> navController.navigate(event.route)
                            }
                        }
                    }

                    NavHost(navController = navController, startDestination = "home") {
                        composable("home") { HomeScreen(navController) }
                        composable("moreMenu") { MoreMenuScreen(navController) }
                        composable("addInventoryItem") {
                            AddInventoryItemScreen(navController)
                        }
                    }
                }
            }
        }
    }
}