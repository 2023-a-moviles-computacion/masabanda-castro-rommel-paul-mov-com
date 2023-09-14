package com.example.examenrommel

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

class ESqliteHelper (contexto: Context?, ) : SQLiteOpenHelper(
    contexto,
    "moviles", //nombre BD
    null,
    1
) {
    //Cuando se crea la BD por primera vez
    override fun onCreate(db: SQLiteDatabase?) {
        //Crear la tabla "LIGAFUTBOL"
        val scriptSQLCrearTablaLigaFutbol =
            """
                CREATE TABLE LIGAFUTBOL(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                pais VARCHAR(50),
                temporadaEnCurso BOOLEAN,
                premioAlCampeon DOUBLE,
                fechaDeInicioTexto VARCHAR(50) 
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaLigaFutbol)

        val scriptSQLCrearTablaEquipoFutbol =
            """
                CREATE TABLE EQUIPOFUTBOL(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                ligaID INTEGER,
                nombre VARCHAR(50),
                campeonatos INTEGER,
                poseeEstadio BOOLEAN,
                precioDelEquipo DOUBLE,
                fechaCreacion VARCHAR(50),
                FOREIGN KEY (ligaID) REFERENCES LIGAFUTBOL(id) ON DELETE CASCADE 
                )
            
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEquipoFutbol)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    //Crear una nueva liga en la BD
    fun crearLigaEquipo(
        nombre: String,
        pais: String,
        temporadaEnCurso: Boolean,
        premioAlCampeon: Double,
        fechaDeInicioTexto: String,
    ): Boolean{

        // Convertir el valor booleano temporadaEnCurso a un entero (0 o 1)
        val temp = if(temporadaEnCurso) 1 else 0

        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("pais", pais)
        valoresAGuardar.put("temporadaEnCurso", temp)
        valoresAGuardar.put("premioAlCampeon", premioAlCampeon)
        valoresAGuardar.put("fechaDeInicioTexto", fechaDeInicioTexto)

        Log.w("B1 ", "$temporadaEnCurso")
        Log.w("B2 ", "$temp")

        // Insertar los valores en la tabla "LIGAFUTBOL"
        val resultadoGuardar = basedatosEscritura
            .insert(
                "LIGAFUTBOL",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true

    }

    // Método para eliminar una liga de la BD
    fun eliminarLigaFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        // Eliminar la liga con el ID proporcionado
        val resultadoEliminacion = conexionEscritura
            .delete(
                "LIGAFUTBOL",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarLigaFormulario(
        nombre: String,
        pais: String,
        temporadaEnCurso: Boolean,
        premioAlCampeon: Double,
        fechaDeInicioTexto: String,
        id: Int,
    ): Boolean{

        val temp = if(temporadaEnCurso) 1 else 0

        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("pais", pais)
        valoresAActualizar.put("temporadaEnCurso", temp)
        valoresAActualizar.put("premioAlCampeon", premioAlCampeon)
        valoresAActualizar.put("fechaDeInicioTexto", fechaDeInicioTexto)
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadosActualizacion = conexionEscritura
            .update(
                "LIGAFUTBOL",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadosActualizacion.toInt() == -1) false else true
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerTodasLasLigas(): ArrayList<LigaDeFutbol> {
        // Obtener una instancia de la base de datos en modo lectura
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM LIGAFUTBOL
    """.trimIndent()
        // Ejecutar la consulta
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, null)
        val arregloLigas = ArrayList<LigaDeFutbol>()
        // Comprobar si la consulta devolvió resultados
        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val nombre = resultadoConsultaLectura.getString(1)
                val pais = resultadoConsultaLectura.getString(2)
                val temporadaEnCurso = resultadoConsultaLectura.getInt(3) == 1
                val premioAlCampeon = resultadoConsultaLectura.getDouble(4)
                val fechaDeInicioTexto = resultadoConsultaLectura.getString(5)

                val liga = LigaDeFutbol(id, nombre, pais, temporadaEnCurso, premioAlCampeon, fechaDeInicioTexto)
                arregloLigas.add(liga)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return arregloLigas
    }


    fun crearEquipo(
        ligaID: Int,
        nombre: String,
        campeonatos: Int,
        poseeEstadio: Boolean,
        precioDelEquipo: Double,
        fechaDeCreacionTexto: String,

    ): Boolean{

        val estadio = if(poseeEstadio) 1 else 0

        val basedatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("ligaID", ligaID)
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("campeonatos", campeonatos)
        valoresAGuardar.put("poseeEstadio", estadio)
        valoresAGuardar.put("precioDelEquipo", precioDelEquipo)
        valoresAGuardar.put("fechaCreacion", fechaDeCreacionTexto)
        val resultadoGuardar = basedatosEscritura
            .insert(
                "EQUIPOFUTBOL",
                null,
                valoresAGuardar
            )
        basedatosEscritura.close()
        return if (resultadoGuardar.toInt() == -1) false else true

    }

    fun eliminarEquipoFormulario(id: Int): Boolean {
        val conexionEscritura = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura
            .delete(
                "EQUIPOFUTBOL",
                "id=?",
                parametrosConsultaDelete
            )
        conexionEscritura.close()
        return if (resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarEquipoFormulario(

        ligaID: Int,
        nombre: String,
        campeonatos: Int,
        poseeEstadio: Boolean,
        precioDelEquipo: Double,
        fechaDeCreacionTexto: String,

    ): Boolean{

        val estadio = if(poseeEstadio) 1 else 0

        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("campeonatos", campeonatos)
        valoresAActualizar.put("poseeEstadio", estadio)
        valoresAActualizar.put("precioDelEquipo", precioDelEquipo)
        valoresAActualizar.put("fechaCreacion", fechaDeCreacionTexto)
        val parametrosConsultaActualizar = arrayOf(ligaID.toString())
        val resultadosActualizacion = conexionEscritura
            .update(
                "EQUIPOFUTBOL",
                valoresAActualizar,
                "id=?",
                parametrosConsultaActualizar
            )
        conexionEscritura.close()
        return if (resultadosActualizacion.toInt() == -1) false else true
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun obtenerEquipoDeLiga(ligaId: Int): ArrayList<EquipoFutbol> {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura =
            """
        SELECT * FROM EQUIPOFUTBOL WHERE ligaID = ?
    """.trimIndent()
        val parametrosConsultaLectura = arrayOf(ligaId.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(scriptConsultaLectura, parametrosConsultaLectura)

        val arregloEquipos = ArrayList<EquipoFutbol>()

        if (resultadoConsultaLectura.moveToFirst()) {
            do {
                val id = resultadoConsultaLectura.getInt(0)
                val idLiga = resultadoConsultaLectura.getInt(1)
                val nombre = resultadoConsultaLectura.getString(2)
                val campeonatos = resultadoConsultaLectura.getInt(3)
                val poseeEstadio = resultadoConsultaLectura.getInt(4) == 1
                val precioDelEquipo = resultadoConsultaLectura.getDouble(5)
                val fechaDeInicioTexto = resultadoConsultaLectura.getString(6)

                val equipo = EquipoFutbol(id, idLiga, nombre, campeonatos, poseeEstadio, precioDelEquipo, fechaDeInicioTexto)
                arregloEquipos.add(equipo)
            } while (resultadoConsultaLectura.moveToNext())
        }

        resultadoConsultaLectura.close()
        baseDatosLectura.close()

        return arregloEquipos
    }




}
