package com.example.exameniib_masabanda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FormularioLiga : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_liga)

        val botonAceptarCreacion = findViewById<Button>(R.id.btn_aceptar_liga_formulario)
        botonAceptarCreacion.setOnClickListener {
            crearLigaFutbol()
        }
    }

    private fun crearLigaFutbol(){
        val intentDevolverParametros = Intent()

        val nombre = findViewById<EditText>(R.id.editText_nombre_Liga).text.toString()
        intentDevolverParametros.putExtra("nombreModificado", nombre)

        val pais = findViewById<EditText>(R.id.editText_pais_Liga).text.toString()
        intentDevolverParametros.putExtra("paisModificado", pais)

        val temporadaEnCurso = findViewById<EditText>(R.id.editText_temporada_Liga).text.toString().toBoolean()
        intentDevolverParametros.putExtra("temporadaEnCursoModificada", temporadaEnCurso)

        val premioAlCampeon = findViewById<EditText>(R.id.editText_premio_Liga).text.toString().toDouble()
        intentDevolverParametros.putExtra("premioModificado", premioAlCampeon)

        val fechaDeInicioTexto = findViewById<EditText>(R.id.editText_finicio_Liga).text.toString()
        intentDevolverParametros.putExtra("fechaInicioModificada",  fechaDeInicioTexto)

        //resultado que la actividad enviará de vuelta a la actividad MainActivity
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }

}