package com.example.myapplication
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { QuizApp() }
    }
}

@Composable
fun QuizApp() {
    val questions = remember {
        mutableStateListOf(
            Question("¿Cuál es la capital de Francia?", listOf("París", "Madrid", "Berlín", "Lisboa"), "París"),
            Question("¿Cuántos continentes hay en el mundo?", listOf("7", "6", "5", "8"), "7"),
            Question("¿Quién escribió 'Cien años de soledad'?", listOf("Gabriel García Márquez", "Mario Vargas Llosa", "Pablo Neruda", "Jorge Luis Borges"), "Gabriel García Márquez"),
            Question("¿Cuál es el país con la línea costera más grande?", listOf("Canadá", "Rusia", "Australia", "Brasil"), "Canadá"),
            Question("Un granjero habla a la policia, unos tigres entraron en su trigal ¿Cuántos tigres entraron?", listOf("3","2","7","4"), "3"),
            Question("¿quien se perdio en el bosque de la china?", listOf("la chinita","el emperador","un guardia","nadie"), "la chinita"),
            Question("a que se refiere el termino donghua", listOf("series animadas chinas","comics coreanos","un acompañamiento cantones","un deporte vietnamita"), "series animadas chinas"),
        )
    }
    var currentQuestion by remember { mutableStateOf<Question?>(null) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var gameOver by remember { mutableStateOf(false) }
    var shuffledAnswers by remember { mutableStateOf<List<String>>(emptyList()) }

    fun loadNewQuestion() {
        val unusedQuestions = questions.filter { !it.used }
        if (unusedQuestions.isNotEmpty()) {
            currentQuestion = unusedQuestions.random().apply { used = true }
            shuffledAnswers = currentQuestion!!.answers.shuffled()
            selectedAnswer = null
        } else { gameOver = true }
    }

    fun restartGame() {
        questions.forEach { it.used = false }
        gameOver = false
        loadNewQuestion()
    }

    LaunchedEffect(Unit) { loadNewQuestion() }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (gameOver) {
            Text("Juego terminado", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { restartGame() },
                modifier = Modifier.fillMaxWidth()
            ) { Text("Volver a jugar") }
        } else {
            currentQuestion?.let { question ->
                Text(question.text, fontSize = 20.sp)
                Spacer(modifier = Modifier.height(16.dp))

                shuffledAnswers.forEach { answer ->
                    Button(
                        onClick = { selectedAnswer = answer },
                        modifier = Modifier.fillMaxWidth().padding(4.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (selectedAnswer == answer) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                        )
                    ) { Text(answer) }
                }

                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        if (selectedAnswer == question.correctAnswer) { loadNewQuestion() }
                        else { gameOver = true }
                    },
                    enabled = selectedAnswer != null,
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Confirmar") }
            }
        }
    }
}
data class Question(
    val text: String,
    val answers: List<String>,
    val correctAnswer: String,
    var used: Boolean = false
)