package entidades
import java.text.SimpleDateFormat
import java.util.Date

data class EquipoDeFutbol(
    var nombre: String,
    var campeonatos: Int,
    var poseeEstadio: Boolean,
    var precioDelEquipo: Float,
    var fechaDeCreacion : Date
)
{


    fun actualizarEquipo(nombre: String, campeonatos: Int, poseeEstadio: Boolean, precioDelEquipo: Float, fechaDeCreacion: Date){
        this.nombre = nombre
        this.campeonatos = campeonatos
        this.poseeEstadio = poseeEstadio
        this.precioDelEquipo = precioDelEquipo
        this.fechaDeCreacion = fechaDeCreacion
    }
}


