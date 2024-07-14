package com.umaee.mastercalculator

import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    val suma = "+"
    val resta = "-"
    val multiplicacion = "*"
    val division = "/"
    val porcentaje = "%"

    var operacionActual = ""

    var firstNumber:Double = Double.NaN
    var secondNumber:Double = Double.NaN

    lateinit var textViewtemp:TextView
    lateinit var textViewresult: TextView

    lateinit var formatoDecimal:DecimalFormat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        formatoDecimal = DecimalFormat("#.########")
        textViewtemp = findViewById(R.id.textViewtemp)
        textViewresult = findViewById(R.id.textViewresult)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun changeOperator(b: View){
        if(textViewtemp.text.isNotEmpty() || firstNumber.toString() != "NaN"){

        calculate()
        val boton:Button = b as Button
        if(boton.text.toString().trim()=="÷"){
            operacionActual = "/"
        } else
            if(boton.text.toString().trim()=="X"){
            operacionActual = "*"
        } else{
            operacionActual = boton.text.toString().trim()
        }
        textViewresult.text = formatoDecimal.format(firstNumber) + operacionActual
        textViewtemp.text = ""
        }
    }

    fun calculate(){
        try {
            if(firstNumber.toString() != "NaN" ){
                if (textViewtemp.text.toString().isEmpty()){
                    textViewtemp.text = textViewresult.text.toString()
                }
                secondNumber = textViewtemp.text.toString().toDouble()
                textViewtemp.text = ""

                when(operacionActual){
                    "+" -> firstNumber = (firstNumber + secondNumber)
                    "-" -> firstNumber = (firstNumber - secondNumber)
                    "*" -> firstNumber = (firstNumber * secondNumber)
                    "/" -> firstNumber = (firstNumber / secondNumber)
                    "%" -> firstNumber = (firstNumber % secondNumber)
                }
            } else {
                firstNumber = textViewtemp.text.toString().toDouble()
            }
        } catch (e:Exception){

        }
    }

    fun selectNumber(b: View){
        val boton:Button = b as Button
        textViewtemp.text = textViewtemp.text.toString() + boton.text.toString()

    }
    fun equals(b: View){
        calculate()
        textViewresult.text = formatoDecimal.format(firstNumber)
        //firstNumber = Double.NaN
        operacionActual = ""
    }

    fun borrar(b: View){
        calculate()
        val boton:Button = b as Button
        if(boton.text.toString().trim()=="C"){
            if (textViewtemp.text.toString().isNotEmpty()){
                var actualData = textViewtemp.text.toString() as CharSequence
                textViewtemp.text = actualData.subSequence(0, actualData.length - 1)
            } else {
                firstNumber = Double.NaN
                secondNumber = Double.NaN
                textViewresult.text = ""
                textViewtemp.text = ""
            }
        } else
            if(boton.text.toString().trim()=="A/C"){
                firstNumber = Double.NaN
                secondNumber = Double.NaN
                textViewresult.text = ""
                textViewtemp.text = ""
            } else{
                operacionActual = boton.text.toString().trim()
            }
    }
}