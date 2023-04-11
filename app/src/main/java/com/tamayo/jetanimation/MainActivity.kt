package com.tamayo.jetanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tamayo.jetanimation.ui.theme.JetAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ColorAnimationSimple()
                }
            }
        }
    }
}

@Composable
fun ColorAnimationSimple() {

    var firstColor by rememberSaveable {
        mutableStateOf(false)
    }
    var secondColor by rememberSaveable {
        mutableStateOf(false)
    }

    var showBox by rememberSaveable {
        mutableStateOf(true)
    }

    val realColor = if (firstColor) Color.Green else Color.Red
    val realColor2 by animateColorAsState(
        targetValue = if (secondColor) Color.Yellow else Color.Magenta,
        animationSpec = tween(2000),
        finishedListener = { showBox = false }
    )


    Column {
        Box(modifier = Modifier
            .size(100.dp)
            .background(realColor)
            .clickable { firstColor = !firstColor }
            .clip(RoundedCornerShape(12.dp)))


        Spacer(modifier = Modifier.size(200.dp))

        if (showBox) {
            Box(modifier = Modifier
                .size(100.dp)
                .background(realColor2)
                .clickable { secondColor = !secondColor }
                .clip(RoundedCornerShape(12.dp)))
        }
    }


}