package entidades

import java.util.Date

data class LigaDeFutbol(
    //Val no puedo reasignar a un nuevo valor
    var nombre: String,
    var pais: String,
    var temporadaEnCurso: Boolean,
    var premioAlCampeon: Double,
    var fechaDeInicio: Date,
    var listaDeEquipos: MutableList<EquipoDeFutbol> = mutableListOf()
    )
    {
        fun eliminarEquipo(equipo: EquipoDeFutbol) {
            listaDeEquipos.remove(equipo)
        }

        fun actualizarEquipo(equipo: EquipoDeFutbol, nombre: String, campeonatos:Int, poseeEstadio:Boolean,precioDelEquipo:Float,fechaDeCreacion:Date){
            equipo.actualizarEquipo(nombre,campeonatos,poseeEstadio,precioDelEquipo,fechaDeCreacion)
        }
    }

