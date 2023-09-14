package com.example.examenrommel

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance

class EBaseDeDatos {
    //Tener una única instancia de ESqliteHelper en toda la aplicación
    companion object{
        var BDatos: ESqliteHelper?=null
        // Comprobar si ya existe una instancia
        fun getInstance(context: Context): ESqliteHelper {
            if (instance == null) {
                // Si no existe, crear una nueva instancia con el contexto proporcionado
                BDatos = ESqliteHelper(context.applicationContext)
            }
            return BDatos!!
        }
    }
}