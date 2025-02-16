package com.example.mytercera

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    var mostrarAlerta by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Registro de Pedido", style = MaterialTheme.typography.headlineMedium)

        // Campo para el nombre del producto
        OutlinedTextField(
            value = nombreProducto,
            onValueChange = { nombreProducto = it },
            label = { Text("Nombre del Producto") },
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para la cantidad (solo números)
        OutlinedTextField(
            value = cantidad,
            onValueChange = { cantidad = it },
            label = { Text("Cantidad") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Campo para la dirección de despacho
        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección de Despacho") },
            modifier = Modifier.fillMaxWidth()
        )

        // Botón para registrar el pedido
        Button(
            onClick = {
                if (nombreProducto.isNotEmpty() && cantidad.isNotEmpty() && direccion.isNotEmpty()) {
                    mensajeConfirmacion = "Pedido registrado: $nombreProducto ($cantidad) - Enviado a: $direccion"
                    Log.d("Pedido", mensajeConfirmacion)
                } else {
                    mensajeConfirmacion = "Por favor, completa todos los campos."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Registrar Pedido")
        }

        // Botón para mostrar el pedido en alerta y consola
        Button(
            onClick = {
                if (nombreProducto.isNotEmpty() && cantidad.isNotEmpty() && direccion.isNotEmpty()) {
                    Log.d("Pedido", "Pedido: $nombreProducto ($cantidad) - Enviado a: $direccion")
                    mostrarAlerta = true
                } else {
                    mensajeConfirmacion = "No hay información suficiente para mostrar."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Mostrar Pedido")
        }

        // Mensaje de confirmación
        if (mensajeConfirmacion.isNotEmpty()) {
            Text(text = mensajeConfirmacion, style = MaterialTheme.typography.bodyLarge)
        }

        // AlertDialog para mostrar la información del pedido
        if (mostrarAlerta) {
            AlertDialog(
                onDismissRequest = { mostrarAlerta = false },
                title = { Text("Información del Pedido") },
                text = {
                    Text("Producto: $nombreProducto\nCantidad: $cantidad\nDirección: $direccion")
                },
                confirmButton = {
                    Button(onClick = { mostrarAlerta = false }) {
                        Text("Cerrar")
                    }
                }
            )
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
