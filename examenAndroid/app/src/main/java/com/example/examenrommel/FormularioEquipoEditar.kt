package com.example.examenrommel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi

class FormularioEquipoEditar : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    var arregloE = BDMemoria.listaEquipos

    @SuppressLint("CutPasteId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_equipo_editar)


        val equipoSeleccionado = intent.getSerializableExtra("equipo") as EquipoFutbol

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
        val ind = buscarIndice(idEquipo)

        val botonAceptarEditarE = findViewById<Button>(R.id.btn_aceptar_equipo_formulario_Editar)
        botonAceptarEditarE.setOnClickListener {
            arregloE[ind].fkLiga = findViewById<EditText>(R.id.editText_id_Equipo_Editar).text.toString().toInt()
            arregloE[ind].nombre = findViewById<EditText>(R.id.editText_nombre_Equipo_Editar).text.toString()
            arregloE[ind].campeonatos = findViewById<EditText>(R.id.editText_campeonato_Equipo_Editar).text.toString().toInt()
            arregloE[ind].poseeEstadio = findViewById<EditText>(R.id.editText_estadio_Equipo_Editar).text.toString().toBoolean()
            arregloE[ind].precioDelEquipo = findViewById<EditText>(R.id.editText_precio_Equipo_Editar).text.toString().toDouble()
            arregloE[ind].fechaDeCreacionTexto = findViewById<EditText>(R.id.editText_fcreacion_Equipo_Editar).text.toString()
            //irActividad(ListViewEquipos::class.java)
            finish()
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun buscarIndice(nombreBuscado:String):Int{

        var idEncontrado: Int = -1

        for (equipo in arregloE) {
            if (equipo.nombre == nombreBuscado) {
                idEncontrado = arregloE.indexOf(equipo)
                break
            }
        }
        return idEncontrado
    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase);
        startActivity(intent);
    }
}