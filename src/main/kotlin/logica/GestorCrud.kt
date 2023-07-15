package logica

import entidades.EquipoDeFutbol
import entidades.LigaDeFutbol
import java.text.SimpleDateFormat
import java.util.*


class GestorCrud(
    private val manejoDeArchivo: ManejoDeArchivo
){
    private val equipos: MutableList<EquipoDeFutbol> = mutableListOf()
    private val ligas: MutableList<LigaDeFutbol> = mutableListOf()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        val contenido = manejoDeArchivo.leerArchivo()
        val lineas = contenido.split("\n")
        for (linea in lineas) {
            val partes = linea.split(",")
            if (partes.size == 5) {


                val dateString = partes[4].toString()
                val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
                val date: Date = dateFormat.parse(dateString)


                val equipo = EquipoDeFutbol(partes[0], partes[1].toInt(),partes[2].toBoolean(),partes[3].toFloat(),date)
                equipos.add(equipo)
            } else if (partes.size >= 6) {
                val nombreLiga = partes[0]
                val pais = partes[1]
                val temporadaEnCurso = partes[2].toBoolean()
                val premioAlCampeon = partes[3].toDouble()
                val fechaInicio = partes[4].toString()
                val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US)
                val fechaInicioDate: Date = dateFormat.parse(fechaInicio)
                val equiposLiga = partes.subList(5, partes.size)
                    .map { nombreEquipo -> equipos.find { it.nombre == nombreEquipo } }
                    .filterNotNull()

                val liga = LigaDeFutbol(nombreLiga, pais, temporadaEnCurso, premioAlCampeon, fechaInicioDate, equiposLiga.toMutableList())
                ligas.add(liga)
            }
        }
    }

    private fun guardarDatos() {
        val contenido = StringBuilder()
        for (equipo in equipos) {
            contenido.append("${equipo.nombre},${equipo.campeonatos},${equipo.poseeEstadio},${equipo.precioDelEquipo},${equipo.fechaDeCreacion}\n")
        }
        for (liga in ligas) {
            contenido.append("${liga.nombre},${liga.pais},${liga.temporadaEnCurso},${liga.premioAlCampeon},${liga.fechaDeInicio}")
            for (equipo in liga.listaDeEquipos) {
                contenido.append(",${equipo.nombre}")
            }
            contenido.append("\n")
        }

        manejoDeArchivo.escribirArchivo(contenido.toString())
    }

    fun crearEquipo(nombre: String, campeonatos: Int, poseeEstadio: Boolean, precioDelEquipo: Float, fechaDeCreacion: Date) {
        val equipo = EquipoDeFutbol(nombre, campeonatos,poseeEstadio,precioDelEquipo,fechaDeCreacion)
        equipos.add(equipo)
        guardarDatos()
    }

    fun eliminarEquipo(nombre: String) {
        val equipo = equipos.find { it.nombre == nombre }
        if (equipo != null) {
            equipos.remove(equipo)
            ligas.forEach { it.eliminarEquipo(equipo) }
            guardarDatos()
        }
    }


    fun editarEquipo(nombre: String) {
        val equipo = equipos.find { it.nombre == nombre }
        val equipoIndex = equipos.indexOf(equipos.find { it.nombre == nombre })
        if (equipo != null) {
            println("Ingrese opción a cambiar: ")
            println(" 1 - nombre ")
            println(" 2 - campeonatos")
            println(" 3 - posee estadio")
            println(" 4 - precio del equipo")
            println(" 5 - fecha de creación")
            val opc = readLine()?.toIntOrNull()
            var nombre:String="";
            var campeonatos:Int=0;
            var estadioRespuesta:Boolean=false;
            var precioDelEquipo:Float= 0F
            var fechaDeCreacion: Date? = null
            when(opc){
                1->{
                    println("Digita el nombre")
                    nombre = readLine().toString();
                    equipos[equipoIndex].nombre=nombre
                    campeonatos=equipos[equipoIndex].campeonatos
                    estadioRespuesta=equipos[equipoIndex].poseeEstadio
                    precioDelEquipo = equipos[equipoIndex].precioDelEquipo
                    fechaDeCreacion=equipos[equipoIndex].fechaDeCreacion
                }
                2->{
                    println("Digita el numero de campeonatos")
                    campeonatos = readLine()?.toIntOrNull()!!
                    equipos[equipoIndex].campeonatos=campeonatos
                    nombre=equipos[equipoIndex].nombre
                    estadioRespuesta=equipos[equipoIndex].poseeEstadio
                    precioDelEquipo = equipos[equipoIndex].precioDelEquipo
                    fechaDeCreacion=equipos[equipoIndex].fechaDeCreacion
                }
                3->{
                    println("Digita 1 para true y 0 para false")
                    var estadio = readLine().toString().toInt();
                    if (estadio == 1){
                        estadioRespuesta=true
                    }
                    equipos[equipoIndex].poseeEstadio=estadioRespuesta
                    nombre=equipos[equipoIndex].nombre
                    estadioRespuesta=equipos[equipoIndex].poseeEstadio
                    precioDelEquipo = equipos[equipoIndex].precioDelEquipo
                    fechaDeCreacion=equipos[equipoIndex].fechaDeCreacion
                }
                4->{
                    println("Digita el precio del equipo")
                    var precio = readLine().toString().toFloat();
                    equipos[equipoIndex].precioDelEquipo=precio
                    nombre=equipos[equipoIndex].nombre
                    campeonatos=equipos[equipoIndex].campeonatos
                    estadioRespuesta=equipos[equipoIndex].poseeEstadio
                    fechaDeCreacion=equipos[equipoIndex].fechaDeCreacion
                }
                5->{
                    println("Digita la fecha de creación del equipo en formato dd/MM/yyyy")
                    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
                    var fecha:String = readLine().toString();
                    val fechaB: Date = formatoFecha.parse(fecha)
                    equipos[equipoIndex].fechaDeCreacion=fechaB
                    nombre=equipos[equipoIndex].nombre
                    campeonatos=equipos[equipoIndex].campeonatos
                    precioDelEquipo=equipos[equipoIndex].precioDelEquipo
                    estadioRespuesta=equipos[equipoIndex].poseeEstadio
                }
                else -> {
                    println("Opción inválida. Intente de nuevo.")
                }
            }
            ligas.forEach {
                if (fechaDeCreacion != null) {
                    it.actualizarEquipo(equipo,nombre,campeonatos,estadioRespuesta,precioDelEquipo,fechaDeCreacion)
                }
            }
            guardarDatos()
        } else {
            println("El equipo no existe")
        }
    }

    fun crearLiga(nombre: String, pais:String, temporadaEnCurso:Boolean, premioAlCampeon:Double, fechaIncio:Date, nombresEquipos: List<String>) {

        val equiposLiga = nombresEquipos.mapNotNull { nombreEquipo -> equipos.find { it.nombre == nombreEquipo } }
        val liga = LigaDeFutbol(nombre, pais, temporadaEnCurso, premioAlCampeon, fechaIncio, equiposLiga.toMutableList())
        ligas.add(liga)
        guardarDatos()
    }

    fun eliminarLiga(nombre: String) {
        val equipoAEliminar : MutableList<String> = mutableListOf();
        val liga = ligas.find { it.nombre == nombre }
        if (liga != null) {
            for (equipo in liga.listaDeEquipos) {
                for (i in 0 until liga.listaDeEquipos.size){
                    if (liga.listaDeEquipos[i].nombre == equipo.nombre)

                    equipoAEliminar.add(equipo.nombre)
                }
            }
            //No puedo eliminar directo mientras recorro la lista, por lo cual se creo una
            //lista auxiliar y con base en esta se va iterando y eliminando de la lista
            for(a in equipoAEliminar){
                eliminarEquipo(a)
            }
            ligas.remove(liga)
            guardarDatos()
        }
    }

    fun editarLiga(nombre: String){
        val liga = ligas.find { it.nombre == nombre }
        var bandera = false
        if (liga!=null){
            do {
                println("Digite la opción que desea editar 1 nombre")
                println("Ingrese opción a cambiar: ")
                println(" 1 - nombre ")
                println(" 2 - pais")
                println(" 3 - torneo en curso")
                println(" 4 - premio al campeón")
                println(" 5 - fecha de inicio")
                val opc = readLine()?.toIntOrNull();
                when(opc){
                    1->{
                        println("Ingrese el nombre de la liga")
                        val nombre = readLine().toString();
                        liga.nombre=nombre
                    }
                    2->{
                        println("Ingrese el pais al que pertenece la liga :")
                        var pais = readLine().toString()
                        liga.pais=pais
                    }
                    3->{
                        println("La temporada de la liga esta en curso?, digita 1 para verdadero y 0 para falso")
                        var temporada = readLine().toString().toInt();
                        var temporadaRespuesta:Boolean = false
                        if (temporada == 1){
                            temporadaRespuesta=true
                        }
                        liga.temporadaEnCurso=temporadaRespuesta
                    }
                    4->{
                        println("Digita el premio que se le otorga al ganador de la liga")
                        var premio = readLine().toString().toDouble();
                        liga.premioAlCampeon=premio
                    }
                    5->{
                        println("Digita la fecha de inicio de la temporada de la liga en formato dd/MM/yyyy")
                        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
                        var fecha:String = readLine().toString();
                        val fechaB: Date = formatoFecha.parse(fecha)
                        liga.fechaDeInicio=fechaB
                    }
                    else ->{
                        println("Opción inválida. Intente de nuevo.")
                    }
                }
                guardarDatos()
            }while (bandera)
        }else{
            println("La Liga no existe")
        }
    }

    fun mostrarEquipos() {
        for (equipo in equipos) {
            println("${equipo.nombre} - ${equipo.campeonatos} - ${equipo.poseeEstadio} - ${equipo.precioDelEquipo} - ${equipo.fechaDeCreacion}")
        }
    }

    fun mostrarLigas() {
        for ( liga in ligas) {
            println(liga.nombre +" - "+ liga.pais+" - "+liga.temporadaEnCurso+" - "+liga.premioAlCampeon+" - "+liga.fechaDeInicio)
            for (equipo in liga.listaDeEquipos) {
                println("   - ${equipo.nombre}")
            }
        }
    }
}
