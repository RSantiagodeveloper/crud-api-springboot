# CRUD-API

Proyecto de ejemplificacion para servicios que realizan operaciones CRUD sobre una base de datos Postgresql
utilizando Spring-boot 3, JAVA 17, JPA, Hibernate, etc. Contiene capa de seguridad con Spring Security
y hace uso de una base de datos Postgresql.

## Stack tecnologico.
* java version minima -> 17
* java version utilizada -> 21
* apache maven -> 3.8.8
* postgresql version -> 17.5.1
* Sistema Operativo -> Linux Debian 12
* git version -> 2.39.5

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
### core.application
Representa la capa de Aplicacion. Solamente resguarda los controladores y modelos de Solicitud-Respuesta que comunican al
microservicio con el cliente web que lo consuma.

### core.application.controller
Contiene unicamente las clases Java anotadas con @RestController y que define los endpoints para las apis del microservicio.

### core.application.models
Contiene todos los modelos de entrada y salida utilizados por el proyecto.
* models.request -> todos los objetos de solicitud.
* models.response -> todos los objetos de respuesta.

### core.domain
### core.domain.models
Contiene todos los modelos de DDBB o de transferencia de datos utilizados por el proyecto.
* models.dto -> todos los objetos de transferencia de datos.
* models.entity -> todas los objetos que representan entidades de DDBB.

### core.domain.services
Contiene todas las declaraciones y definiciones de la logica de las Apis que proporciona el microservicio.
* services.definitions -> todas las interfaces que declaran los servicios.
* services.implemetation -> todas las implementaciones de la logica de los servicios declarados.

### core.Infrastruture
Representa la capa de infraestructura. Contiene todos los elementos que comunican con otras APIs, servidores de bases
de datos u otros elementos externos con el microservicio.

### core.infrastructure.repository
Contiene todas las interfaces que definene los metodos de iteraccion con la base de datos.
* EntityNameRepository.java -> Declara todos los metodos que se utilizaran para consultas personalizadas.
* EntityNameJDBCRepository.java -> Define todos los metodos que se utilizan para consultas personalizadas. 
* EntityNameJpaRepository.java -> Declara y define todos los metodos que se utilizan para consultasa partir de la implementacion de JpaRepository.

### core.errors
Contiene todas las definiciones de Excepciones personalizadas (errors.exceptions) y su configuracion en el AdvisorController
para su manejo en la respuesta de las apis del microservicio.

### core.util
Contiene todas las clases estaticas y no estaticas para definicion de Constantes o herramientas utiles para complementar
la implementacion de la logica de los servicios.

## security

Contiene todos los paquetes utilizados en la capa de seguridad del microservicio usando spring-security.

### security.config
Configuracion general de los Java Bean para SpringSecurity. dentro de la clase SpringSecurityConfig
se encunetra el metodo 'filterChain' en el cual se definen los filtros de seguridad para proteger mediante
token de sesion con JWT, los enpoints de las API-REST del CRUD. Asi mismo. indican el nivel de acceso o
privilegio requerido para poder ejecutar cada operacion del CRUD.

### security.filter
Contiene los filtros para generar el token JWT una vez autenticado el usuario o para validar el token
JWT cuando se solicita el acceso a un API.

### security.model
contiene el modelo de respuesta de la capa de seguridad.

### security.util
Utilidades para el manejo de la capa de seguridad.

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

## INSTALACION y USO.
* descomprima o clone el repositorio en su espacio de trabajo local
* ejecute el comando ``` $ mvn clean ``` para instalar todas la dependencias del proyecto.
* **Debe de tener instalado un servidor Postgresql en su sistema o tener lista la conexion para probar.** 
* Configuracion de la conexion.
  * En su servidor de DDBB, cree una nueva base de datos. Solo basta con tenerla creada, **el proyecto hace uso del ORM que
  porporciona JAKARTA**, por lo que toda la estructura de tablas en base de datos se creara en automatico una vez se inicie
  el proyecto. **NO USE UNA BASE YA EXISTENTE O SE LA SOBREESCRIBIRA**
  * dirijase a la siguiente ruta: "src/main/resources" 
  * cree un nuevo archivo llamado "application-dev.yml"
    * puede utilizar el ejemplo del archivo: src/main/resources/application-env-example.txt
  * indique el host, db, esquema, usuario y contraseña como se le indica.
  * verifique en el archivo **application.yml** tenga configurado en su apartado spring.profiles.default: dev
* Configurar nivel de logs. 
  * En el mismo archivo donde condiguro la conexion de postgrest, agregue los siguientes campos:
    * SQL_LOGS_LEVEL: ERROR
    * PROJECT_LOGS_LEVEL: DEBUG
  * para tener acceso a los diferentes niveles de log en el dialecto de SQL o en los paquetes del proyecto.
    * INFO, ERROR, DEBUG, ETC.
* Una vez configurada la conexion y logs, puede iniciar el proyecto con el comando ```$ mvn spring-boot:run```
  * En los recursos ya existe un archivo de importacion para rellenar el catalogo de CRUD con datos de prueba en la DDBB.
* Utilice la **coleccion postman** proporcionada en la ruta "api-colection" para hacer las peticiones a los enpoints contenidos
* por defecto el microservicio se monta en http://localhost:8080/
* Use los endpoints de **AUTH** para crear un nuevo usuario e inicar sesion para poder usar los enpoint del CRUD.
* Añada el jwt generado en el encabezado "AUTORITATION" con el prefijo "Bearer " de la peticion.
* Creacion de usuarios:
  * Administrador: en la peticion preparada del apartado Body, marque como **true**, el campo "admin".
  * Usuario: en la peticion preparada del apartado Body, marque como **false**, el campo "admin".

## Comandos

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