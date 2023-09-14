package com.example.examenrommel

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class EquipoFutbol(
    var id: Int,
    var fkLiga: Int,
    var nombre: String?,
    var campeonatos: Int?,
    var poseeEstadio: Boolean?,
    var precioDelEquipo: Double?,
    var fechaDeCreacionTexto: String?
):Serializable {
    var fechaDeCreacion: LocalDate? = null

    init {
        val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        fechaDeCreacion = LocalDate.parse(fechaDeCreacionTexto, formato)
    }

    override fun toString(): String {
        return "►${nombre}\n\tcampeonatos: ${campeonatos}\n\testadio: ${poseeEstadio}\n\tprecio: ${precioDelEquipo}\n\tCreación: ${fechaDeCreacionTexto}"
    }
}