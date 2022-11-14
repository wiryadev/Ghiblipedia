package com.wiryadev.ghiblipedia.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun RowScope.BottomNavItem(
    title: String,
    icon: ImageVector,
    selectedIcon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomNavigationItem(
        selected = isSelected,
        onClick = onClick,
        modifier = modifier,
        label = { Text(text = title) },
        icon = {
            Icon(
                imageVector = if (isSelected) selectedIcon else icon,
                contentDescription = title,
            )
        }
    )
}