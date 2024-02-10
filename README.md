# PROYECTO API - BACKEND CON SPRING

Una práctica habitual en el backend es crear APIS.
En este caso creamos una API con Spring usando **Modelo Vista Controlador** aunque no tenga vista definida ya que solo nos interesa el backend.

## Pasos para la creación de la API

1. Configuramos el archivo ***application.properties***
2. Creamos los paquetes para los controladores, entidades, repositorios y servicios.
3. Creamos las clases modelo, en este caso Product.
4. Creamos el Repository que extenderá de CrudRepository
5. Creamos las clases Service, primero la interface del product con sus métodos
6. También en service creamos la implementación de la interfaz anterior donde daremos forma a los métodos.
7. Creamos unos tests básicos para comprobar los métodos de la implementación
8. Creamos el controlador para desarrollar la lógica de los métodos CRUD
9. Creamos los tests del controlador para no olvidarnos de las buenas prácticas