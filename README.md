# Experta CRUD

Este es un Microservicio para realizar un CRUD de clientes.

## Resumen  
El proyecto Spring fue creado a partir del generador de codigo de Swagger [swagger-codegen](https://github.com/swagger-api/swagger-codegen)

La libreria que integra swagger con spring es [springfox](https://github.com/springfox/springfox)  

Ejecutar la aplicacion como una aplicacion java estandar.

La documentacion API se puede visualizar en accediendo a http://localhost:8089/experta-exercise/api/v1/swagger-ui.html

Su defición se encuentra documentada en expertaExerciseApi.yaml

Puede modificar el puerto y otros parametros en application.properties

## Base de datos
Como base de datos implemente [H2](https://www.h2database.com/html/main.html).

Su configuación puede modificarse en application.properties

La definición del esquema de base de datos (DDL) se encuentra en el archivo "schema.sql"

## Arquitectura
Para esta solución se implemento una arquitectura de capas (Controller, servicios, repositorios)
   - **Controller:** Controlladores, se encargar del filtrar peticiones, el manejo de excepcion y generacón de respuestas.
   - **Servicios:** Esta capa se encarga de la logica de negocio y transformación del datos del modelo de vista al de respositorio.
   - **Repositorio:** Encargado de almacenar entidades y su estado. 
