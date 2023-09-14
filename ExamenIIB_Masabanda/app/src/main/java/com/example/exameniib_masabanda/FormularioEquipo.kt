package com.example.exameniib_masabanda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class FormularioEquipo : AppCompatActivity() {

    var idLiga: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_equipo)

        this.idLiga = intent.getStringExtra("id")!!

        val crearEquipo = findViewById<Button>(R.id.btn_aceptar_equipo_formulario)
        crearEquipo.setOnClickListener(){
            crearAceptarEquipo()
        }
    }

    private  fun crearAceptarEquipo(){

        val intentDevolverParametros = Intent()

        intentDevolverParametros.putExtra("idDevueltoLiga", this.idLiga)

        var nombre = findViewById<EditText>(R.id.editText_nombre_Equipo).text.toString()
        intentDevolverParametros.putExtra("nombreModificado", nombre)

        var campeonatos = findViewById<EditText>(R.id.editText_campeonato_Equipo).text.toString().toInt()
        intentDevolverParametros.putExtra("campeonatosModificado", campeonatos)

        var poseeEstadio = findViewById<EditText>(R.id.editText_estadio_Equipo).text.toString().toBoolean()
        intentDevolverParametros.putExtra("estadioModificado",poseeEstadio)

        var precioEquipo = findViewById<EditText>(R.id.editText_precio_Equipo).text.toString().toDouble()
        intentDevolverParametros.putExtra("precioModificado",precioEquipo)

        var fechaDeCreacionTexto = findViewById<EditText>(R.id.editText_fcreacion_Equipo).text.toString()
        intentDevolverParametros.putExtra("fechaCreacionModificada",fechaDeCreacionTexto)

        setResult(
            RESULT_OK,
            intentDevolverParametros
        )

        finish()

    }




}