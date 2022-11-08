package net.doheco.presentation.screens.tiers.views

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import net.doheco.R
import net.doheco.presentation.screens.tiers.models.TiersState

@ExperimentalFoundationApi
@Composable
fun TierRowView(
    tierNum: Int,
    viewState: TiersState,
    OnTierChange: (String) -> Unit
) {

    var selectedTier by remember { mutableStateOf("0") }

    when (viewState) {
        is TiersState.IndefinedState -> {
            Log.e("TOHA","viewState.player_tier="+viewState.player_tier)
            if (viewState.player_tier != "undefined") {
                selectedTier = viewState.player_tier[0] + ""
            }
        }
        is TiersState.DefinedState -> {
            if (viewState.player_tier != "undefined") {
                selectedTier = viewState.player_tier[0] + ""
            }
        }
        else -> {}
    }

    Log.e("TOHA", "selectedTier:" + selectedTier);

    var tierImage = "https://doheco.net/app/img/tier/0.png"
    var tierDescription = "Tier undefined"
    var tierName = stringResource(id = R.string.undefined_tier_title)
    var rowTextColor = Color.White
    var rowBackgroundColor = Color.Transparent

    if (tierNum > 0) {
        tierImage = "https://doheco.net/app/img/tier/$tierNum.png"
        when (tierNum) {
            1 -> tierName = stringResource(id = R.string.herald_tier_title)
            2 -> tierName = stringResource(id = R.string.guardian_tier_title)
            3 -> tierName = stringResource(id = R.string.crusader_tier_title)
            4 -> tierName = stringResource(id = R.string.archon_tier_title)
            5 -> tierName = stringResource(id = R.string.legend_tier_title)
            6 -> tierName = stringResource(id = R.string.ancient_tier_title)
            7 -> tierName = stringResource(id = R.string.divine_tier_title)
            8 -> tierName = stringResource(id = R.string.immortal_tier_title)
        }
    }

    if (selectedTier==tierNum.toString()) {
        rowTextColor = Color.Black
        rowBackgroundColor = Color(0xFFc98000)
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .drawWithContent {
                drawContent()
                clipRect { // Not needed if you do not care about painting half stroke outside
                    val strokeWidth = Stroke.DefaultMiter
                    val y = size.height // strokeWidth
                    drawLine(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF0d111c),
                                Color(0xFF0d111c),
                                Color(0xFF0d111c),
                                //Color(0xFF000022),
                                //Color(0xFF000022)
                            )
                        ),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Square,
                        start = Offset.Zero.copy(y = y),
                        end = Offset(x = size.width, y = y)
                    )
                }
            }
            .fillMaxWidth()
            .height(50.dp)
            .background(rowBackgroundColor)
            .clickable { OnTierChange(tierNum.toString()) }

    ) {
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Image(
                modifier = Modifier
                    .width(20.dp)
                    .height(20.dp),
                painter = rememberAsyncImagePainter(tierImage),
                contentDescription = tierDescription
            )
            Text(
                modifier = Modifier
                    .padding(start = 10.dp),
                fontSize = 12.sp,
                lineHeight = 50.sp,
                color = rowTextColor,
                text = tierName
            )

        }

    }
}