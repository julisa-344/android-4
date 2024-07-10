package com.example.ep4_1
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class WelcomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WelcomeScreen(onContinueClicked = {
                // Aquí puedes iniciar la actividad de inicio de sesión u otra actividad
                startActivity(Intent(this@WelcomeActivity, MainActivity::class.java))
                finish() // Opcional: finaliza la actividad actual si no deseas que permanezca en el back stack
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(onContinueClicked: () -> Unit) {
    var currentIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Imagen de fondo a pantalla completa
        Image(
            painter = painterResource(id = R.drawable.fondo_wlcome2),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Escala la imagen para que ocupe todo el espacio sin recortarla
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo en la parte superior centrada
            Image(
                painter = painterResource(id = R.drawable.logo_principal),
                contentDescription = "Logo",
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .size(200.dp) // Establece el tamaño de la imagen
            )

            // Imagen
            Image(
                painter = painterResource(id = R.drawable.wlcome), // Reemplaza "R.drawable.tu_imagen" con el ID de tu imagen
                contentDescription = "Imagen",
                modifier = Modifier
                    .padding(vertical = 1.dp)
                    .size(250.dp) // Establece el tamaño de la imagen // Espaciado vertical
            )
            // Textos
            val textList = listOf(
                stringResource(id = R.string.texto1_welcome),
                stringResource(id = R.string.texto2_welcome),
                stringResource(id = R.string.texto3_welcome),
                stringResource(id = R.string.texto4_welcome),
            )
            Text(
                text = textList[currentIndex],
                fontSize = 22.sp,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp),
                textAlign = TextAlign.Center
            )
            // Botón "Continuar" en la parte inferior
            Button(
                colors = ButtonDefaults.buttonColors(Color.White),

                onClick = onContinueClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp) // Espaciado inferior

            ) {
                Text(text = stringResource(id = R.string.start_now), color = colorResource(id = R.color.primary_600),
                    fontSize = 18.sp)
            }

            // Detecta gestos de deslizamiento horizontal
            Box(

                modifier = Modifier
                    .fillMaxSize()
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures { change, dragAmount ->
                            if (dragAmount > 0 && currentIndex > 0) {
                                // Deslizar hacia la izquierda
                                currentIndex--
                            } else if (dragAmount < 0 && currentIndex < textList.size - 1) {
                                // Deslizar hacia la derecha
                                currentIndex++
                            }
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                // Contenido para detectar gestos de deslizamiento horizontal
            }

        }
    }
}

