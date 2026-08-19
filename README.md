# SC-403-Tecnofibras

Integrantes
Arias Jimenez Amanda
Monge Jimenez Yulisa
Monge Jimenez Yeiner
Cabrera Fernandez Fernando

Pyme - TecnofibrasCR



Descripción general



TecnoFibras es una aplicación web tipo catálogo/cotizador para una PYME (TecnofibrasCR), desarrollada con Spring Boot 3.4.5 y Java 21. Permite gestionar un catálogo de productos organizados por categorías, generar cotizaciones para clientes, y administrar usuarios con roles.



Stack tecnológico



Backend: Spring Boot (Web, Data JPA, DevTools)

Vistas: Thymeleaf (server-side rendering)

Base de datos: MySQL (vía mysql-connector-j), ORM con Hibernate/JPA

Frontend/UI: Bootstrap 5.3.3, jQuery 3.7.1, Font Awesome 6.5.2 (vía WebJars)

Utilidades: Lombok (reduce boilerplate en entidades)

Build: Maven



Funcionalidades identificadas

\- Catálogo público de productos, filtrable por categoría

\-CRUD de categorías (listar, guardar, modificar, eliminar)

Modelo de cotizaciones con detalle (producto + cantidad) por cliente/vendedor

Gestión de usuarios con roles y búsqueda por correo

Formulario de contacto



Requisitos de ejecución



Para ejecutar el proyecto se requiere:



\- Java JDK 21

\- NetBeans o un IDE compatible con Maven

\- MySQL Server

\- Maven

\- Base de datos llamada tecnofibras



\## Configuración de base de datos



La conexión a base de datos se encuentra configurada en:



text

src/main/resources/application.properties





Configuración actual:



properties

spring.datasource.url=jdbc:mysql://localhost:3306/tecnofibras

spring.datasource.username=usuario\_tecnofibras

spring.datasource.password=usuario\_clave

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect

spring.jpa.hibernate.ddl-auto=update

server.port=7070


Credenciales

Cliente:
usuario: aarias
contraseña: cliente123

Vendedor:
usuario: ymonge
contraseña: vendedor123

Administrador:
usuario: admin01
contraseña: admin123





Antes de ejecutar el proyecto, se debe crear la base de datos en MySQL.

