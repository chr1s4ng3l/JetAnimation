package com.tamayo.jetanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tamayo.jetanimation.ui.theme.JetAnimationTheme
import com.tamayo.jetanimation.ui.theme.Purple500
import kotlin.random.Random.Default.nextInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetAnimationTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //ColorAnimationSimple()
                    //SizeAnimation()
                    // VisibilityAnimation()
                    CrossFadeExampleAnimation()

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
            .clip(CircleShape))


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

@Composable
fun SizeAnimation() {

    var smallSize by rememberSaveable {
        mutableStateOf(true)
    }

    var showText by rememberSaveable {
        mutableStateOf(false)
    }

    val size by animateDpAsState(
        targetValue = if (smallSize) 50.dp else 150.dp,
        tween(600),
        finishedListener = { showText = !smallSize })


    Box(modifier = Modifier
        .size(size)
        .background(Purple500)
        .clickable { smallSize = !smallSize }) {

        if (showText) {
            Text(
                text = "Hi",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = size.value.sp, modifier = Modifier.align(Alignment.Center)
            )
        }

    }

}

@Composable
fun VisibilityAnimation() {


    var isVisible by remember {
        mutableStateOf(true)
    }

    Button(onClick = { isVisible = !isVisible }) {
        Text(text = "Show/Hide")
    }

    Spacer(modifier = Modifier.size(50.dp))

    AnimatedVisibility(isVisible, enter = slideInVertically(), exit = slideOutVertically()) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Purple500)
        )
    }


}

@Composable
fun CrossFadeExampleAnimation() {
    var myComponentType: ComponentType by remember {
        mutableStateOf(ComponentType.Text)
    }

    Column(modifier = Modifier.fillMaxSize()) {

        Button(onClick = { myComponentType = getComponentTypeRandom() }) {
            Text(text = "Change component")
        }

        Crossfade(targetState = myComponentType) {
            when (it) {
                ComponentType.Image -> Icon(Icons.Default.Phone, "")
                ComponentType.Text -> Text(text = "Hi there i am a component")
                ComponentType.Box -> Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(Purple500)
                )
                ComponentType.Error -> Text(text = "Error")
            }
        }

    }

}

fun getComponentTypeRandom(): ComponentType {
    return when (nextInt(from = 0, until = 3)) {
        0 -> ComponentType.Image
        1 -> ComponentType.Text
        2 -> ComponentType.Box

        else -> ComponentType.Error
    }

}

enum class ComponentType() {
    Image, Text, Box, Error
}

































