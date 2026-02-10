# API REST – Práctica de Examen

Este proyecto consiste en una API REST desarrollada en Java como práctica para un examen. La aplicación está basada en Jakarta EE (JAX-RS y JPA) y permite la gestión de empleados y departamentos mediante servicios REST, ofreciendo operaciones básicas de consulta y mantenimiento de datos.

El proyecto incluye el código fuente de la API, un script SQL para la creación de la base de datos y una colección de pruebas realizada con Postman para comprobar el correcto funcionamiento de los endpoints.

La estructura del proyecto se organiza en un módulo principal que contiene las entidades JPA, los controladores de persistencia y los servicios REST. Además, se incluye el archivo de configuración de la persistencia y los recursos necesarios para el despliegue en un servidor de aplicaciones compatible con Jakarta EE.

Para poner en marcha la aplicación es necesario crear previamente la base de datos ejecutando el script `empleados.sql`, configurar la conexión a la base de datos en el archivo `persistence.xml` y desplegar el proyecto desde NetBeans en un servidor como Tomcat, GlassFish o Payara. Una vez desplegada la aplicación, los servicios REST pueden probarse importando la colección de Postman incluida en el repositorio.

El proyecto está versionado con Git y alojado en un repositorio público de GitHub, que contiene únicamente los archivos necesarios para su evaluación, sin binarios ni ficheros generados automáticamente.

Repositorio del proyecto:
https://github.com/JorgeJGR/API_Rest_PracticandoExamen

Autor: Jorge Juan Guijarro Rabasco

