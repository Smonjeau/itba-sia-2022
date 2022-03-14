
# TP1
Generador de soluciones para el problema de rompecabeza de números de 8 dígitos.
Se utilizan las estrategias de busqueda: BPA, BPP, BPPV, Heurística Local, Heurística Global y A*.

## Archivo de configuración
Los parámetros de ejecución se leeran de un archivo json cuya ubicación se debe poner como parámetro de ejecución.
Los posibles parámetros son:

| Parameter     | Type     | Values                                         | Description                                                                       |
| :-------------| :------- | :----------------------------------------------| :-------------------------------------------------------------------------------- |
| `algorithm`   | `String` | `BPA`, `BPP`, `BPPV`, `local`, `local-bt`, `global`, `A*`  | Estrategía de busqueda que se utilizará                                           |
| `guess`       | `int`    |                                                | Limite inicial utilizado en la estrategía BPPV                                    |
| `heuristic`   | `String` | `manhattan`, `misplaced`, `worst_case`         | Heuristica ha utilizar. Es necesario para las estrategías `local`, `global`, `A*` |

## Resultados de ejecución
Los resultados de ejecución se encontraran en el archivo `logs.txt`.

