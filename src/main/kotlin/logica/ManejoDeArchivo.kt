package logica

import java.io.File

class ManejoDeArchivo(
    private val nombreArchivo: String
){
    fun leerArchivo(): String {
        val archivo = File(nombreArchivo)
        return archivo.readText()
    }

    fun escribirArchivo(contenido: String) {
        val archivo = File(nombreArchivo)
        archivo.writeText(contenido)
    }
}
