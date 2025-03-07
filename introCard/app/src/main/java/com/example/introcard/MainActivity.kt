package com.example.introcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.introcard.ui.theme.IntroCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IntroCardTheme {
                Surface(modifier = Modifier.fillMaxSize(),color= MaterialTheme.colorScheme.background) {
                    Greeting()
                }
            }
        }
    }
}

@Composable fun Greeting(modifier: Modifier = Modifier) {
    var showSocialMedia by remember { mutableStateOf(true) }
    var imageIndex by remember { mutableStateOf(1) }

    val imageResource = when (imageIndex) {
        1 -> R.drawable.perfil
        2 -> R.drawable.itcm
        3 -> R.drawable.tecnm
        else -> R.drawable.android_logo
    }

    Column(modifier = modifier.fillMaxWidth().padding(16.dp),
           horizontalAlignment = Alignment.CenterHorizontally,
           verticalArrangement = Arrangement.Center){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(imageResource),
                  contentDescription = "Profile Picture",
                  modifier = Modifier.size(100.dp).clickable { imageIndex = (imageIndex % 4) + 1 })
            Text(text = stringResource(R.string.salute),
                 fontSize = 20.sp,
                 fontWeight = FontWeight.Bold)
            Text(text = stringResource(R.string.profession), fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(modifier = Modifier.fillMaxWidth(),
               verticalArrangement = Arrangement.spacedBy(8.dp)) {
            ContactRow(R.drawable.phoneicon, stringResource(R.string.phoneNumber))
            ContactRow(R.drawable.correo, stringResource(R.string.email))
            ContactRow(R.drawable.placeicon, stringResource(R.string.direction))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { showSocialMedia = !showSocialMedia }) { Text(if (showSocialMedia) "Ocultar redes sociales" else "Mostrar redes sociales") }
        Spacer(modifier = Modifier.height(16.dp))

        if (showSocialMedia) {
            Column(modifier = Modifier.fillMaxWidth(),
                   verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ContactRow(R.drawable.facebook, stringResource(R.string.facebook))
                ContactRow(R.drawable.whatsapp, stringResource(R.string.phoneNumber))
                ContactRow(R.drawable.twitter, stringResource(R.string.facebook))
            }
        }
    }
}

@Composable fun ContactRow(icon: Int, text: String) {
    Row(modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(icon),
              contentDescription = null,
              modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text, fontSize = 14.sp)
    }
}

@Preview(showBackground = true) @Composable fun GreetingPreview() {
    IntroCardTheme {
        Greeting()
    }
}
