package com.example.mytercera

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

        // Mensaje de confirmación
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