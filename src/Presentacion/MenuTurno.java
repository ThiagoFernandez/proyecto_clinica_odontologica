package Presentacion;

import Modelo.Odontologo;
import Modelo.Paciente;
import Modelo.Turno;
import Servicio.ServicioOdontologo;
import Servicio.ServicioPaciente;
import Servicio.ServicioTurno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class MenuTurno {
    private ServicioTurno servicioTurno;
    private ServicioPaciente servicioPaciente;
    private ServicioOdontologo servicioOdontologo;

    public MenuTurno(ServicioTurno servicioTurno, ServicioPaciente servicioPaciente, ServicioOdontologo servicioOdontologo) {
        this.servicioTurno = servicioTurno;
        this.servicioPaciente = servicioPaciente;
        this.servicioOdontologo = servicioOdontologo;
    }

    public void mostrar() {
        while (true) {
            System.out.println("\n--- TURNOS ---");
            System.out.println("1. Agendar turno");
            System.out.println("2. Buscar turno por ID");
            System.out.println("3. Listar todos los turnos");
            System.out.println("4. Listar turnos por paciente");
            System.out.println("5. Listar turnos por odontólogo");
            System.out.println("6. Listar turnos por fecha");
            System.out.println("7. Confirmar turno");
            System.out.println("8. Cancelar turno");
            System.out.println("9. Completar turno");
            System.out.println("10. Modificar turno");
            System.out.println("11. Eliminar turno");
            System.out.println("0. Volver");

            int opcion = LectorConsola.leerInt("Opción: ");
            switch (opcion) {
                case 1 -> agendar();
                case 2 -> buscar();
                case 3 -> listar();
                case 4 -> listarPorPaciente();
                case 5 -> listarPorOdontologo();
                case 6 -> listarPorFecha();
                case 7 -> confirmar();
                case 8 -> cancelar();
                case 9 -> completar();
                case 10 -> modificar();
                case 11 -> eliminar();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void agendar() {
        // primero mostramos los pacientes y odontólogos disponibles para que el usuario sepa qué IDs existen
        System.out.println("\n-- Pacientes registrados --");
        servicioPaciente.listarPacientes().forEach(p ->
                System.out.println("ID: " + p.getId() + " | " + p.getNombreCompleto() + " | DNI: " + p.getDni())
        );

        System.out.println("\n-- Odontólogos registrados --");
        servicioOdontologo.listarOdontologos().forEach(o ->
                System.out.println("ID: " + o.getId() + " | " + o.getNombreCompleto() + " | Mat: " + o.getMatricula())
        );

        Long idPaciente = LectorConsola.leerLong("\nID del paciente: ");
        Long idOdontologo = LectorConsola.leerLong("ID del odontólogo: ");
        LocalDate fecha = LectorConsola.leerFecha("Fecha del turno");
        LocalTime hora = LectorConsola.leerHora("Hora del turno");

        try {
            Paciente paciente = servicioPaciente.buscarPaciente(idPaciente);
            Odontologo odontologo = servicioOdontologo.buscarOdontologo(idOdontologo);
            servicioTurno.agendarTurno(paciente, odontologo, fecha, hora);
            System.out.println("Turno agendado correctamente.");
            System.out.println("Duración estimada: " + odontologo.calcularDuracionTurno() + " minutos.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscar() {
        Long id = LectorConsola.leerLong("ID del turno: ");
        try {
            Turno turno = servicioTurno.buscarTurno(id);
            System.out.println(turno);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listar() {
        List<Turno> turnos = servicioTurno.listarTurnos();
        if (turnos.isEmpty()) {
            System.out.println("No hay turnos registrados.");
            return;
        }
        turnos.forEach(t -> System.out.println(
                "ID: " + t.getId() + " | " + t.getPaciente().getNombreCompleto() +
                        " | " + t.getOdontologo().getNombreCompleto() +
                        " | " + t.getFecha() + " " + t.getHora() +
                        " | " + t.getEstado()
        ));
    }

    private void listarPorPaciente() {
        Long id = LectorConsola.leerLong("ID del paciente: ");
        try {
            Paciente paciente = servicioPaciente.buscarPaciente(id);
            List<Turno> turnos = servicioTurno.listarPorPaciente(paciente);
            if (turnos.isEmpty()) {
                System.out.println("El paciente no tiene turnos.");
                return;
            }
            turnos.forEach(t -> System.out.println(
                    "ID: " + t.getId() + " | " + t.getFecha() + " " + t.getHora() + " | " + t.getEstado()
            ));
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarPorOdontologo() {
        Long id = LectorConsola.leerLong("ID del odontólogo: ");
        try {
            Odontologo odontologo = servicioOdontologo.buscarOdontologo(id);
            List<Turno> turnos = servicioTurno.listarPorOdontologo(odontologo);
            if (turnos.isEmpty()) {
                System.out.println("El odontólogo no tiene turnos.");
                return;
            }
            turnos.forEach(t -> System.out.println(
                    "ID: " + t.getId() + " | " + t.getPaciente().getNombreCompleto() +
                            " | " + t.getFecha() + " " + t.getHora() + " | " + t.getEstado()
            ));
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listarPorFecha() {
        LocalDate fecha = LectorConsola.leerFecha("Fecha");
        List<Turno> turnos = servicioTurno.listarPorFecha(fecha);
        if (turnos.isEmpty()) {
            System.out.println("No hay turnos para esa fecha.");
            return;
        }
        turnos.forEach(t -> System.out.println(
                "ID: " + t.getId() + " | " + t.getPaciente().getNombreCompleto() +
                        " | " + t.getOdontologo().getNombreCompleto() +
                        " | " + t.getHora() + " | " + t.getEstado()
        ));
    }

    private void confirmar() {
        Long id = LectorConsola.leerLong("ID del turno: ");
        try {
            servicioTurno.confirmarTurno(id);
            System.out.println("Turno confirmado.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cancelar() {
        Long id = LectorConsola.leerLong("ID del turno: ");
        String confirmar = LectorConsola.leerString("¿Confirmar cancelación? (s/n): ");
        if (!confirmar.equalsIgnoreCase("s")) {
            System.out.println("Operación cancelada.");
            return;
        }
        try {
            servicioTurno.cancelarTurno(id);
            System.out.println("Turno cancelado.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void completar() {
        Long id = LectorConsola.leerLong("ID del turno: ");
        try {
            servicioTurno.completarTurno(id);
            System.out.println("Turno completado.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void modificar() {
        Long id = LectorConsola.leerLong("ID del turno a modificar: ");
        LocalDate nuevaFecha = LectorConsola.leerFecha("Nueva fecha");
        LocalTime nuevaHora = LectorConsola.leerHora("Nueva hora");
        try {
            servicioTurno.modificarTurno(id, nuevaFecha, nuevaHora);
            System.out.println("Turno modificado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        Long id = LectorConsola.leerLong("ID del turno: ");
        String confirmar = LectorConsola.leerString("¿Confirmar eliminación? (s/n): ");
        if (!confirmar.equalsIgnoreCase("s")) {
            System.out.println("Operación cancelada.");
            return;
        }
        try {
            servicioTurno.eliminarTurno(id);
            System.out.println("Turno eliminado.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}