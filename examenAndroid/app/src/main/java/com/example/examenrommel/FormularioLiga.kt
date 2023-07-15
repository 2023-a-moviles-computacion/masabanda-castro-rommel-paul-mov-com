package com.example.examenrommel

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.annotation.RequiresApi

class FormularioLiga: AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    var arrelo = BDMemoria.listaLigas

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_liga)

        val botonAceptarCreacion = findViewById<Button>(R.id.btn_aceptar_liga_formulario)
        botonAceptarCreacion.setOnClickListener {
            var id = findViewById<EditText>(R.id.editText_id_Liga).text.toString().toInt()
            var nombre = findViewById<EditText>(R.id.editText_nombre_Liga).text.toString()
            var pais = findViewById<EditText>(R.id.editText_pais_Liga).text.toString()
            var temporadaEnCurso = findViewById<EditText>(R.id.editText_temporada_Liga).text.toString().toBoolean()
            var premioAlCampeon = findViewById<EditText>(R.id.editText_premio_Liga).text.toString().toDouble()
            var fechaDeInicioTexto = findViewById<EditText>(R.id.editText_finicio_Liga).text.toString()

            arrelo.add(LigaDeFutbol(id,nombre,pais,temporadaEnCurso,premioAlCampeon,fechaDeInicioTexto))
            //irActividad(MainActivity::class.java)
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