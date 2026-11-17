package com.example.jocparelles

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GameScreen(vm: GameViewModel = viewModel()) {
    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(vm.background),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Joc de les Parelles",
                color = Color(0xFFFFA000),
                fontSize = 28.sp
            )
            Spacer(Modifier.height(24.dp))

            // 2 files x 3 cartes
            for (row in 0..1) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    for (col in 0..2) {
                        val index = row * 3 + col
                        CardView(index, vm)
                    }
                }
                Spacer(Modifier.height(32.dp))
            }

            if (vm.finished) {
                Text(
                    text = "Has guanyat!",
                    color = Color.White,
                    fontSize = 24.sp
                )
                Spacer(Modifier.height(8.dp))
                Button(onClick = { vm.onRestart() }) { Text("RESTART") }
            } else {
                Button(onClick = { vm.onValidate() }) { Text("VALIDATE") }
            }
        }
    }
}

@Composable
private fun RowScope.CardView(index: Int, vm: GameViewModel) {
    val card = vm.cards[index]
    val shown = card.state != CardState.HIDDEN
    val shape = RoundedCornerShape(8.dp)

    Image(
        painter = painterResource(if (shown) card.imageRes else vm.back),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .weight(1f)
            .height(170.dp)
            .clip(shape)
            .then(
                if (!shown) Modifier.border(BorderStroke(3.dp, Color.White), shape)
                else Modifier
            )
            .clickable(enabled = !vm.finished) { vm.onCardClick(index) }
    )
}