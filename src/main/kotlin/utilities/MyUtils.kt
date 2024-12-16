package utilities
import java.util.*

fun abrirScanner () : Scanner {
    var scan : Scanner = Scanner(System.`in`)
    //devolvemos el scan
    return scan
}

//Hacemos una funcion para cerrar el scanner
fun cerrarScanner (scan: Scanner){
    scan.close()

}
