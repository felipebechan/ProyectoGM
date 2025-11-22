## Instrucciones de Ejecución

Este proyecto está construido con Gradle y puede ejecutarse en diferentes IDEs (Eclipse y VS Code).

-------------------------------------------------------------------

## Ejecución en Eclipse (Método del Profesor)

Video del profesor:  
https://www.youtube.com/watch?v=tr2DdwocdaY

Pasos:

1. Generar un proyecto base (si empiezas desde cero) usando gdx-setup.jar y seleccionando solo la plataforma Desktop.
2. Importar a Eclipse desde: File → Import… → Gradle → Existing Gradle Project.  
3. Copiar el código presente en este repositorio dentro del módulo core.
4. Verificar que todos los assets estén dentro de la carpeta assets.
5. Crear Run Configuration:
   - Project: desktop
   - Main class: DesktopLauncher
   - Working directory: carpeta assets
6. Ejecutar el proyecto.

-------------------------------------------------------------------

## Ejecución en VS Code

Prerrequisitos:
- JDK 11
- Extensiones:
  - Extension Pack for Java
  - Gradle for Java

Pasos:

1. Abrir el proyecto con File → Open Folder… y esperar a que Gradle configure todo.
2. Configurar JDK: Ctrl + Shift + P → Java: Configure Java Runtime → elegir JDK 11.
3. Ejecutar desde la terminal integrada el comando:
   ./gradlew desktop:run

-------------------------------------------------------------------

# GM1.1 – Descripción Funcional del Juego

Objetivo: Controlar a Steve con un caldero para recolectar objetos buenos, evitar los malos y aprovechar power-ups.

Elementos del juego:
- Jugador: Steve
- Objetos buenos: Manzana y Sandía (suman puntos)
- Objetos malos: TNT (quita una vida)
- Power-ups:
  - Estrella del Nether: puntos extra + vida adicional
  - Poción de Tamaño: Steve se agranda temporalmente
  - Poción de Velocidad: Steve se mueve más rápido un tiempo
  - Perla de Ender: activa modo "Giro de Gravedad"

Controles:
- Modo normal: flechas izquierda/derecha o teclas A/D
- Giro de gravedad: flechas arriba/abajo o teclas W/S

![Captura del Juego](Animation.gif)

-------------------------------------------------------------------

# GM1.2 – Análisis Técnico y Diseño

El proyecto usa la estructura típica de LibGDX con pantallas Screen gestionadas desde una clase que extiende Game.

GM1.4 – Clase Abstracta:
Se creó la clase abstracta ObjetoCayendo que contiene:
- posición
- velocidad
- movimiento base
- renderizado base

Evidencia: ObjetoCayendo.java (líneas 1-70)
Las subclases (Fruta.java, Bomba.java, PowerUpEstrella.java, PowerUpGiro.java, PowerUpTamanio.java, PowerUpVelocidad.java) implementan el método abstracto aplicarEfecto().

GM1.5 – Interfaz Dibujable:
Define el método render(SpriteBatch batch).  

Evidencia: Dibujable.java (líneas 1-6)
Implementada por: Tarro.java (línea 11) y ObjetoCayendo.java (línea 12)
Usada en: GameScreen.java para renderizar elementos (líneas 115-116)

GM1.6 – Encapsulamiento y Principios OO:
- Atributos privados o protegidos en todas las clases
- Acceso mediante getters/setters (ej: Tarro.java líneas 40-48)
- Aplicación del principio Open/Closed (OCP): se agregaron PowerUpTamanio y PowerUpVelocidad sin modificar código existente

Extras:
- Manejo de excepciones en carga de assets (GameScreen.java líneas 47-51)

-------------------------------------------------------------------

# GM1.3 – Diagrama UML

Ver archivo: uml_final.png

-------------------------------------------------------------------

# GM1.7 – Control de Versiones

Proyecto manejado con Git.  
Repositorio: https://github.com/felipebechan/ProyectoGM
Los commits muestran una progresión lógica desde la base del juego hasta la incorporación de herencia, interfaces y power-ups.

-------------------------------------------------------------------

# ENTREGA FINAL – Patrones de Diseño

## GM2.1 – Patrón Singleton

### Problema:
Los recursos (texturas, sonidos, música) se recargaban cada vez que se creaba una instancia de GameScreen, causando:
- Desperdicio de memoria
- Tiempos de carga redundantes
- Violación del principio DRY

### Contexto:
El juego necesita acceder a recursos compartidos desde múltiples clases (GameScreen, ControladorDeObjetos, Tarro) de manera eficiente.

### Solución:
Implementación de GestorDeRecursos como Singleton:

Evidencia en código:
- Clase: GestorDeRecursos.java
- Instancia estática privada: línea 10
- Constructor privado: línea 27
- Método getInstancia(): líneas 33-38
- Carga centralizada de recursos: líneas 40-62

Uso del Singleton:
- GameScreen.java líneas 48-53
- ControladorDeObjetos.java línea 63
- ProyectoJuegoLluvia.java línea 20 (dispose)

### UML - Participantes:

Diagrama de GestorDeRecursos:
- Clase con instancia estática privada
- Constructor privado que impide creación externa
- Método público getInstancia() que retorna la única instancia
- Métodos getter para acceder a recursos (texturas y sonidos)
- Método dispose() para liberar recursos

### Roles:
- GestorDeRecursos actúa como Singleton
- GameScreen, ControladorDeObjetos y ProyectoJuegoLluvia actúan como clientes

### Beneficios:
- Recursos cargados solo una vez durante toda la ejecución
- Acceso global controlado
- Gestión centralizada del ciclo de vida de recursos
- Reducción significativa del uso de memoria

-------------------------------------------------------------------

## GM2.2 – Patrón Template Method

### Problema:
Los objetos cayendo comparten comportamiento común (actualización por frame) pero algunos podrían necesitar personalizaciones antes o después del movimiento.

### Contexto:
Todos los objetos siguen este flujo:
1. Preparación antes del movimiento
2. Actualización de posición
3. Acciones posteriores al movimiento

Se necesita un mecanismo extensible sin duplicar código.

### Solución:
Implementación de Template Method en ObjetoCayendo:

Evidencia en código:
- Clase abstracta: ObjetoCayendo.java
- Template Method (final): líneas 24-28 método actualizar()
- Hook antesDeMoverse(): líneas 31-34
- Hook despuesDeMoverse(): líneas 37-40
- Operación concreta mover(): líneas 43-46

Uso del patrón:
- ControladorDeObjetos.java línea 72: invoca objeto.actualizar(delta)

### UML - Participantes:

Jerarquía:
- ObjetoCayendo (clase abstracta)
  - Define actualizar() como template method final
  - Define hooks antesDeMoverse() y despuesDeMoverse() (vacíos por defecto)
  - Define mover() como operación concreta
  - Define aplicarEfecto() como método abstracto

Subclases concretas:
- Fruta.java
- Bomba.java  
- PowerUpEstrella.java
- PowerUpGiro.java
- PowerUpTamanio.java
- PowerUpVelocidad.java

### Roles:
- ObjetoCayendo: Clase abstracta que define el template method
- Subclases concretas: Implementan aplicarEfecto() y pueden sobrescribir hooks opcionales
- ControladorDeObjetos: Cliente que usa el template method

### Interrelaciones:
El método actualizar() llama en orden:
1. antesDeMoverse() - hook que subclases pueden sobrescribir
2. mover() - operación concreta definida en la clase abstracta
3. despuesDeMoverse() - hook que subclases pueden sobrescribir

### Beneficios:
- Define estructura de algoritmo reutilizable
- Evita duplicación de código común
- Permite personalización sin romper el flujo general
- Facilita extensión futura (ej: FrutaEspecial con movimiento ondulatorio)

-------------------------------------------------------------------

## GM2.3 – Patrón Strategy

### Problema:
La generación de objetos cayendo varía según el modo de juego (Normal, Gravedad Izquierda, Gravedad Derecha). El código original usaba switch-case extenso, difícil de mantener y violando el principio Open/Closed.

### Contexto:
Tres modos de juego con diferentes características:
- Modo Normal: objetos caen desde arriba, alta variedad (7 tipos)
- Modo Gravedad Izquierda: objetos desde derecha, solo 3 tipos
- Modo Gravedad Derecha: objetos desde izquierda, solo 3 tipos

Cada modo requiere diferentes posiciones iniciales, velocidades y probabilidades de generación.

### Solución:
Implementación del patrón Strategy con:
- Interfaz común: EstrategiaGeneracion
- Tres estrategias concretas, una por modo
- Context: ControladorDeObjetos

Evidencia en código:

Interfaz Strategy:
- EstrategiaGeneracion.java (líneas 1-6)

Estrategias Concretas:
- EstrategiaGeneracionNormal.java (líneas 1-27)
- EstrategiaGeneracionIzquierda.java (líneas 1-29)
- EstrategiaGeneracionDerecha.java (líneas 1-29)

Context (ControladorDeObjetos):
- Atributos de estrategias: líneas 15-18
- Inicialización: líneas 21-25
- Cambio de estrategia: líneas 33-44 (método setEstrategia)
- Uso de estrategia: líneas 63-65 (método crearObjetoCayendo)

Coordinación:
- GameScreen.java línea 79: cambia estrategia según estado

### UML - Participantes:

Componentes:

1. EstrategiaGeneracion (interfaz)
   - Define método generarObjeto()

2. Estrategias Concretas (implementan interfaz)
   - EstrategiaGeneracionNormal
   - EstrategiaGeneracionIzquierda
   - EstrategiaGeneracionDerecha
   Cada una implementa generarObjeto() con lógica específica

3. ControladorDeObjetos (Context)
   - Mantiene referencia a estrategiaActual
   - Mantiene instancias de las 3 estrategias concretas
   - Método setEstrategia() cambia la estrategia activa
   - Método crearObjetoCayendo() delega a estrategiaActual

### Roles:
- EstrategiaGeneracion: Define la interfaz común para todos los algoritmos
- Estrategias concretas: Implementan variaciones del algoritmo
- ControladorDeObjetos: Mantiene referencia a estrategia y la utiliza
- GameScreen: Decide cuándo cambiar de estrategia

### Interrelaciones:
1. ControladorDeObjetos tiene una referencia a EstrategiaGeneracion
2. En tiempo de ejecución, esta referencia apunta a una de las tres estrategias concretas
3. Cuando GameScreen detecta cambio de modo, llama a setEstrategia()
4. ControladorDeObjetos cambia su referencia a la estrategia apropiada
5. Al generar objetos, delega el trabajo a la estrategia actual

### Flujo de ejecución:
1. GameScreen detecta cambio de estado (power-up giro)
2. Llama a controlador.reorientarTodosLosObjetos(nuevoEstado)
3. ControladorDeObjetos ejecuta setEstrategia(nuevoEstado)
4. Al crear nuevo objeto: estrategiaActual.generarObjeto(recursos)
5. Estrategia concreta determina tipo, posición y probabilidades

### Beneficios:
- Elimina condicionales complejos (switch-case)
- Algoritmos intercambiables en tiempo de ejecución
- Fácil agregar nuevos modos sin modificar código existente
- Cada estrategia encapsulada en su propia clase
- Cumple principio Open/Closed

-------------------------------------------------------------------

## GM2.4 – Control de Versiones (Entrega Final)

Commits principales en rama feature/entrega-final:

1. Implementación del Singleton GestorDeRecursos
2. Refactor de ObjetoCayendo a Template Method
3. Implementación del patrón Strategy
4. Integración final y documentación

Requisito cumplido: Más de 3 commits adicionales a la entrega de avance.

-------------------------------------------------------------------

# Diagrama UML – Entrega Final

Ver archivo: uml_final.png

El diagrama muestra:
- Patrón Singleton en GestorDeRecursos
- Patrón Template Method en ObjetoCayendo con método final actualizar()
- Patrón Strategy con EstrategiaGeneracion y sus 3 implementaciones
- Relaciones entre GameScreen, ControladorDeObjetos y las estrategias
- Dependencias con GestorDeRecursos

-------------------------------------------------------------------

# Resumen de Arquitectura

El proyecto integra tres patrones que trabajan juntos de forma cohesiva:

1. Singleton (GestorDeRecursos)
   - Gestiona recursos de manera centralizada
   - Evita duplicación en memoria
   - Proporciona acceso global controlado

2. Template Method (ObjetoCayendo)
   - Define comportamiento común de objetos cayendo
   - Permite personalización mediante hooks
   - Evita duplicación de código

3. Strategy (EstrategiaGeneracion)
   - Cambia dinámicamente la forma de generar objetos
   - Elimina condicionales complejos
   - Facilita extensión de modos de juego

Características de la arquitectura:
- Mantenible: cada patrón tiene responsabilidad clara
- Escalable: fácil agregar nuevas funcionalidades
- Flexible: comportamientos intercambiables en tiempo real
- Eficiente: gestión óptima de recursos
- Cohesivo: patrones trabajan juntos sin acoplamiento excesivo

-------------------------------------------------------------------





-------------------------------------------------------------------
