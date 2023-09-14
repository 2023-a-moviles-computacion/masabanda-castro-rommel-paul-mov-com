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

class FormularioLigaEditar : AppCompatActivity() {

    @SuppressLint("CutPasteId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_liga_editar)

        EBaseDeDatos.BDatos = ESqliteHelper(this)

        val ligaSeleccionada = intent.getSerializableExtra("ligaID") as LigaDeFutbol
        val ligaID = ligaSeleccionada.id

        val id = findViewById<EditText>(R.id.editText_id_Liga_editar)
        val nombre = findViewById<EditText>(R.id.editText_nombre_Liga_editar)
        val pais = findViewById<EditText>(R.id.editText_pais_Liga_editar)
        val temporadaEnCurso = findViewById<EditText>(R.id.editText_temporada_Liga_editar)
        val premioAlCampeon = findViewById<EditText>(R.id.editText_premio_Liga_editar)
        val fechaDeInicioTexto = findViewById<EditText>(R.id.editText_finicio_Liga_editar)

        id.setText(ligaSeleccionada.id.toString())
        nombre.setText(ligaSeleccionada.nombre.toString())
        pais.setText(ligaSeleccionada.pais.toString())
        temporadaEnCurso.setText(ligaSeleccionada.temporadaEnCurso.toString())
        premioAlCampeon.setText(ligaSeleccionada.premioAlCampeon.toString())
        fechaDeInicioTexto.setText(ligaSeleccionada.fechaDeInicioTexto.toString())

        val idDos = findViewById<EditText>(R.id.editText_id_Liga_editar)
        val idLiga = idDos.text.toString().toInt()
        id.isEnabled = false


        val botonAceptarEditar = findViewById<Button>(R.id.btn_aceptar_liga_formulario_editar)
        botonAceptarEditar.setOnClickListener {

            val nombre = findViewById<EditText>(R.id.editText_nombre_Liga_editar)
            val pais = findViewById<EditText>(R.id.editText_pais_Liga_editar)
            val temporadaEnCurso = findViewById<EditText>(R.id.editText_temporada_Liga_editar)
            val premioAlCampeon = findViewById<EditText>(R.id.editText_premio_Liga_editar)
            val fechaDeInicioTexto = findViewById<EditText>(R.id.editText_finicio_Liga_editar)

            EBaseDeDatos.BDatos!!.actualizarLigaFormulario(
                nombre.text.toString(),
                pais.text.toString(),
                temporadaEnCurso.text.toString().toBoolean(),
                premioAlCampeon.text.toString().toDouble(),
                fechaDeInicioTexto.text.toString(),
                ligaID
            )
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