package com.example.exameniib_masabanda

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    var idItemSeleccionado = 0
    var listaLigaFutbol: ArrayList<LigaDeFutbol> = arrayListOf<LigaDeFutbol>()

    @RequiresApi(Build.VERSION_CODES.O)
    val crearLigaFutbolActivityResult =
        registerForActivityResult(//registrar un callback pendiente del resultado de la actividad
            ActivityResultContracts.StartActivityForResult() //contrato se activa cuando una actividad se inicia con startActivityResult
        ){
            //Cuando se reciba el resultado se ejecuta
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data //datos de la actividad crear liga futbol
                    val db = Firebase.firestore
                    val ligaFutbol = db.collection("liga_futbol")
                    val ligaFutbolInsertar = hashMapOf(
                        "nombre" to data?.getStringExtra("nombreModificado").toString(),
                        "pais" to data?.getStringExtra("paisModificado").toString(),
                        "temporadaEnCurso" to data?.getBooleanExtra("temporadaEnCursoModificada",false),
                        "premio" to data?.getDoubleExtra("premioModificado",102.3),
                        "fechaInicio" to data?.getStringExtra("fechaInicioModificada").toString()
                    )

                    // Insertar la nueva liga de fútbol en Firestore
                    ligaFutbol
                        .add(ligaFutbolInsertar)
                        .addOnSuccessListener {  }
                        .addOnFailureListener(){  }

                    actualizarListView()
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    val editarLigaFutbolActivityResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){

                    val data = result.data
                    val db = Firebase.firestore

                    val ligaFutbolRef = db.collection("liga_futbol").document(data?.getStringExtra("idDevuelto")?.toString()!!)
                    val ligaFutbolActualizada = hashMapOf(
                        "nombre" to data?.getStringExtra("nombreModificado").toString(),
                        "pais" to data?.getStringExtra("paisModificado").toString(),
                        "temporadaEnCurso" to data?.getBooleanExtra("temporadaEnCursoModificada",false),
                        "premio" to data?.getDoubleExtra("premioModificado", 0.0),
                        "fechaInicio" to data?.getStringExtra("fechaInicioModificada").toString()
                    )

                    // Actualizar los datos en Firestore
                    ligaFutbolRef.set(
                        ligaFutbolActualizada
                    )
                        .addOnSuccessListener {  }
                        .addOnFailureListener(){  }

                    actualizarListView()
                }
            }
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        actualizarListView()

        val crearLigaFutbolButton = findViewById<Button>(R.id.btn_crear_liga)
        crearLigaFutbolButton.setOnClickListener(){
            irActividad(FormularioLiga::class.java)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun actualizarListView(){

        val listViewLidaFutbol = findViewById<ListView>(R.id.list_view_ligas)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            this.listaLigaFutbol
        )
        // Configurar el adaptador y notificar cambios en la vista
        listViewLidaFutbol.adapter = adaptador
        adaptador.notifyDataSetChanged()

        registerForContextMenu(listViewLidaFutbol)

        consultarLigaFutbol(adaptador)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun consultarLigaFutbol(adaptador: ArrayAdapter<LigaDeFutbol>){
        val db = Firebase.firestore
        val ligaDeFutbolRefUnico = db.collection("liga_futbol")

        limpiarArreglo()
        adaptador.notifyDataSetChanged()

        ligaDeFutbolRefUnico
            .get()
            .addOnSuccessListener {
                for (ligaFutbol in it){
                    anadirAListaLigaFutbol(ligaFutbol, ligaFutbol.id)
                }
                adaptador.notifyDataSetChanged()
            }
            .addOnFailureListener(){
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun anadirAListaLigaFutbol(
        ligaFutbol: QueryDocumentSnapshot,
        idLigaFutbol: String
    ){
        val nuevaLiga = LigaDeFutbol(
            idLigaFutbol.toString(),
            ligaFutbol.data.get("nombre").toString() as String,
            ligaFutbol.data.get("pais").toString() as String,
            ligaFutbol.data.get("temporadaEnCurso").toString().toBoolean() as Boolean,
            ligaFutbol.data.get("premio").toString().toDouble() as Double,
            ligaFutbol.data.get("fechaInicio").toString() as String
        )

        this.listaLigaFutbol.add(nuevaLiga)
    }

    private fun limpiarArreglo() {
        this.listaLigaFutbol.clear()
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar ->{
                abrirConParametrosEditarLF(FormularioLigaEditar::class.java)
                return true
            }
            R.id.mi_eliminar ->{
                abrirDialogoEliminar()
                actualizarListView()
                return true
            }
            R.id.mi_ver_liga ->{

                try {
                    abrirActividadListarLF(ListViewEquipos::class.java)
                } catch (e: Exception) {
                    val errorMessage = "Ocurrió un error: ${e.message}"
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }

                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun abrirConParametrosEditarLF(clase: Class<*>){
        val intentExplicito = Intent(this, clase)
        val ligaFutbol = listaLigaFutbol.get(idItemSeleccionado)

        intentExplicito.putExtra("id", ligaFutbol.id)
        intentExplicito.putExtra("nombre", ligaFutbol.nombre)
        intentExplicito.putExtra("pais", ligaFutbol.pais)
        intentExplicito.putExtra("temporadaEnCurso", ligaFutbol.temporadaEnCurso)
        intentExplicito.putExtra("premio", ligaFutbol.premioAlCampeon)
        intentExplicito.putExtra("fechaInicio", ligaFutbol.fechaDeInicioTexto)

        editarLigaFutbolActivityResult.launch(intentExplicito)
    }

    fun abrirActividadListarLF(clase: Class<*>){
        val intentExplicito = Intent(this, clase)

        intentExplicito.putExtra("idLiga", listaLigaFutbol.get(idItemSeleccionado).id)
        intentExplicito.putExtra("nombreLiga", listaLigaFutbol.get(idItemSeleccionado).nombre)

        startActivity(intentExplicito)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun irActividad(clase: Class<*>){
        val intent = Intent(this, clase)
        crearLigaFutbolActivityResult.launch(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun abrirDialogoEliminar(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea Eliminar?")
        builder.setPositiveButton("Aceptar") {
                dialog, which ->
            val ligaFutbol = listaLigaFutbol.get(idItemSeleccionado)
            val db = Firebase.firestore
            val ligaFutbolRef = db.collection("liga_futbol").document(ligaFutbol.id!!.toString())

            ligaFutbolRef.delete()
                .addOnSuccessListener {  }
                .addOnFailureListener {  }
            actualizarListView()

        }
        builder.setNegativeButton("Cancelar", null)
        val dialogo = builder.create()
        dialogo.show()
    }


}