import java.util.Date

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    //Inmutable (No se reasignan)
    val inmutable: String ="Rommel";

    //Mutable (re asignar)
    var mutable: String = "Paul";
    mutable = "Rommel"

    //val > var

    //Duck typing
    val ejemploVariable = "Rommel Masabanda"
    val edadEjemplo: Int = 23
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo;

    //Variable primitiva
    val nombreProfesor: String = "Rommel Masabanda"
    val sueldo: Double = 1.2
    var estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    //Clases Java
    val fechaNacimiento: Date = Date()

    //No existe el switch, pero si el when
    val estadoCivilWhen = "C"
    when(estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        ("S") -> {
            println("Soltero")
        }
        else ->{
            println("no sabemos")
        }
    }

    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    calcularSueldo(10.0)
    calcularSueldo(10.0, 20.0)
    calcularSueldo(10.0, 20.0, 30.0)
    calcularSueldo(10.0, bonoEspecial = 30.0)
    calcularSueldo(sueldo=10.0, tasa = 45.0, bonoEspecial = 18.0 )

    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null, 1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    //ARREGLOS
    //Tipo de arreglos
    //Arreglo estatico



}

fun imprimirNombre(nombre:String): Unit{
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, //requerido
    tasa: Double = 12.0, //opcional(por defecto)
    bonoEspecial: Double? = null, //opcional (nullable)
): Double{
    if (bonoEspecial == null){
        return sueldo*(100/tasa)
    }else{
        return sueldo*(100/tasa)+bonoEspecial
    }
}

abstract class NumerosJava {
    protected val numeroUno: Int
    private val numeroDos: Int
    constructor(
        uno: Int,
        dos: Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class numeros(//constructor primario
    //ejemplo:
    //uno: Int (Parametro (sin modificar acceso))
    //private var uno : Int (Propiedad publica Clase numeros )
    //var uno:Int Propiedad de la clase por defecto publica
    protected val numeroUno:Int, //propiedad de la clase protected numeros.numeroUno
    protected val numeroDos:Int,
){
    init {
        this.numeroUno; this.numeroDos;//No es necesario el uso de this
        numeroUno; numeroDos;
        println("Inicializando")

    }
}

class Suma(//Constructor Primario Suma
    uno: Int, //Parametro
    dos: Int //Parametro
): numeros(uno, dos) { // <- Cosntructor padre
    init { //Bloque constructor primario
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }
    constructor( // Segundo constructor
        uno: Int?, //parametros
        dos: Int //parametros
    ): this(//llamada constructor primario
        if(uno==null) 0 else uno,
        dos
    ){//si necesitamos bloque de codigo lo usamos
        numeroUno
    }
    constructor(//tercer constructor
        uno:Int,
        dos: Int?
    ): this(
        uno,
        if (dos==null) 0 else dos
    )// si no necesitamos el bloque de codigo "{}" lo omitios

    constructor( //cuarto constructor
        uno: Int?,
        dos: Int?
     ): this(
        if ( uno == null ) 0 else uno,
        if ( dos == null ) 0 else dos
     )
    public fun sumar(): Int{
        val total = numeroUno+numeroDos
        agregarHistorial(total)
        return total
    }

    companion object{//atributos y metodos compartidos
        //entre las instancias
        val pi = 3.14
        fun elevarAlCuadrado(num:Int):Int{
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma: Int){
            historialSumas.add(valorNuevaSuma)
        }
    }
}