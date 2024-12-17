package controllers

import java.util.*
/**
 * Data class que representa un billete de metro, con su tipo y zona.
 *
 * @param tipo El precio del billete.
 * @param zona La zona del billete (1, 2 o 3).
 */
data class billete(
    var tipo: Double,  // Cambiado de Int a Double
    var zona: Int
)

/**
 * Función principal que inicia la máquina expendedora y gestiona la compra de billetes.
 *
 * Inicia el proceso, mostrando el menú y permitiendo seleccionar el tipo de billete,
 * la zona y realizar el pago.
 */
fun main() {
    val scan = abrirscanner()
    val cesta = mutableListOf<billete>()

    var continuar = true
    while (continuar) {
        val opcion = menu(scan)
        if (opcion == -1) {
            continuar = false
        } else {
            when (opcion) {
                1 -> pedirzona("Elige una zona para el Billet Senzill (1-3). Para volver al menu principal, pulsa 0.", scan, cesta)
                2 -> pedirzona("Elige una zona para el TCasual (1-3). Para volver al menu principal, pulsa 0.", scan, cesta)
                3 -> pedirzona("Elige una zona para el TUsual (1-3). Para volver al menu principal, pulsa 0.", scan, cesta)
                4 -> pedirzona("Elige una zona para el TFamiliar (1-3). Para volver al menu principal, pulsa 0.", scan, cesta)
                5 -> pedirzona("Elige una zona para el TJove (1-3). Para volver al menu principal, pulsa 0.", scan, cesta)
                else -> {
                    println("Opcion no valida. Intenta de nuevo.")
                    continuar = false
                }
            }
        }
    }

    cerrarscanner(scan)
}

/**
 * Función que abre un objeto `Scanner` configurado para leer entradas del usuario.
 *
 * @return Un objeto `Scanner` para leer entradas desde la consola.
 */
fun abrirscanner(): Scanner {
    return Scanner(System.`in`).useLocale(Locale.UK)
}

/**
 * Función que cierra el objeto `Scanner`.
 *
 * @param scanner El objeto `Scanner` que se va a cerrar.
 */
fun cerrarscanner(scanner: Scanner) {
    scanner.close()
}

/**
 * Función que cierra el programa con un código secreto.
 *
 * @param num El código que, si es igual a 4321, apaga la máquina.
 */
fun cerrarprogr(num: Int) {
    println("Codigo secreto correcto, programa apagado.")
}

/**
 * Muestra el menú principal para elegir el tipo de billete y devuelve la opción seleccionada.
 *
 * @param scan El objeto `Scanner` para leer la opción seleccionada.
 * @return Un entero que representa la opción elegida por el usuario.
 */
fun menu(scan: Scanner): Int {
    var opcion = pedirnumero(
        """
        Escoge alguna opcion:
         1.- Bitllet senzill
         2.- TCasual
         3.- TUsual
         4.- TFamiliar
         5.- TJove
        """.trimIndent(),
        scan
    )
    if (opcion == 4321) {
        cerrarprogr(opcion)
        return -1
    }
    while (opcion !in 1..5) {
        println("Opcion no valida. Escoge una opcion de las anteriores (1-5).")
        opcion = scan.nextInt()
    }
    return opcion
}

/**
 * Función que pide un número al usuario.
 *
 * @param msg El mensaje que se muestra al usuario para ingresar un número.
 * @param scan El objeto `Scanner` utilizado para leer la entrada del usuario.
 * @return Un número entero proporcionado por el usuario.
 */
fun pedirnumero(msg: String, scan: Scanner): Int {
    println(msg)
    return scan.nextInt()
}

/**
 * Solicita al usuario que seleccione el tipo de billete que desea comprar.
 *
 * @param scan El objeto `Scanner` utilizado para leer la opción seleccionada.
 * @return El precio del billete seleccionado como un valor de tipo `Double`.
 */
fun pedirtipo(scan: Scanner): Double {
    println("Selecciona el tipo de billete:")
    println("1.- Bitllet senzill (2.40€)")
    println("2.- TCasual (11.35€)")
    println("3.- TUsual (40.00€)")
    println("4.- TFamiliar (10.00€)")
    println("5.- TJove (80.00€)")

    var tipo: Double
    var opcion = scan.nextInt()

    while (opcion !in 1..5) {
        println("Opcion no valida. Escoge una opcion de las anteriores (1-5).")
        opcion = scan.nextInt()
    }

    tipo = when (opcion) {
        1 -> 2.40
        2 -> 11.35
        3 -> 40.00
        4 -> 10.00
        5 -> 80.00
        else -> 0.0
    }
    return tipo
}

/**
 * Pide al usuario la zona del billete que desea comprar.
 *
 * @param msg El mensaje que se le muestra al usuario para que ingrese la zona del billete.
 * @param scan El objeto `Scanner` utilizado para leer la zona seleccionada.
 * @param cesta La lista donde se almacenarán los billetes comprados.
 */
fun pedirzona(msg: String, scan: Scanner, cesta: MutableList<billete>) {
    var seguiranyadiendo = true

    while (seguiranyadiendo) {
        val tipo = pedirtipo(scan) // Aquí pedimos el tipo del billete

        println(msg)
        var opcion = scan.nextInt()

        // Consumir la línea residual después de leer nextInt
        scan.nextLine()

        if (opcion == 0) {
            println("Volviendo al menu principal...")
            return
        } else if (opcion in 1..3) {
            cesta.add(billete(tipo = tipo, zona = opcion))
            println("Billete añadido: Tipo $tipo, Zona $opcion")

            if (cesta.size < 3) {
                println("¿Quieres añadir otro billete? (s/n)")
                val respuesta = scan.nextLine().trim()
                if (respuesta == "n") {
                    println("Calculando total...")
                    calculo(cesta, scan)
                    seguiranyadiendo = false // Finaliza el bucle si no quiere añadir más billetes
                }
            } else {
                println("Has alcanzado el limite de 3 billetes. Finalizando compra...")
                calculo(cesta, scan)
                seguiranyadiendo = false // Finaliza el bucle si ya hay 3 billetes
            }
        } else {
            println("Zona no valida. Intenta de nuevo.")
        }
    }
}

/**
 * Calcula el total de la compra sumando el precio de los billetes según las zonas seleccionadas.
 *
 * @param cesta La lista de billetes que el usuario ha añadido a su compra.
 * @param scan El objeto `Scanner` utilizado para leer cualquier dato adicional durante el cálculo.
 */
fun calculo(cesta: MutableList<billete>, scan: Scanner) {
    var total = 0.00
    for (billete in cesta) {
        val extra = when (billete.zona) {
            1 -> 1.0
            2 -> 1.3125
            3 -> 1.8443
            else -> 0.0
        }
        total += extra * billete.tipo
    }
    println("El total de la compra es: $total €")
    // No imprimimos el tiquet aquí, solo calculamos el total
    pagar(cesta, total, scan)
}

/**
 * Imprime el tiquet final de la compra, mostrando el tipo de cada billete y su zona.
 *
 * @param cesta La lista de billetes comprados.
 * @param total El total a pagar por los billetes.
 */
fun imprimirtiquet(cesta: MutableList<billete>, total: Double) {
    println("\n_____TIQUET_____")
    cesta.forEach { billete ->
        println("Billete Tipo ${billete.tipo}, Zona ${billete.zona}")
    }
    println("Total: $total €")
    println("________________")
    println("Recoge tu tiquet y pago.")
}

/**
 * Calcula el cambio a devolver al usuario.
 *
 * @param total El total de la compra.
 * @param dinerointroducido El dinero que el usuario ha introducido.
 * @return El cambio a devolver al usuario.
 */
fun calcularcambio(total: Double, dinerointroducido: Double): Double {
    return dinerointroducido - total
}

/**
 * Función que gestiona el pago, validando que el dinero introducido sea válido y suficiente.
 *
 * @param cesta La lista de billetes comprados.
 * @param total El total de la compra que debe ser pagado.
 * @param scan El objeto `Scanner` utilizado para leer las cantidades de dinero introducidas por el usuario.
 */
fun pagar(cesta: MutableList<billete>, total: Double, scan: Scanner) {
    val dineroValido = arrayOf(0.05, 0.10, 0.20, 0.20, 0.5, 1.0, 5.0, 10.0, 20.0, 50.0) // Monedas y billetes válidos
    var dinerointroducido = 0.0
    while (dinerointroducido < total) {
        println("Introduce el dinero para pagar el total ($total €), Introduce de uno en uno.")
        val dinero = scan.nextDouble()

        // Consumir la línea residual después de leer nextDouble
        scan.nextLine()

        // Validación para asegurarse de que el dinero introducido es válido
        if (dinero in dineroValido) {
            dinerointroducido += dinero
            if (dinerointroducido < total) {
                println("Te falta ${total - dinerointroducido} € para completar el pago.")
            }
        } else {
            println("Por favor, introduce una cantidad válida (monedas o billetes permitidos).")
        }
    }
    val cambio = calcularcambio(total, dinerointroducido) // Calcular el cambio
    println("Tu cambio es: $cambio €")

    // Preguntar si quiere imprimir el tiquet
    println("¿Quieres imprimir el tiquet? (s/n)")
    val respuesta = scan.nextLine().trim()

    if (respuesta == "s") {
        imprimirtiquet(cesta, total)
    } else {
        println("Gracias por utilizar nuestro servicio. ¡Hasta luego!")
    }
}


