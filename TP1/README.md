
# TP1
Generador de soluciones para el problema de rompecabezas de 8 números.
Se utilizan las estrategias de busqueda: BPA, BPP, BPPV, Heurística Local, Heurística Global y A*.

## Ejecución
```bash
java -jar tp1-g18.jar <path_archivo_json_config>
```
Los resultados de la ejecución se generarán en el archivo `logs.txt`

## Archivo de configuración
Los parámetros de ejecución se leeran del archivo .json pasado por argumento al correr el programa.

Los posibles parámetros son:

| Parameter     | Type     | Values                                        | Description                                                                       |
| :-------------| :------- |:----------------------------------------------|:----------------------------------------------------------------------------------|
| `alg`         | `String` | `BPA`, `BPP`, `BPPV`, `local`,`local-bt`, `global`, `A*` | Estrategía de busqueda que se utilizará                                           |
| `guess`       | `int`    |                                               | Limite inicial utilizado en la estrategía BPPV. El valor máximo es 31             |
| `heuristic`   | `String` | `manhattan`, `misplaced`, `enforced order`    | Heuristica ha utilizar. Es necesario para las estrategías `local`, `global`, `A*` |



## Ejemplos de uso

#### BPA
```json
// parameters.json
{
    "algorithm": "BPA",
    "board": [
        [1, 8, 2],
        [0, 4, 3],
        [7, 6, 5]
    ]
}
```

#### BPPV con limite inicial 20
```json
// parameters.json
{
    "algorithm": "BPPV",
    "guess": 20,
    "board": [
        [1, 8, 2],
        [0, 4, 3],
        [7, 6, 5]
    ]
}
```

#### A* con heurística Manhattan
```json
// parameters.json
{
    "algorithm": "BPPV",
    "heuristic": "manhattan",
    "board": [
        [1, 8, 2],
        [0, 4, 3],
        [7, 6, 5]
    ]
}
```

#### Heurística Local con backtracking
```json
// parameters.json
{
    "algorithm": "local-bt",
    "heuristic": "manhattan",
    "board": [
        [1, 8, 2],
        [0, 4, 3],
        [7, 6, 5]
    ]
}
```

#### Heurística Global
```json
// parameters.json
{
    "algorithm": "global",
    "heuristic": "manhattan",
    "board": [
        [1, 8, 2],
        [0, 4, 3],
        [7, 6, 5]
    ]
}
```




