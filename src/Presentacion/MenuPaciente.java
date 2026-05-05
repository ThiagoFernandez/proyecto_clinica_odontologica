package Presentacion;

import Modelo.Domicilio;
import Modelo.Paciente;
import Modelo.Turno;
import Servicio.ServicioPaciente;
import java.time.LocalDate;
import java.util.List;

public class MenuPaciente {
    private final ServicioPaciente servicioPaciente;

    public MenuPaciente(ServicioPaciente servicioPaciente) {
        this.servicioPaciente = servicioPaciente;
    }

    public void mostrar() {
        while (true) {
            System.out.println("\n--- PACIENTES ---");
            System.out.println("1. Registrar paciente");
            System.out.println("2. Buscar paciente por ID");
            System.out.println("3. Buscar paciente por DNI");
            System.out.println("4. Listar pacientes");
            System.out.println("5. Listar pacientes por localidad");
            System.out.println("6. Listar pacientes alfabeticamente");
            System.out.println("7. Ver turnos del paciente");
            System.out.println("8. Modificar paciente");
            System.out.println("9. Eliminar paciente");
            System.out.println("0. Volver");

            int opcion = LectorConsola.leerInt("Opcion: ");
            switch (opcion) {
                case 1 -> registrar();
                case 2 -> buscar();
                case 3 -> buscarPorDni();
                case 4 -> listar();
                case 5 -> listarPorLocalidad();
                case 6 -> listarAlfabeticamente();
                case 7 -> verTurnos();
                case 8 -> modificar();
                case 9 -> eliminar();
                case 0 -> { return; }
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private void registrar() {
        String nombre = LectorConsola.leerString("Nombre: ");
        String apellido = LectorConsola.leerString("Apellido: ");
        String dni = LectorConsola.leerString("DNI: ");
        String email = LectorConsola.leerString("Email: ");
        String calle = LectorConsola.leerString("Calle: ");
        int numero = LectorConsola.leerInt("Numero: ");
        String localidad = LectorConsola.leerString("Localidad: ");
        String provincia = LectorConsola.leerString("Provincia: ");

        Domicilio domicilio = new Domicilio(calle, numero, localidad, provincia);
        Paciente paciente = new Paciente(nombre, apellido, dni, email, LocalDate.now(), domicilio);

        try {
            servicioPaciente.registrarPaciente(paciente);
            System.out.println("Paciente registrado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscar() {
        Long id = LectorConsola.leerLong("ID: ");
        try {
            Paciente paciente = servicioPaciente.buscarPaciente(id);
            System.out.println(paciente.toString());
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listar() {
        List<Paciente> pacientes = servicioPaciente.listarPacientes();
        System.out.println("Total pacientes: " + pacientes.size());
        for (Paciente paciente : pacientes) {
            System.out.println("Paciente id: " + paciente.getId() + "\nNombre y apellido: " + paciente.getNombreCompleto());
        }
    }

    private void listarPorLocalidad() {
        String localidad = LectorConsola.leerString("Localidad: ");
        List<Paciente> pacientes = servicioPaciente.listarPorLocalidad(localidad);
        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes en esa localidad.");
        } else {
            pacientes.forEach(p -> System.out.println("ID: " + p.getId() + " --- DNI: " + p.getDni() + " --- Nombre completo: " + p.getNombreCompleto()));
        }
    }

    private void listarAlfabeticamente(){
        List<Paciente> pacientes = servicioPaciente.listarOrdenadosPorApellido();
        if (pacientes.isEmpty()){
            System.out.println("No hay pacientes registrados");
        } else{
            pacientes.forEach(p -> System.out.println("ID: " + p.getId() + " --- DNI: " + p.getDni() + " --- Nombre completo: " + p.getNombreCompleto()));
        }
    }

    private void verTurnos() {
        Long id = LectorConsola.leerLong("ID del paciente: ");
        try {
            Paciente paciente = servicioPaciente.buscarPaciente(id);
            List<Turno> turnos = paciente.getTurnos();
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

    private void modificar() {
        Long id = LectorConsola.leerLong("ID del paciente a modificar: ");
        String nombre = LectorConsola.leerString("Nombre nuevo: ");
        String apellido = LectorConsola.leerString("Apellido nuevo: ");
        String dni = LectorConsola.leerString("DNI nuevo: ");
        String email = LectorConsola.leerString("Email nuevo: ");
        String calle = LectorConsola.leerString("Calle nueva: ");
        int numero = LectorConsola.leerInt("Numero nuevo: ");
        String localidad = LectorConsola.leerString("Localidad nueva: ");
        String provincia = LectorConsola.leerString("Provincia nueva: ");

        Domicilio domicilio = new Domicilio(calle, numero, localidad, provincia);
        Paciente paciente = new Paciente(nombre, apellido, dni, email, LocalDate.now(), domicilio);
        paciente.setId(id);

        try {
            servicioPaciente.modificarPaciente(paciente);
            System.out.println("Paciente modificado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarPorDni() {
        String dni = LectorConsola.leerString("DNI: ");
        try {
            Paciente paciente = servicioPaciente.buscarPorDni(dni);
            System.out.println(paciente);
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        Long id = LectorConsola.leerLong("ID: ");
        try {
            String confirmar = LectorConsola.leerString("Confirmar eliminacion? (s/n): ");
            if (!confirmar.equalsIgnoreCase("s")) {
                System.out.println("Operacion cancelada.");
                return;
            }
            servicioPaciente.eliminarPaciente(id);
            System.out.println("Paciente eliminado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
