# proyecto_clinica_odontologica

Sistema de gestión para una clínica odontológica, escrito en **Java** con interfaz gráfica
**Swing**. Administra pacientes, odontólogos (con especialidades) y turnos sobre una arquitectura
en capas — modelo / repositorio / servicio / vista — con persistencia en disco.

> **Proyecto académico** (POO en Java). El repo incluye el diagrama de clases
> (`diagramaDeClases2.pdf`) y el informe de la segunda entrega (`informeSegundaEntrega.pdf`).

---

## Features

- **Gestión de pacientes** — alta, baja, modificación y búsqueda, con validación de DNI duplicado
- **Gestión de odontólogos con especialidades** — Odontólogo General, Endodoncista y Ortodoncista (por herencia); matrícula única
- **Gestión de turnos** — reserva, estados (`EstadoTurno`) y control de turno ya reservado
- **Interfaz gráfica Swing** — ventana principal con un panel por entidad, tablas (`TableModel`) y un panel de búsquedas
- **Persistencia automática** — serialización a archivos `.dat` (`pacientes.dat`, `odontologos.dat`, `turnos.dat`); los datos se cargan al arrancar
- **Excepciones de dominio propias** — `DniDuplicado`, `MatriculaDuplicada`, `TurnoYaReservado`, `*NoEncontrado`, `DatoInvalido`, todas bajo `ClinicaException`
- **IDs sin colisión** — al cargar, los contadores de ID se sincronizan con el máximo persistido

---

## Arquitectura (en capas)

```
src/
├── Main.java          # arranca: carga los .dat y abre la ventana Swing
├── Modelo/            # entidades: Paciente, Odontologo (+ subtipos), Turno, Domicilio, EstadoTurno
├── Repositorio/       # acceso a datos con interfaz genérica iRepository<T> (CRUD)
├── Servicio/          # lógica de negocio y validaciones (Servicio Paciente/Odontologo/Turno)
├── Persistencia/      # serialización Java a archivos .dat
├── Vista/             # GUI Swing: paneles + TableModels
├── Presentacion/      # menús de consola (front-end alternativo de una entrega previa)
└── Exception/         # jerarquía de excepciones propias (ClinicaException como base)
```

- **`iRepository<T>`** define el contrato CRUD genérico: `guardar` / `buscarPorId` / `actualizar` / `eliminar` / `listar`.
- Cada capa tiene una única responsabilidad: la **Vista** habla con el **Servicio**, el Servicio valida y usa el **Repositorio**, y la **Persistencia** serializa a disco.

---

## Requisitos

- **JDK 8+** (usa Streams y genéricos; Swing viene con el JDK)
- Sin dependencias externas ni build tool: se compila con `javac` o desde IntelliJ

---

## Uso

Desde **IntelliJ**: abrir el proyecto y correr `Main`.

Por línea de comandos:

```bash
cd src
javac Main.java     # compila el árbol de clases
java Main
```

Al arrancar carga los `.dat` si existen (si no, arranca vacío) y abre la ventana principal.

---

## Cómo funciona

- `Main` crea los tres repositorios y servicios, carga los datos persistidos y abre la
  `VentanaPrincipal`.
- Las **reglas del dominio se expresan como excepciones**: un DNI repetido lanza
  `DniDuplicadoException`, reservar un turno tomado lanza `TurnoYaReservadoException`, y así.
- Los **odontólogos usan herencia** para las especialidades (`OdontologoGeneral`, `Endodoncista`,
  `Ortodoncista`), todos derivados de `Odontologo`.
- La persistencia es **serialización Java** (`ObjectOutputStream` → `.dat`), transparente para el
  resto del sistema.

---

## Limitaciones conocidas

- **Persistencia por serialización `.dat`**: queda atada a la versión de las clases; un cambio en
  un modelo puede invalidar los `.dat` viejos.
- **Conviven dos front-ends**: la GUI Swing (`Vista/`) y los menús de consola (`Presentacion/`);
  el que abre `Main` es el gráfico.
- Los archivos `.dat` (datos de pacientes y turnos) no deberían versionarse.
