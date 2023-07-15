package com.example.examenrommel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi

class FormularioLigaEditar : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    var arreglo = BDMemoria.listaLigas


    @SuppressLint("CutPasteId")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_liga_editar)


        val ligaSeleccionada = intent.getSerializableExtra("liga") as LigaDeFutbol

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
        val ind = buscarIndice(idLiga)

        id.isEnabled = false

        val botonAceptarEditar = findViewById<Button>(R.id.btn_aceptar_liga_formulario_editar)
        botonAceptarEditar.setOnClickListener {
            arreglo[ind].id = findViewById<EditText>(R.id.editText_id_Liga_editar).text.toString().toInt()
            arreglo[ind].nombre = findViewById<EditText>(R.id.editText_nombre_Liga_editar).text.toString()
            arreglo[ind].pais = findViewById<EditText>(R.id.editText_pais_Liga_editar).text.toString()
            arreglo[ind].temporadaEnCurso = findViewById<EditText>(R.id.editText_temporada_Liga_editar).text.toString().toBoolean()
            arreglo[ind].premioAlCampeon = findViewById<EditText>(R.id.editText_premio_Liga_editar).text.toString().toDouble()
            arreglo[ind].fechaDeInicioTexto = findViewById<EditText>(R.id.editText_finicio_Liga_editar).text.toString()
            //irActividad(MainActivity::class.java)
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun buscarIndice(idBuscado:Int):Int{

        var idEncontrado: Int = -1

        for (liga in arreglo) {
            if (liga.id == idBuscado) {
                idEncontrado = arreglo.indexOf(liga)
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