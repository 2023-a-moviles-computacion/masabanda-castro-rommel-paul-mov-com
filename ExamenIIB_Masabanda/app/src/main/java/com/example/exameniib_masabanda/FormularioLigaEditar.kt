package com.example.exameniib_masabanda

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi

class FormularioLigaEditar : AppCompatActivity() {

    var id: String? = null

    @SuppressLint("CutPasteId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_liga_editar)

        this.id = intent.getStringExtra("id")
        val nombre = intent.getStringExtra("nombre")
        val pais = intent.getStringExtra("pais")
        val temporadaEnCurso = ""+intent.getBooleanExtra("temporadaEnCurso", false)
        val premioAlCampeon = ""+intent.getDoubleExtra("premio", 12030.2)
        val fechaDeInicioTexto = intent.getStringExtra("fechaInicio")


        val tid = findViewById<EditText>(R.id.editText_id_Liga_editar)
        tid.setText(this.id, TextView.BufferType.EDITABLE)

        val tnombre = findViewById<EditText>(R.id.editText_nombre_Liga_editar)
        tnombre.setText(nombre, TextView.BufferType.EDITABLE)

        val tpais = findViewById<EditText>(R.id.editText_pais_Liga_editar)
        tpais.setText(pais, TextView.BufferType.EDITABLE)

        val ttemporadaEnCurso = findViewById<EditText>(R.id.editText_temporada_Liga_editar)
        ttemporadaEnCurso.setText(temporadaEnCurso.toString(), TextView.BufferType.EDITABLE)

        val tpremioAlCampeon = findViewById<EditText>(R.id.editText_premio_Liga_editar)
        tpremioAlCampeon.setText(premioAlCampeon.toString(), TextView.BufferType.EDITABLE)

        val tfechaDeInicioTexto = findViewById<EditText>(R.id.editText_finicio_Liga_editar)
        tfechaDeInicioTexto.setText(fechaDeInicioTexto, TextView.BufferType.EDITABLE)


        tid.isEnabled = false


        val botonAceptarEditar = findViewById<Button>(R.id.btn_aceptar_liga_formulario_editar)
        botonAceptarEditar.setOnClickListener {

            try {
                devolverRespuesta()
            } catch (e: Exception) {
                val errorMessage = "Ocurrió un error: ${e.message}"
                Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
            }


        }
    }

    private fun devolverRespuesta() {
        val intentDevolverParametros = Intent()

        intentDevolverParametros.putExtra("idDevuelto", this.id)

        val textNombre = findViewById<EditText>(R.id.editText_nombre_Liga_editar)
        intentDevolverParametros.putExtra("nombreModificado", textNombre.text.toString())

        val textPais = findViewById<EditText>(R.id.editText_pais_Liga_editar)
        intentDevolverParametros.putExtra("paisModificado", textPais.text.toString())

        val temporada = findViewById<EditText>(R.id.editText_temporada_Liga_editar)
        intentDevolverParametros.putExtra("temporadaEnCursoModificada", temporada.text.toString().toBoolean())

        val premio = findViewById<EditText>(R.id.editText_premio_Liga_editar)
        intentDevolverParametros.putExtra("premioModificado", premio.text.toString().toDouble())

        val fecha = findViewById<EditText>(R.id.editText_finicio_Liga_editar)
        intentDevolverParametros.putExtra("fechaInicioModificada", fecha.text.toString())

        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()
    }



}