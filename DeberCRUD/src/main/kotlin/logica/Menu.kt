package logica

import java.text.SimpleDateFormat
import java.util.*

class Menu() {

    private val manejoDeArchivo = ManejoDeArchivo("datos.txt")
    private val crud = GestorCrud(manejoDeArchivo)

    fun menuInicial() {
        while (true) {
            println("Seleccione una opción:")
            println("1. Ligas de futbol")
            println("2. Equipos de futbol")
            println("3. Salir")

            val opcion = readLine()?.toIntOrNull()

            when (opcion) {
                1 -> {
                    var a: Boolean = true;
                    do {
                        println("--------Ligas de futbol--------")
                        val opc = mensajeOpciones()
                        if (opc != null) {
                            opcionesLiga(opc)
                            if (opc == 5) {
                                a = false
                            }
                        }
                    } while (a)
                }

                2 -> {
                    var a: Boolean = true;
                    do {
                        println("--------Equipos de futbol--------")
                        val opc = mensajeOpciones()
                        if (opc != null) {
                            opcionesEquipo(opc)
                            if (opc == 5) {
                                a = false
                            }
                        }
                    } while (a)
                }

                3 -> {
                    println("Hasta luego!")
                    return
                }

                else -> {
                    println("Opción inválida. Intente de nuevo.")
                }
            }
        }
    }

    private fun mensajeOpciones(): Int? {
        println("   1. Leer")
        println("   2. Crear")
        println("   3. Editar")
        println("   4. Eliminar")
        println("   5. Regresar")
        val opcion = readLine()?.toIntOrNull()
        return opcion
    }

    private fun opcionesLiga(opc: Int) {
        when (opc) {
            1 -> {
                crud.mostrarLigas()
            }

            2 -> {
                println("Ingrese el nombre de la liga:")
                val nombre = readLine()
                println("Ingrese el pais al que pertenece la liga :")
                var pais = readLine()
                println("La temporada de la liga esta en curso?, digita 1 para verdadero y 0 para falso")
                var temporada = readLine().toString().toInt();
                var temporadaRespuesta:Boolean = false
                if (temporada == 1){
                    temporadaRespuesta=true
                }
                println("Digita el premio que se le otorga al ganador de la liga")
                var premio = readLine().toString().toDouble();
                println("Digita la fecha de inicio de la temporada de la liga en formato dd/MM/yyyy")
                val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
                var fecha:String = readLine().toString();
                val fechaB: Date = formatoFecha.parse(fecha)
                println("Ingrese los nombres de los equipos separados por comas sin espacios:")
                val nombresEquipos = readLine()?.split(",") ?: emptyList()
                crud.crearLiga(nombre ?: "", pais?: "",temporadaRespuesta,premio,fechaB,nombresEquipos)
            }

            3 -> {
                println("Ingrese el nombre de la liga que desea editar:")
                val nombre = readLine().toString()
                crud.editarLiga(nombre)
            }

            4 -> {
                println("Ingrese el nombre de la liga a eliminar:")
                val nombre = readLine()
                crud.eliminarLiga(nombre ?: "")
            }

            5 -> {
                println("Volviendo al inicio")
                return
            }

            else -> {
                println("Opción inválida. Intente de nuevo.")
            }
        }
    }

    private fun opcionesEquipo(opc: Int) {
        when (opc) {
            1 -> {
                crud.mostrarEquipos()
            }

            2 -> {
                println("Ingrese el nombre del equipo:")
                val nombre = readLine()
                println("Ingrese el numero de campeonatos totales del equipo:")
                var campeonato:Int = readLine().toString().toInt()
                println("El equipo posee estadio propio, digita 1 para verdadero y 0 para falso")
                var estadio = readLine().toString().toInt();
                var estadioRespuesta:Boolean = false
                if (estadio == 1){
                    estadioRespuesta=true
                }
                println("Digita el precio en el que esta valorado el equipo")
                var precio = readLine().toString().toFloat();
                println("Digita la fecha de creación del equipo en formato dd/MM/yyyy")
                val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
                var fecha:String = readLine().toString();
                val fechaB: Date = formatoFecha.parse(fecha)
                crud.crearEquipo(nombre ?: "", campeonato, estadioRespuesta,precio,fechaB)
            }

            3 -> {
                println("Ingrese el nombre del equipo que desea editar:")
                val nombre = readLine().toString()
                crud.editarEquipo(nombre)
            }

            4 -> {
                println("Ingrese el nombre del equipo a eliminar:")
                val nombre = readLine()
                crud.eliminarEquipo(nombre ?: "")
            }

            5 -> {
                println("Volviendo al inicio")
                return
            }

            else -> {
                println("Opción inválida. Intente de nuevo.")
            }
        }
    }
}