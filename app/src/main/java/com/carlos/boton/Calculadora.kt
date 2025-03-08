package com.carlos.boton

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Calculadora : AppCompatActivity() {

    private lateinit var textView: TextView
    private var currentInput = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boton)

        textView = findViewById(R.id.textView)

        //----------- btn numeros
        findViewById<Button>(R.id.uno).setOnClickListener { agregarInput("1") }
        findViewById<Button>(R.id.dos).setOnClickListener { agregarInput("2") }
        findViewById<Button>(R.id.tres).setOnClickListener { agregarInput("3") }
        findViewById<Button>(R.id.cuatro).setOnClickListener { agregarInput("4") }
        findViewById<Button>(R.id.cinco).setOnClickListener { agregarInput("5") }
        findViewById<Button>(R.id.seis).setOnClickListener { agregarInput("6") }
        findViewById<Button>(R.id.siete).setOnClickListener { agregarInput("7") }
        findViewById<Button>(R.id.ocho).setOnClickListener { agregarInput("8") }
        findViewById<Button>(R.id.nueve).setOnClickListener { agregarInput("9") }
        findViewById<Button>(R.id.cero).setOnClickListener { agregarInput("0") }

        //----------- btn operadores
        findViewById<Button>(R.id.suma).setOnClickListener { agregarInput("+") }
        findViewById<Button>(R.id.menos).setOnClickListener { agregarInput("-") }
        findViewById<Button>(R.id.multiplicar).setOnClickListener { agregarInput("*") }
        findViewById<Button>(R.id.dividir).setOnClickListener { agregarInput("/") }
        findViewById<Button>(R.id.punto).setOnClickListener { agregarInput(".") }

        // Funcionalidades adicionales
        findViewById<Button>(R.id.borrar).setOnClickListener { limpiarInput() }
        findViewById<Button>(R.id.igual).setOnClickListener { calcularResultado() }
    }

    // Añadir al input y actualizar el TextView
    private fun agregarInput(value: String) {
        currentInput += value
        textView.text = currentInput
    }

    // Limpiar el input
    private fun limpiarInput() {
        currentInput = ""
        textView.text = ""
    }

    // Función para calcular el resultado
    private fun calcularResultado() {
        try {
            val resultado = evaluarExpresion(currentInput)
            textView.text = resultado.toString()
            currentInput = resultado.toString()
        } catch (e: Exception) {
            textView.text = "Error"
            currentInput = ""
        }
    }

    // evaluar operaciones
    private fun evaluarExpresion(expression: String): Double {
        // funcion regular
        val tokens = expression.split("(?=[-+*/])|(?<=[-+*/])".toRegex())

        if (tokens.size < 3) throw IllegalArgumentException("Expresión incompleta")

        var resultado = tokens[0].toDouble()

        var operador = ""

        for (token in tokens.drop(1)) {
            when (token) {
                "+", "-", "*", "/" -> operador = token
                else -> {
                    val valor = token.toDouble()
                    when (operador) {
                        "+" -> resultado += valor
                        "-" -> resultado -= valor
                        "*" -> resultado *= valor
                        "/" -> {
                            if (valor == 0.0) throw ArithmeticException("División por cero")
                            resultado /= valor
                        }
                    }
                }
            }
        }

        return resultado
    }
}
