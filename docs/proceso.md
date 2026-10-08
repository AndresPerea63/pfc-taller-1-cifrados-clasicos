# Algoritmo Factorial con Recursión de Cola

## Definición del Algoritmo

```Scala
def factorial(n: Int): BigInt = {
  @annotation.tailrec
  def loop(x: Int, acumulador: BigInt): BigInt = {
    if (x <= 1) acumulador
    else loop(x - 1, acumulador * x)
  }
  loop(n, 1)
}
```

* La función `factorial` calcula el factorial de un número `n` utilizando **recursión de cola**.
* La función interna `loop` es la que hace la recursión:

  * Recibe dos parámetros:

    * `x`: el valor actual decreciente hasta llegar a 1.
    * `acumulador`: donde se guarda el resultado parcial en cada paso.
* El decorador `@annotation.tailrec` obliga a que la función sea optimizada como recursión de cola, es decir, **no se acumulan llamados en la pila**.

## Explicación paso a paso

### Caso base

```Scala
if (x <= 1) acumulador
```

Cuando `x` llega a `1`, la función retorna directamente el valor acumulado, evitando más llamadas.

### Caso recursivo

```Scala
loop(x - 1, acumulador * x)
```

En cada llamada:

* Se reduce el valor de `x` en 1.
* Se multiplica el acumulador por `x` y se pasa a la siguiente iteración.
* Como es recursión de cola, la llamada recursiva es la **última instrucción** en ejecutarse, lo que permite a Scala optimizar la pila.

---

## Llamados de pila en recursión de cola

Ejemplo:

```Scala
factorial(5)
```

### Paso 1: Llamada inicial

```Scala
loop(5, 1)
```

### Paso 2: Primera iteración

```Scala
loop(4, 5)   // acumulador = 1 * 5
```

### Paso 3: Segunda iteración

```Scala
loop(3, 20)  // acumulador = 5 * 4
```

### Paso 4: Tercera iteración

```Scala
loop(2, 60)  // acumulador = 20 * 3
```

### Paso 5: Cuarta iteración

```Scala
loop(1, 120) // acumulador = 60 * 2
```

### Paso 6: Caso base

```Scala
return 120
```

---

## Diferencia con recursión normal

* En **recursión normal** cada llamada queda en la pila esperando a que termine la siguiente, lo que puede causar desbordamiento si `n` es muy grande.
* En **recursión de cola**, el compilador transforma el proceso en un **bucle optimizado**, por lo que no se guarda cada llamada en la pila y el algoritmo puede ejecutarse para valores muy grandes sin problema.

---

## Ejemplo de uso

```Scala
val resultado = factorial(5)
println(resultado)  // 120
```

El resultado de `factorial(5)` es `120`.


## Diagrama de llamados de pila con recursión de cola

```mermaid
sequenceDiagram
    participant Main as factorial(5)
    participant L1 as loop(5, 1)
    participant L2 as loop(4, 5)
    participant L3 as loop(3, 20)
    participant L4 as loop(2, 60)
    participant L5 as loop(1, 120)

    Main->>L1: llamada inicial
    L1->>L2: tail call con (4, 5)
    L2->>L3: tail call con (3, 20)
    L3->>L4: tail call con (2, 60)
    L4->>L5: tail call con (1, 120)
    L5-->>Main: return 120
```




---

## Punto 1: `cesar` con recursión lineal

```scala
def cesar(m: Mensaje, k: Int): Mensaje =
  if (m.isEmpty) "" else desplazar(m.head) + cesar(m.tail, k)
```

### Traza de `cesar("casa", 3)`

```
cesar("casa", 3)
= 'f' + cesar("asa", 3)
= 'f' + ('d' + cesar("sa", 3))
= 'f' + ('d' + ('v' + cesar("a", 3)))
= 'f' + ('d' + ('v' + ('d' + cesar("", 3))))
= 'f' + ('d' + ('v' + ('d' + "")))
= 'f' + ('d' + ('v' + "d"))
= 'f' + ('d' + "vd")
= 'f' + "dvd"
= "fdvd"
```

### Pila de llamados

```mermaid
graph TD
  A["cesar('casa', 3): espera 'f' + ..."] --> B["cesar('asa', 3): espera 'd' + ..."]
  B --> C["cesar('sa', 3): espera 'v' + ..."]
  C --> D["cesar('a', 3): espera 'd' + ..."]
  D --> E["cesar('', 3): devuelve ''"]
```

### ¿Por qué es recursión lineal?

En cada invocación hay un único llamado recursivo, sobre `m.tail`. Además, ese
llamado no es lo último que se hace: al volver queda pendiente la
concatenación con `desplazar(m.head)`. Por eso cada llamado debe esperar en la
pila a que termine el siguiente, y la pila crece una posición por cada letra
del mensaje: para $n$ letras hay $n+1$ llamados activos a la vez, es decir,
espacio $O(n)$.
