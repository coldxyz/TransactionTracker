package com.cold.transactiontracker.core.navigation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import com.cold.transactiontracker.core.navigation.data.BottomNavItem
import com.cold.transactiontracker.features.homescreen.ui.HomeDestination

val bottomNavItems = listOf(
    BottomNavItem(
        route = HomeDestination.route,
        icon = Icons.Default.Home,
        label = "Home"
    )
)

@Composable
fun BottomNavigationBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        bottomNavItems.forEach { item ->

            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute == item.route) return@NavigationBarItem
                    onNavigate(item.route)
                },
                icon = {
                    Icon(item.icon, contentDescription = item.label)
                }
            )
        }
    }
}