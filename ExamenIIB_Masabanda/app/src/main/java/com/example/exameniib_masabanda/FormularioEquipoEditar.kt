package com.example.exameniib_masabanda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class FormularioEquipoEditar : AppCompatActivity() {

    var id: String? = null
    var idLiga: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_equipo_editar)

        this.idLiga = intent.getStringExtra("idLiga")
        this.id = intent.getStringExtra("id")
        val nombre = intent.getStringExtra("nombre")
        val campeonatos = ""+intent.getIntExtra("campeonatos",0)
        val estadio = ""+intent.getBooleanExtra("estadio", false)
        val precio = ""+intent.getDoubleExtra("precio",0.0)
        val fechaCreacion = intent.getStringExtra("fechaCreacion")


        val tnombre = findViewById<EditText>(R.id.editText_nombre_Equipo_Editar)
        tnombre.setText(nombre, TextView.BufferType.EDITABLE)

        val tcampeonatos = findViewById<EditText>(R.id.editText_campeonato_Equipo_Editar)
        tcampeonatos.setText(campeonatos, TextView.BufferType.EDITABLE)

        val tposeeEstadio = findViewById<EditText>(R.id.editText_estadio_Equipo_Editar)
        tposeeEstadio.setText(estadio,TextView.BufferType.EDITABLE)

        val tprecioEquipo = findViewById<EditText>(R.id.editText_precio_Equipo_Editar)
        tprecioEquipo.setText(precio,TextView.BufferType.EDITABLE)

        val tfechaCreacion = findViewById<EditText>(R.id.editText_fcreacion_Equipo_Editar)
        tfechaCreacion.setText(fechaCreacion,TextView.BufferType.EDITABLE)

        val botonAceptarEditarE = findViewById<Button>(R.id.btn_aceptar_equipo_formulario_Editar)
        botonAceptarEditarE.setOnClickListener {
            devolverRespuesta()
        }

    }

    private fun devolverRespuesta(){

        val intentDevlverParametros = Intent()

        intentDevlverParametros.putExtra("idLigaDevuelto", this.idLiga)

        intentDevlverParametros.putExtra("idDevuelto", this.id)

        val tnombre = findViewById<EditText>(R.id.editText_nombre_Equipo_Editar).text.toString()
        intentDevlverParametros.putExtra("nombreModificado", tnombre)

        val tcampeonatos = findViewById<EditText>(R.id.editText_campeonato_Equipo_Editar).text.toString().toInt()
        intentDevlverParametros.putExtra("campeonatosModificado",tcampeonatos)

        val tposeeEstadio = findViewById<EditText>(R.id.editText_estadio_Equipo_Editar).text.toString().toBoolean()
        intentDevlverParametros.putExtra("estadioModificado", tposeeEstadio)

        val tprecioEquipo = findViewById<EditText>(R.id.editText_precio_Equipo_Editar).text.toString().toDouble()
        intentDevlverParametros.putExtra("precioModificado",tprecioEquipo)

        val tfechaCreacion = findViewById<EditText>(R.id.editText_fcreacion_Equipo_Editar).text.toString()
        intentDevlverParametros.putExtra("fechaCreacionModificada",tfechaCreacion)

        // devuelve los datos editados
        setResult(
            RESULT_OK,
            intentDevlverParametros
        )

        finish()

    }


}