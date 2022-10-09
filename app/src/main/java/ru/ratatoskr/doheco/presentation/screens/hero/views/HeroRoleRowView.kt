package ru.ratatoskr.doheco.presentation.screens.hero.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.ratatoskr.doheco.domain.model.Hero
import ru.ratatoskr.doheco.presentation.base.Screens


@Composable
fun HeroRoleRowView(
    navController: NavController,
    name: String,
    role: String,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(Screens.Role.route + "/" + role)
            }
    ) {
        Box(
            modifier = Modifier
                .padding(start = 20.dp)
        ) {
            Text(
                fontSize = 12.sp,
                color = Color.White,
                text = name
            )
        }

    }
}