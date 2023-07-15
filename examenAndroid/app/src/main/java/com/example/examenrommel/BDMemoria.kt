package com.example.examenrommel

import android.os.Build
import androidx.annotation.RequiresApi

class BDMemoria {

    @RequiresApi(Build.VERSION_CODES.O)
    companion object {
        val listaLigas: MutableList<LigaDeFutbol> = mutableListOf()
        val listaEquipos: MutableList<EquipoFutbol> = mutableListOf()


        init {

            agregarLiga(LigaDeFutbol(
                1,
                "Liga Pro",
                "Ecuador",
                true,
                200000.14,
                "10/01/2023")
            )

            agregarEquipo(
                EquipoFutbol(
                    1,
                    "Liga de Quito",
                    11,
                    true,
                    17000000.27,
                    "11/01/1930")
            )

            agregarEquipo(
                EquipoFutbol(
                    1,
                    "Independiente del valle",
                    6,
                    true,
                    9000000.504,
                    "16/03/1958")
            )

            agregarEquipo(
                EquipoFutbol(
                    1,
                    "Barcelona",
                    16,
                    true,
                    25885404.27,
                    "01/08/1925")
            )

            //asociarEquiposConLiga(1)

        }

        fun agregarLiga(liga: LigaDeFutbol) {
            listaLigas.add(liga)
        }


        fun agregarEquipo(equipo: EquipoFutbol) {
            listaEquipos.add(equipo)
        }

        /*
        fun asociarEquiposConLiga(idLiga: Int){
            val equiposConMismoId = listaEquipos.filter { it.fkLiga == idLiga }
            val liga = listaLigas.find { it.id == idLiga }

            if (liga != null) {
                liga.listaDeEquipos?.addAll(equiposConMismoId)
            }
        }

         */

    }
}