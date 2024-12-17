package controllers.Main

import utilities.abrirScanner
import utilities.cerrarScanner
import java.util.*


/**
 *
 * @since 15/12/2024 v1.0.0
 * @author Oriol Callao, Yujiang Xia
 * ## Main
 * @param scan Abrir Scanner
 * @param menu Llamar la funcion del menu
 *
 */

// Data class guadar las opciones

data class billete(

    var tipo : Int,
    var zona : Int
)

fun main() {

    val scan: Scanner = abrirScanner()

    menu(scan)
}


fun menu(scan: Scanner) {

    // Creación de cesta para guardar la compra
    val cesta = mutableListOf<billete>()

    var opcion: Int
    do {
        opcion = pedirNumero(
            """
            Escoge alguna opción:
             1.- Bitllet senzill
             2.- TCasual
             3.- TUsual 
             4.- TFamiliar 
             5.- TJove
            """.trimIndent(),
            scan
        )

        when (opcion) {

            in 1..5 -> zona("Escoge una zona, para ir atras 0",scan, opcion, cesta, scan)
            else -> println("Opción no válida. Intenta nuevamente.")
        }
    } while (opcion != 4321)

    cerrarScanner(scan)
}

/**
 *
 * ## PedirNumero
 * @param msg mostrara un mensaje
 * @param scan Leer valor del usuario
 * @return Devolvera el valor escaneado
 */

fun pedirNumero(msg: String, scan: Scanner): Int {
    print(msg)
    return scan.nextInt()
}

fun pedirdinero(msg: String, scan: Scanner): Double {
    print(msg)
    return scan.nextDouble()
}



fun zona(msg: String, scan: Scanner, tipo: Int, cesta: MutableList<billete>, scanner: Scanner) {

    println(msg)
    var opcion = scan.nextInt()
    scan.nextLine()

    if (opcion == 0) {
        println("Volviendo Menu principal ...")
        return
    }else if (opcion in 1..3) {
        cesta.add(billete(tipo = tipo, zona = opcion))
        if(cesta.size<3){
            println("Quieres anyadir otro Billete ? (n/s)")
            var sino = scanner.nextLine()
            if (sino=="s"){
                return
            }else{
                println("Haciendo Calculos")
                calculo(cesta)
            }

        }
    }

}



// Calculo de billetes con las zonas
fun calculo(cesta: MutableList<billete>){

    var total = 0.00f

    for (i in 0 until cesta.size){
        // Determinamos tipo de zona
        var extra = if(cesta[i].zona == 1){1f} else if (cesta[i].zona==2){1.3125f}else{1.8443f}

        total += extra * cesta[i].tipo.toFloat()

    }
    println(total)


}



/*

fun zona(msg: String, scan: Scanner, cesta: MutableList<billete>) {

    var tipo = 2.40

    if (cesta.size < 3){
        print(msg)
        val num = scan.nextInt()

        cesta.add(billete(tipo = tipo, zona = num))
        println("Billete anyadido")
        if(cesta.size <3){
            scan.nextLine()
            println("Quieres anyadir otro billete? [s/n]")

            var sn = scan.nextLine()
            if(sn == "n"){
                calculo(cesta)

            }else{
                menu(scan)
            }
        }
    }else{
        println("Maximo 3 billetes")
    }

}
 */
