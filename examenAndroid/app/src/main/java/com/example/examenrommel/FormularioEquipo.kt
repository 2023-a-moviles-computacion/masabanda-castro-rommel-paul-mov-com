package com.example.examenrommel

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi

class FormularioEquipo : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    var arregloE = BDMemoria.listaEquipos

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_equipo)

        val ligaSeleccionada = intent.getIntExtra("idLiga", -1)
        var idLiga = findViewById<EditText>(R.id.editText_id_Equipo)
        idLiga.setText(ligaSeleccionada.toString())
        idLiga.isEnabled = false

        Log.i("TAG", "Mensaje informativo padre_FormularioEquipo ${ligaSeleccionada}")

        val botonAceptarCreacionEquipo = findViewById<Button>(R.id.btn_aceptar_equipo_formulario)
        botonAceptarCreacionEquipo.setOnClickListener {
            var id = findViewById<EditText>(R.id.editText_id_Equipo).text.toString().toInt()
            var nombre = findViewById<EditText>(R.id.editText_nombre_Equipo).text.toString()
            var campeonatos = findViewById<EditText>(R.id.editText_campeonato_Equipo).text.toString().toInt()
            var poseeEstadio = findViewById<EditText>(R.id.editText_estadio_Equipo).text.toString().toBoolean()
            var precioEquipo = findViewById<EditText>(R.id.editText_precio_Equipo).text.toString().toDouble()
            var fechaDeCreacionTexto = findViewById<EditText>(R.id.editText_fcreacion_Equipo).text.toString()

            Log.i("TAG", "Mensaje informativo padre_FormularioEquipo2 ${id}")
            arregloE.add(EquipoFutbol(id,nombre,campeonatos,poseeEstadio,precioEquipo,fechaDeCreacionTexto))
            //irActividad(ListViewEquipos::class.java)
            finish()
        }
    }

    fun irActividad(
        clase:Class<*>
    ){
        val intent = Intent(this, clase);
        startActivity(intent);
    }
}