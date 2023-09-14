package com.example.examenrommel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.annotation.RequiresApi

class FormularioEquipoEditar : AppCompatActivity() {

    @SuppressLint("CutPasteId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_equipo_editar)

        EBaseDeDatos.BDatos = ESqliteHelper(this)

        val equipoSeleccionado = intent.getSerializableExtra("equipoID") as EquipoFutbol
        val equipoID = equipoSeleccionado.id

        val id = findViewById<EditText>(R.id.editText_id_Equipo_Editar)
        val nombre = findViewById<EditText>(R.id.editText_nombre_Equipo_Editar)
        val campeonatos = findViewById<EditText>(R.id.editText_campeonato_Equipo_Editar)
        val poseeEstadio = findViewById<EditText>(R.id.editText_estadio_Equipo_Editar)
        val precioEquipo = findViewById<EditText>(R.id.editText_precio_Equipo_Editar)
        val fechaCreacion = findViewById<EditText>(R.id.editText_fcreacion_Equipo_Editar)

        id.setText(equipoSeleccionado.fkLiga.toString())
        nombre.setText(equipoSeleccionado.nombre.toString())
        campeonatos.setText(equipoSeleccionado.campeonatos.toString())
        poseeEstadio.setText(equipoSeleccionado.poseeEstadio.toString())
        precioEquipo.setText(equipoSeleccionado.precioDelEquipo.toString())
        fechaCreacion.setText(equipoSeleccionado.fechaDeCreacionTexto.toString())

        id.isEnabled = false

        val nombreDos = findViewById<EditText>(R.id.editText_nombre_Equipo_Editar)
        val idEquipo = nombreDos.text.toString()

        val botonAceptarEditarE = findViewById<Button>(R.id.btn_aceptar_equipo_formulario_Editar)
        botonAceptarEditarE.setOnClickListener {
            val fkLiga = findViewById<EditText>(R.id.editText_id_Equipo_Editar).text.toString().toInt()
            val nombre = findViewById<EditText>(R.id.editText_nombre_Equipo_Editar).text.toString()
            val campeonatos = findViewById<EditText>(R.id.editText_campeonato_Equipo_Editar).text.toString().toInt()
            val poseeEstadio = findViewById<EditText>(R.id.editText_estadio_Equipo_Editar).text.toString().toBoolean()
            val precioDelEquipo = findViewById<EditText>(R.id.editText_precio_Equipo_Editar).text.toString().toDouble()
            val fechaDeCreacionTexto = findViewById<EditText>(R.id.editText_fcreacion_Equipo_Editar).text.toString()
            EBaseDeDatos.BDatos!!.actualizarEquipoFormulario(
                equipoID,
                nombre,
                campeonatos,
                poseeEstadio,
                precioDelEquipo,
                fechaDeCreacionTexto
            )
            actualizarListaEquipo()
        }

    }

    private fun actualizarListaEquipo() {

        val listViewEquipo = findViewById<ListView>(R.id.listView_equipos)

        if (listViewEquipo != null) {
            val adaptador = listViewEquipo.adapter as ArrayAdapter<EquipoFutbol>?
            if (adaptador != null) {
                adaptador.notifyDataSetChanged()
            }
        } else{
            finish()
        }
    }
}