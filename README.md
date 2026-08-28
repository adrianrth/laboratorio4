# APIs REST - LaboratorioV

Laboratorio de desarrollo de APIs REST independientes utilizando **Spring Boot** y **Maven**, aplicando los conceptos de HTTP, JSON, controladores REST y operaciones CRUD con listas en memoria.

## Objetivo

Desarrollar 10 APIs REST independientes (Productos, Estudiantes, Libros, Empleados, Peliculas, Cursos, Vehiculos, Tareas, Clientes y Pedidos), cada una con operaciones CRUD completas (GET, POST, PUT, PATCH, DELETE) usando listas en memoria como fuente de datos, y probarlas con Postman.

## Tecnologias

- Java 17
- Spring Boot 3.3.4 (Spring Web)
- Maven

## Estructura del proyecto

```
spring-apis-lab/
│
├── pom.xml
│
└── src/main/java/com/lab/apis/
    │
    ├── ApisApplication.java
    │
    ├── controller/
    │   ├── ProductoController.java
    │   ├── EstudianteController.java
    │   ├── LibroController.java
    │   ├── EmpleadoController.java
    │   ├── PeliculaController.java
    │   ├── CursoController.java
    │   ├── VehiculoController.java
    │   ├── TareaController.java
    │   ├── ClienteController.java
    │   └── PedidoController.java
    │
    └── model/
        ├── Producto.java
        ├── Estudiante.java
        ├── Libro.java
        ├── Empleado.java
        ├── Pelicula.java
        ├── Curso.java
        ├── Vehiculo.java
        ├── Tarea.java
        ├── Cliente.java
        └── Pedido.java
```

## Como ejecutar el proyecto

```bash
mvn spring-boot:run
```

La aplicacion se levanta en `http://localhost:8080`.

## APIs y Endpoints

Cada API expone los mismos 6 endpoints (`GET` todos, `GET` por id, `POST`, `PUT`, `PATCH`, `DELETE`), y arranca con 5 registros precargados en memoria.

| # | API | Base URL |
|---|-----|----------|
| 1 | Productos | `/api/productos` |
| 2 | Estudiantes | `/api/estudiantes` |
| 3 | Libros | `/api/libros` |
| 4 | Empleados | `/api/empleados` |
| 5 | Peliculas | `/api/peliculas` |
| 6 | Cursos | `/api/cursos` |
| 7 | Vehiculos | `/api/vehiculos` |
| 8 | Tareas | `/api/tareas` |
| 9 | Clientes | `/api/clientes` |
| 10 | Pedidos | `/api/pedidos` |

### Ejemplo (Productos)

```
GET     /api/productos           -> lista todos los productos
GET     /api/productos/{id}      -> obtiene un producto por id
POST    /api/productos           -> crea un producto nuevo
PUT     /api/productos/{id}      -> actualiza un producto completo
PATCH   /api/productos/{id}      -> actualiza campos parciales de un producto
DELETE  /api/productos/{id}      -> elimina un producto
```

Ejemplo de `PATCH` en Tareas (actualizar solo el estado):

```json
{
    "completada": true
}
```

Ejemplo de `PATCH` en Pedidos (cambiar solo el estado):

```json
{
    "estado": "ENVIADO"
}
```

## Coleccion de Postman

La coleccion `APIs REST - LaboratorioV.postman_collection.json` (carpeta `postman/`) contiene una carpeta por cada API, con los 6 requests (GET todos, GET por ID, POST, PUT, PATCH, DELETE) listos para importar y probar contra `http://localhost:8080`.

## Repositorio

Enlace al repositorio en GitHub: `<pendiente - agregar URL del repositorio>`
