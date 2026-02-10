# 📝 AppNotas - Gestor de Notas de Escritorio

Aplicación de escritorio desarrollada en Java (Swing) para la gestión completa de notas de texto. Este proyecto implementa el patrón de diseño MVC (Modelo-Vista-Controlador/DAO) y persistencia de datos mediante JDBC con MySQL.

## 🚀 Características

* **Gestión CRUD completa:** Crear, Leer, Actualizar y Borrar notas.
* **Interfaz Gráfica (GUI):** Desarrollada con Java Swing, utilizando `JTable` para listados y `JDialog` para formularios modales.
* **Diseño Moderno:** Implementación del Look & Feel "Nimbus" para una apariencia visual limpia y nativa.
* **Persistencia:** Conexión directa a base de datos MySQL mediante JDBC.
* **Arquitectura por Capas:** Separación clara entre la vista, el modelo de datos y el acceso a datos (DAO).

## 🛠️ Tecnologías Utilizadas

* **Java JDK** (versión 17 o superior recomendada)
* **Maven** (Gestión de dependencias)
* **Java Swing** (Interfaz gráfica)
* **MySQL** (Base de datos)
* **JDBC** (`mysql-connector-j`)

## ⚙️ Configuración de la Base de Datos

Para ejecutar la aplicación, necesitas tener una instancia de MySQL corriendo. Ejecuta el siguiente script SQL para crear la base de datos y la tabla necesaria:

```sql
CREATE DATABASE IF NOT EXISTS notasdb;
USE notasdb;

CREATE TABLE IF NOT EXISTS nota (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    contenido TEXT,
    fecha_creacion DATETIME NOT NULL
);

##  Instalación y Ejecución
Clonar el repositorio:

Bash
git clone [https://github.com/tu-usuario/AppNotas.git](https://github.com/tu-usuario/AppNotas.git)
cd AppNotas
Configurar la Conexión: Abre el archivo src/main/java/dev/juanito/dao/ConnectionDB.java y asegúrate de que las credenciales coincidan con tu base de datos local:

Java
private static final String URL = "jdbc:mysql://localhost:3306/notasdb";
private static final String USER = "root"; // Tu usuario
private static final String PASSWORD = "root"; // Tu contraseña
Compilar y Ejecutar: Si usas Maven desde la terminal:

Bash
mvn clean install
mvn exec:java -Dexec.mainClass="dev.juanito.Main"
O simplemente abre el proyecto en VS Code, IntelliJ o NetBeans y ejecuta la clase Main.java.

## 📂 Estructura del Proyecto
El código está organizado en los siguientes paquetes:

dev.juanito.model: Clases POJO (Entidad Nota).

dev.juanito.dao: Lógica de acceso a datos (ConnectionDB, NotaDAO).

dev.juanito.view: Ventanas y diálogos (FrmPrincipal, DlgListaNotas, DlgEditarNota).

dev.juanito: Clase principal (Main).