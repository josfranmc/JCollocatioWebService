# JCollocatioWebService
Implementa una serie de servicios web que permiten realizar la extracción y consultas de colocaciones.

Como corpus textual para realizar las extracciones se utiliza uno basado en los libros existentes en los repositorios del proyecto Gutenberg. Se implementan también una serie de servicios web que permiten la obtención de los textos y la consulta del catálogo descargado.

Los servicios web desplegados hacen uso de las funcionalidades desarrolladas en los proyectos JCollocatio, JGutenbergDownload y JGutenbergCatalog.

## Contenido
+ javadoc: documentación del código
+ src: Código fuente

## Uso
Desde línea de comandos:

java -jar JCollocatioWebService-1.0.jar

## Notas  
La aplicación se ejecuta en el puerto 8028.

Para poder construir el proyecto es necesario instalar previamente los proyectos JCollocatio, JGutenbergDownload y JGutenbergCatalog.

La base de datos sobre los libros del proyecto Gutenberg se guarda en la carpe db/HSQLDB.

El fichero de log generado se guarda en la carpeta log.
