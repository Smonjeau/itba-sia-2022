
# TP2
Algoritmo genético que resuelve el problema de la mochila utilizando distintos tipos de métodos de cruza y selección.

## Ejecución
```bash
java -jar tp2-g18.jar <path_archivo_con_items> <path_archivo_.json>
```
Se debe poner como parámetro la dirección al archivo que contiene los items.
El resultado óptimo se imprime a pantalla.

## Archivo de configuración
Los parámetros de ejecución se leeran de un archivo .json que debe ser pasado como parámetro.

Los posibles parámetros son:

| Parameter     | Type     | Values                                        | Description                                                                       |
| :-------------| :------- |:----------------------------------------------|:----------------------------------------------------------------------------------|
| `max_iterations`         | `int` |  | Cantidad de iteraciones                                          |
| `P`       | `int`    |                                               | Tamaño de la población de individuos             |
| `pairing`   | `String` | `simple`, `multiple`, `uniformed`    | Método de cruza ha utilizar |
| `mutation_probability`   | `double` |     | Probabilidad con la que se muta un gen |
| `selection`   | `String` |  `boltzmann`, `tournament`, `rank`, `roulette`, `elite`, `truncated`   | Método de selección ha utilizar |
| `k`   | `double` |     | `boltzamnn`: es la velocidad con la que decrece la temperatura. `tournament`: es la probabilidad utilizada en la decisión del ganador |
| `T0`   | `int` |     | Temperatura inicial utilizada para el método de selección de `boltzmann` |


## Ejemplos de uso
#### Elite
```json
// config.json
{
  "max_iterations": 1000,
  "P": 100,
  "pairing": "simple",
  "mutation_probability": 0.01,
  "selection": "elite",
}
```

#### boltzmann
```json
// config.json
{
  "max_iterations": 1000,
  "P": 100,
  "pairing": "simple",
  "mutation_probability": 0.01,
  "selection": "boltzmann",
  "k": 0.75,
  "T0": 100
}
```




