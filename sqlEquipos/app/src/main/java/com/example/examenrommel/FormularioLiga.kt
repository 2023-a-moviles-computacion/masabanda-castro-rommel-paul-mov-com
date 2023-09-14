package com.example.examenrommel

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.annotation.RequiresApi

class FormularioLiga: AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_liga)

        EBaseDeDatos.BDatos = ESqliteHelper(this)

        val botonAceptarCreacion = findViewById<Button>(R.id.btn_aceptar_liga_formulario)
        botonAceptarCreacion.setOnClickListener {
            var nombre = findViewById<EditText>(R.id.editText_nombre_Liga).text.toString()
            var pais = findViewById<EditText>(R.id.editText_pais_Liga).text.toString()
            var temporadaEnCurso = findViewById<EditText>(R.id.editText_temporada_Liga).text.toString().toBoolean()
            var premioAlCampeon = findViewById<EditText>(R.id.editText_premio_Liga).text.toString().toDouble()
            var fechaDeInicioTexto = findViewById<EditText>(R.id.editText_finicio_Liga).text.toString()

            EBaseDeDatos.BDatos!!.crearLigaEquipo(nombre,pais,temporadaEnCurso,premioAlCampeon,fechaDeInicioTexto)
            actualizarListaLigas()
        }

    }

    private fun actualizarListaLigas() {
        val listViewLigaDeFutbol = findViewById<ListView>(R.id.list_view_ligas)

        if (listViewLigaDeFutbol!= null) {
            val adaptador = listViewLigaDeFutbol.adapter as ArrayAdapter<EquipoFutbol>?
            if (adaptador != null) {
                adaptador.notifyDataSetChanged()
            }
        } else{
            finish()
        }
    }
}