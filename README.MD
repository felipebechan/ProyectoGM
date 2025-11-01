
## Instrucciones de Ejecución

Este proyecto está construido con Gradle y puede ser ejecutado en diferentes IDEs. A continuación se detallan los pasos para Eclipse y VS Code.

### En Eclipse (Método del Profesor)

https://www.youtube.com/watch?v=tr2DdwocdaY

La configuración del proyecto en Eclipse sigue la metodología explicada en el video tutorial del profesor. Los pasos generales son:

1.  **Generar un Proyecto Base (si se empieza de cero):** Utilizar el `gdx-setup.jar` de LibGDX para generar la estructura inicial del proyecto, seleccionando únicamente la plataforma "Desktop".
2.  **Importar a Eclipse:** Ir a `File -> Import... -> Gradle -> Existing Gradle Project` y seleccionar la carpeta raíz del proyecto generado. Eclipse se encargará de configurar los sub-proyectos `core` y `desktop`.
3.  **Copiar Archivos de Código:** Reemplazar el código del proyecto `core` con los archivos de este repositorio.
4.  **Copiar Assets:** Asegurarse de que todos los archivos de imagen y sonido estén en la carpeta `assets` del proyecto `core` (o del `desktop` si se generó ahí).
5.  **Configurar Ejecución:** Crear una nueva `Run Configuration` de tipo `Java Application`.
    *   **Project:** Seleccionar el proyecto `desktop`.
    *   **Main class:** Seleccionar `DesktopLauncher`.
    *   **Arguments > Working directory:** Establecer la ruta a la carpeta `assets` del proyecto.
6.  **Ejecutar:** Correr la configuración recién creada.



### En VS Code (Método Alternativo)

1.  **Prerrequisitos:**
    *   Tener instalado un **JDK 11** 
    *   En VS Code, instalar las extensiones **"Extension Pack for Java"** y **"Gradle for Java"** de Microsoft.
2.  **Abrir Proyecto:**
    *   Abrir VS Code e ir a `File -> Open Folder...` y seleccionar la carpeta raíz de este proyecto.
    *   Esperar a que Gradle configure el proyecto automáticamente.
3.  **Configurar JDK para el Proyecto:**
    *   Presionar `Ctrl + Shift + P` para abrir la paleta de comandos.
    *   Escribir y seleccionar `Java: Configure Java Runtime`.
    *   En la sección "Project JDK(s)", asegurarse de que el proyecto esté utilizando la instalación de **JDK 11**.
4.  **Ejecutar el Juego:**
    *   Abrir una nueva terminal integrada en VS Code (`Ctrl + Ñ` o `Ctrl + ``).
    *   Ejecutar el comando de Gradle para el módulo de escritorio:
        ```bash
        .\gradlew desktop:run
        ```

## GM1.1: Descripción Funcional del Juego

**Objetivo:** Controlar un personaje (Steve) para recolectar objetos "buenos" que caen, mientras se esquivan objetos "malos" y se aprovechan power-ups que alteran la jugabilidad.

**Elementos Principales:**
*   **Jugador:** Se controla a STEVE que sostiene un caldero de minecraft.
*   **Objetos a Recolectar:** Manzana y Sandía. Suman puntos.
*   **Obstáculos:** TNT. Resta una vida al contacto.
*   **Power-ups:**
    *   **Estrella del Nether:** Otorga puntos extra y una vida adicional.
    *   **Poción de Tamaño:** Aumenta el tamaño del jugador temporalmente.
    *   **Poción de Velocidad:** Aumenta la velocidad de movimiento del jugador temporalmente.
    *   **Perla de Ender:** Activa el modo "Giro de Gravedad", donde los objetos caen de los lados y los controles cambian a un eje vertical.

**Controles:**
*   **Modo Normal:** Flechas Izquierda/Derecha o A/D.
*   **Modo Giro de Gravedad:** Flechas Arriba/Abajo o W/S.

![Captura de Pantalla del Juego](Animation.gif)

## GM1.2: Análisis Técnico y Diseño de Software

La arquitectura del proyecto se basa utilizando la clase `Game` de LibGDX para gestionar diferentes `Screen` (pantallas) que representan los estados del juego (menú, partida, game over).

A continuación, se detalla cómo se cumplieron los requisitos de diseño:

**GM1.4: Clase Abstracta**
*   Se implementó una clase `public abstract class ObjetoCayendo` para centralizar el comportamiento común de todos los objetos que caen (posición, velocidad, movimiento y renderizado base).
*   Clases concretas como `Fruta`, `Bomba` y los distintos `PowerUp` heredan de esta y especializan el método abstracto `aplicarEfecto()`, aplicando el polimorfismo para definir su interacción única con el jugador.

**GM1.5: Interfaz**
*   Se definió una `public interface Dibujable` con un único método `render(SpriteBatch batch)`.
*   Las clases `Tarro` y `ObjetoCayendo` implementan esta interfaz, lo que obliga a que todos los elementos renderizables del juego tengan un método de dibujado estandarizado. Esto desacopla la lógica de renderizado de la clase `GameScreen`.

**GM1.6: Encapsulamiento y Principios OO**
*   Todos los atributos de las clases de dominio se declaran como `private` o `protected`. El acceso y la modificación del estado interno de los objetos (ej. vidas del jugador, temporizadores de power-ups) se gestionan a través de métodos públicos, protegiendo su consistencia.
*   El diseño es un ejemplo práctico del **Principio Abierto/Cerrado (SOLID)**. Para añadir los power-ups de Tamaño y Velocidad, solo fue necesario crear sus respectivas clases que heredan de `ObjetoCayendo` y actualizar la lógica de creación en `ControladorDeObjetos`, sin modificar clases ya existentes como `GameScreen` o `Tarro`.

**Extras Implementados:**
*   **Manejo de Excepciones:** La carga de todos los assets en `GameScreen` está contenida en un bloque `try-catch` que captura `GdxRuntimeException`. Si un archivo no se encuentra, el juego no crashea, sino que registra un error en la consola y se cierra de forma controlada.

## GM1.3: Diagrama UML

![Diagrama UML](uml.png)

## GM1.7: Control de Versiones

El código fuente del proyecto está versionado con Git. El historial de commits se encuentra en este repositorio de GitHub y sigue una estructura lógica, separando la implementación de la base, la refactorización a herencia/interfaces y la adición de nuevas funcionalidades en commits.