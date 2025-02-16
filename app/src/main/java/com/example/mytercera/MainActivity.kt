package com.example.mytercera

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.example.mytercera.ui.theme.MyTerceraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyTerceraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PedidoScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LifecycleEvent", "La aplicación se ha cerrado.")
    }
}

@Composable
fun PedidoScreen(modifier: Modifier = Modifier) {
    var nombreProducto by remember { mutableStateOf("") }
    var cantidad by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var mensajeConfirmacion by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) } // Controla el estado del AlertDialog

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Registro de Pedido", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(
            value = nombreProducto,
            onValueChange = { nombreProducto = it },
            label = { Text("Nombre del Producto") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección de Despacho") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    if (nombreProducto.isNotEmpty() && cantidad.isNotEmpty() && direccion.isNotEmpty()) {
                        mensajeConfirmacion = "Pedido registrado: $nombreProducto ($cantidad) - Enviado a: $direccion"
                        Log.d("Pedido", mensajeConfirmacion)
                    } else {
                        mensajeConfirmacion = "Por favor, completa todos los campos."
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Registrar Pedido")
            }

            Button(
                onClick = {
                    if (mensajeConfirmacion.isNotEmpty()) {
                        Log.d("Pedido", "Mostrando alerta: $mensajeConfirmacion") // Registrar en la consola
                        showDialog = true // Mostrar la alerta
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Mostrar Pedido")
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    Button(onClick = { showDialog = false }) {
                        Text("Aceptar")
                    }
                },
                title = { Text("Detalles del Pedido") },
                text = { Text(mensajeConfirmacion) }
            )
        }

        if (mensajeConfirmacion.isNotEmpty()) {
            Text(text = mensajeConfirmacion, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PedidoScreenPreview() {
    MyTerceraTheme {
        PedidoScreen()
    }
}
