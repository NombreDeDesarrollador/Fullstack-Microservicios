# Plan De Pruebas - Microservicios

## Objetivo

Evaluar el funcionamiento de los microservicios principales del proyecto

### Microservicios a testear:

* integrantes_service
* grupos_service

## Las pruebas serán realizadas con Postman, Sweagger u OpenApi, navegador y Eureka.

## P-01 CREAR UN INTEGRANTE.
### Metodo: Crear Integrante
### Objetivos:
- Ingresar un integrante de manera exitosa junto a un mensaje de aprobacion.
- Validar la ausencia de un valor y mostrar un mensaje de error.

### Objetivos cumplidos: 2/2

## P-02 MOSTRAR INTEGRANTES.
### Metodo: Obtener Integrantes
### Objetivos:

- Mostrar integrantes junto a un mensaje de aprobación.
- En caso de no existir integrantes, mostrar un mensaje diciendo que no hay integrantes.

### Objetivos cumplidos: 2/2

## P-03 MOSTRAR UN INTEGRANTE POR UN ID ESPECÍFICO.
### Metodo: Buscar Integrante Por Id
### Objetivos:

- Ingresar un ID y mostrar al integrante perteneciente al mismo.
- Mostrar mensaje de error en caso de que no exista un integrante con el ID ingresado.

### Objetivos cumplidos: 2/2

## P-04 ACTUALIZAR UN INTEGRANTE POR SU ID.
### Metodo: actualizar
### Objetivos: 

- Generar cambiar los datos deseados de un integrante que ya exista

### Objetivos cumplidos: 1/1

## P-05 ELIMINAR UN INTEGRANTE POR SU ID.
### Metodo: eliminar
### Objetivos:

- Eliminar un integrante que ya exista mediante su ID.
- Validar si no existe un integrante con el ID ingresado, ymostrar mensaje de error.

### Objetivos cumplidos: 2/2

## P-05 MOSTRAR INTEGRANTE Y GRUPO AL CUAL PERTENECE.
### Metodo: obtenerPorGrupo
### Objetivos:

- Mostrar integrante junto a su grupo.
- Validar si no existe un integrante con el ID ingresado, y mostrar mensaje de error.

### Objetivos cumplidos: 2/2

## P-06 VERIFICAR SI LOS MICROSERVICIOS SE MUESTRAN EN EUREKA