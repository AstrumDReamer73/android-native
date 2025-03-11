package com.example.e657

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.e657.ui.theme.E657Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            E657Theme {

            }
        }
    }
}

@Composable
fun Gallery() {
    var index by remember { mutableStateOf(0) }

    val images = listOf(
        Painting(R.drawable.image1, "Mona Lisa", "Leonardo da Vinci", 1503),
        Painting(R.drawable.image2, "La Noche Estrellada", "Vincent van Gogh", 1889),
        Painting(R.drawable.image3, "El Grito", "Edvard Munch", 1893)
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Image(
            painter = painterResource(images[index].imageID),
            contentDescription = images[index].title
        )
        Spacer(Modifier.height(16.dp))
        Text(text = "Título: ${images[index].title}")
        Spacer(Modifier.height(6.dp))
        Text(text = "Artista: ${images[index].artist} (${images[index].year})")
        Spacer(Modifier.height(12.dp))
        Row {
            Button(
                onClick = { index = (index - 1 + images.size) % images.size } // Retrocede cíclicamente
            ) {
                Text("Anterior")
            }
            Spacer(Modifier.width(8.dp))
            Button(
                onClick = { index = (index + 1) % images.size } // Avanza cíclicamente
            ) {
                Text("Siguiente")
            }
        }
    }
}

data class Painting(
    val imageID:Int,
    var title:String,
    val artist:String,
    val year: Int
)