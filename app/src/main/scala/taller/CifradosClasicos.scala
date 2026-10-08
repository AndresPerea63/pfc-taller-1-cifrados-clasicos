package taller

import scala.annotation.tailrec

/**
 * Taller 1 — cifrados clásicos con recursión.
 *
 * Solo se cifran las 26 letras minúsculas del alfabeto inglés; cualquier otro
 * carácter se copia sin cambio.
 */
class CifradosClasicos {

  type Mensaje = String
  type Clave = String

  // Una frecuencia asocia cada letra con las veces que aparece.
  type Frecuencias = List[(Char, Int)]

  val letras = 26
  val primera = 'a'.toInt

  def esMinuscula(c: Char): Boolean = c >= 'a' && c <= 'z'

  // Punto 1 -------------------------------------------------------------------

  /** César con recursión lineal: una operación pendiente por letra. */
  def cesar(m: Mensaje, k: Int): Mensaje = ???

  // Punto 2 -------------------------------------------------------------------

  /**
   * El mismo César como proceso iterativo: espacio constante.
   * Cuando la función esté escrita, anótela con @tailrec: el compilador
   * comprueba que la llamada recursiva sea lo último que hace.
   */
  final def cesarCola(m: Mensaje, k: Int, acc: Mensaje = ""): Mensaje = ???

  // Punto 3 -------------------------------------------------------------------

  /**
   * Cuenta las letras minúsculas del mensaje, de mayor a menor frecuencia y,
   * en empate, en orden alfabético. El recorrido es recursivo de cola.
   */
  def frecuencias(m: Mensaje): Frecuencias = ???

  // Punto 4 -------------------------------------------------------------------

  /**
   * Supone que la letra más frecuente del mensaje cifrado es la 'e' del
   * original y devuelve la distancia entre las dos. Sin letras, cero.
   */
  def desplazamientoProbable(m: Mensaje): Int = {
    val fs = frecuencias(m) // la primera de la lista es la letra más frecuente
    if (fs.isEmpty) 0 // sin letras, el desplazamiento es 0
    else {
      val (letraMasFrecuente, _) = fs.head // se queda solo con la letra
      (letraMasFrecuente - 'e' + letras) % letras // el + letras evita resultados negativos
    }
  }

  def romperCesar(m: Mensaje): Mensaje =
    cesar(m, -desplazamientoProbable(m)) // descifrar es cifrar con el desplazamiento negativo

  // Punto 5 -------------------------------------------------------------------

  /**
   * Cuántos mensajes de longitud n se forman con a letras sin dos iguales
   * seguidas.
   */
  def combinaciones(n: Int, a: Int): BigInt =
    if (n == 0) BigInt(1) // un solo mensaje: el vacío
    else if (n == 1) BigInt(a) // hay a mensajes de una letra
    else BigInt(a - 1) * combinaciones(n - 1, a) // C(n, a) = (a - 1) * C(n - 1, a)

  /**
   * Vigenère: cada letra se corre según la letra de la clave que le toca. Lo
   * que no es letra minúscula se copia y no consume clave.
   */
  def vigenere(m: Mensaje, clave: Clave): Mensaje = {
    // pos cuenta cuántas letras del mensaje llevamos; sirve para escoger la letra de la clave
    def cifrar(resto: Mensaje, pos: Int): Mensaje =
      if (resto.isEmpty) ""
      else if (esMinuscula(resto.head)) {
        val desplazamiento = clave(pos % clave.length) - primera // a = 0, b = 1, ..., z = 25
        cesar(resto.head.toString, desplazamiento) + cifrar(resto.tail, pos + 1)
      } else
        resto.head.toString + cifrar(resto.tail, pos) // no es letra: se copia y pos no cambia

    if (clave.isEmpty) m else cifrar(m, 0)
  }
}
