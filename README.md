# CRUD-API

Proyecto de ejemplificacion para servicios que realizan operaciones CRUD sobre una base de datos Postgresql
utilizando Spring-boot 3, JAVA 17, JPA, Hibernate, etc.

## Estructura
El proyecto consta de los componetes ordenados en cada uno de los paquetes listados a continuacion y siguiendo
las reglas establecidas,

### application.yml
* configuracion general del proyecto. Aqui se describe el nombre del proyecto, que debe de ser acorde con lo declarado 
en el archivo pom.xml
* la version del proyecto debe de ser equivalente al declarado en el pom.xml
* La configuracion de las credenciales o informacion sencible del proyecto, debe de estar por separado
en un archivo de perfil application-{profile|env}.yml siguiendo la estructura sugerida en el archivo application-env-example.txt.
**Este no se incluye en el proyeto** para no exponer la informacion sencible en el repositorio.

## paquetes
### application
Representa la capa de Aplicacion. Solamente resguarda los controladores y modelos de Solicitud-Respuesta que comunican al
microservicio con el cliente web que lo consuma.

### application.controller
Contiene unicamente las clases Java anotadas con @RestController y que define los endpoints para las apis del microservicio.

### application.models
Contiene todos los modelos de entrada y salida utilizados por el proyecto.
* models.request -> todos los objetos de solicitud.
* models.response -> todos los objetos de respuesta.

### domain
### domain.models
Contiene todos los modelos de DDBB o de transferencia de datos utilizados por el proyecto.
* models.dto -> todos los objetos de transferencia de datos.
* models.entity -> todas los objetos que representan entidades de DDBB.

### domain.services
Contiene todas las declaraciones y definiciones de la logica de las Apis que proporciona el microservicio.
* services.definitions -> todas las interfaces que declaran los servicios.
* services.implemetation -> todas las implementaciones de la logica de los servicios declarados.

### Infrastruture
Representa la capa de infraestructura. Contiene todos los elementos que comunican con otras APIs, servidores de bases
de datos u otros elementos externos con el microservicio.

### infrastructure.repository
Contiene todas las interfaces que definene los metodos de iteraccion con la base de datos.
* EntityNameRepository.java -> Declara todos los metodos que se utilizaran para consultas personalizadas.
* EntityNameJDBCRepository.java -> Define todos los metodos que se utilizan para consultas personalizadas. 
* EntityNameJpaRepository.java -> Declara y define todos los metodos que se utilizan para consultasa partir de la implementacion de JpaRepository.

### errors
Contiene todas las definiciones de Excepciones personalizadas (errors.exceptions) y su configuracion en el AdvisorController
para su manejo en la respuesta de las apis del microservicio.

### util
Contiene todas las clases estaticas y no estaticas para definicion de Constantes o herramientas utiles para complementar
la implementacion de la logica de los servicios.

### TEST
Contiene todas las pruebas unitarias de los controladores, Apis y Repositorios.

## Plugings fundamentales

- **LOMBOK**. 
  - Anotaciones utiles para simplificar bloques de codigo comunes en programas Java.
- **MAVEN JACOCO**
  - Reporte de covertura de pruebas unitarias en controladores, servicios o cualquier otro tipo implementaciones.
  - Se ignoran paquetes que contienen objetos de transporte de datos, modelos, entidades, constantes, exceptions, etc.
    - Se pueden configurar mas rutas en el apartado: **build.plugins.plugin-jacoco.configuraton.excludes** del **POM.XML**
    - La propiedad **<main.package.path>** debe configurarse con el nombre del paquete principal de cada proyecto.
  - El reporte se genera en la fase de TEST.
    - El Reporte se encuentra en la ruta **target/site/jacoco/index.html**
  - El coverage general minimo para las pruebas, es del 85% y se configura en el mvn prop: **<general.coverage.porcent>**
- **MAVEN CHECKSTYLE**
  - Analisis y control de calidad en el codigo estatico.
  - La cofiguracion de reglas general se encuentra en el archivo checkstyle.xml en la raiz de proyecto.
    - Todas las reglas se basan en el archivo de [sun_checks.xml](https://github.com/checkstyle/checkstyle/blob/master/src/main/resources/sun_checks.xml).
    - Las reglas se personalizadas, se basan en las configuraciones disponibles en [Configuraciones_CheckStyle](https://checkstyle.org/config.html)
  - El reporte de Checkstyle se genera durante la fase de VALIDATE, por lo que es **IMPORTANTE cumplir con las reglas establecidas en el checkstyle** para lograr una compilacion exitosa.
    - El reporte se encuentra en la ruta **target/checkstyle-result.xml**

## Comandos

Arraque del servidor de DDBB en Local (LAMPP)

```console
foo@bar:~$ sudo /opt/lampp/xammp startmysql
```

Compilacion del proyecto

```console
foo@bar:~$ mvn clean compile
```

Ejecucion de Pruebas

```console
foo@bar:~$ mvn clean test
```

Ejecucion local del proyecto

```console
foo@bar:~$ mvn spring-boot:run
```