
# TP1
Generador de soluciones para el problema de rompecabeza de números de 8 dígitos.
Se utilizan las estrategias de busqueda: BPA, BPP, BPPV, Heurística Local, Heurística Global y A*.
## Archivo de configuración
Los parámetros de ejecución se leeran del archivo `sia.properties` dentro de la carpeta `resources`.
Los posibles parámetros son:

| Parameter     | Type     | Values                                        | Description                                                                       |
| :-------------| :------- |:----------------------------------------------| :-------------------------------------------------------------------------------- |
| `alg`         | `String` | `BPA`, `BPP`, `BPPV`, `local`, `global`, `A*` | Estrategía de busqueda que se utilizará                                           |
| `guess`       | `int`    |                                               | Limite inicial utilizado en la estrategía BPPV                                    |
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

## Ejecución
```bash
java -jar itba-sia-2022-g18.jar <config.json>
```


