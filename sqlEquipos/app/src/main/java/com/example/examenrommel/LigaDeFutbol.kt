package com.example.examenrommel

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class LigaDeFutbol (
    var id: Int,
    var nombre: String?,
    var pais: String?,
    var temporadaEnCurso: Boolean?,
    var premioAlCampeon: Double?,
    var fechaDeInicioTexto: String?,

): Serializable {
    var fechaDeInicio: LocalDate? = null
    init {
        val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        fechaDeInicio = LocalDate.parse(fechaDeInicioTexto, formato)
    }

    override fun toString(): String {
        return "► Nombre: ${nombre}\n\t  País: ${pais}\n\t  T en curso: ${temporadaEnCurso}\n\t  Premio: ${premioAlCampeon}\n\t  F. Inicio: ${fechaDeInicioTexto}"
    }

}