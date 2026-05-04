package Presentacion;

import Modelo.Domicilio;
import Modelo.Paciente;
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
            System.out.println("3. Listar pacientes");
            System.out.println("4. Modificar paciente");
            System.out.println("5. Eliminar paciente");
            System.out.println("0. Volver");

            int opcion = LectorConsola.leerInt("Opción: ");
            switch (opcion) {
                case 1 -> registrar();
                case 2 -> buscar();
                case 3 -> listar();
                case 4 -> modificar();
                case 5 -> eliminar();
                case 0 -> { return; }
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private void registrar() {
        String nombre = LectorConsola.leerString("Nombre: ");
        String apellido = LectorConsola.leerString("Apellido: ");
        String dni = LectorConsola.leerString("DNI: ");
        String email = LectorConsola.leerString("Email: ");
        String calle = LectorConsola.leerString("Calle: ");
        int numero = LectorConsola.leerInt("Número: ");
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
            System.out.println("Error: "+e.getMessage());
        }
    }

    private void listar() {
        List<Paciente> pacientes = servicioPaciente.listarPacientes();
        System.out.println("Total pacientes: "+pacientes.size());
        for (Paciente paciente : pacientes) {
            System.out.println("Paciente id: " + paciente.getId() + "\nNombre y apellido: " + paciente.getNombreCompleto());
        }
    }
    private void modificar() {
        Long id = LectorConsola.leerLong("ID del paciente a modificar: ");
        String nombre = LectorConsola.leerString("Nombre nuevo: ");
        String apellido = LectorConsola.leerString("Apellido nuevo: ");
        String dni = LectorConsola.leerString("DNI nuevo: ");
        String email = LectorConsola.leerString("Email nuevo: ");
        String calle = LectorConsola.leerString("Calle nueva: ");
        int numero = LectorConsola.leerInt("Número nuevo: ");
        String localidad = LectorConsola.leerString("Localidad nueva: ");
        String provincia = LectorConsola.leerString("Provincia nueva: ");

        Domicilio domicilio = new Domicilio(calle, numero, localidad, provincia);
        Paciente paciente = new Paciente(nombre, apellido, dni, email, LocalDate.now(), domicilio);
        paciente.setId(id); // ← apunta al paciente existente

        try {
            servicioPaciente.modificarPaciente(paciente);
            System.out.println("Paciente modificado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void eliminar() {  // esto podria tener una confirmacion para no borrarlo sin querer
        Long id = LectorConsola.leerLong("ID: ");
        try {
            String confirmar = LectorConsola.leerString("¿Confirmar eliminación? (s/n): ");
            if (!confirmar.equalsIgnoreCase("s")) {
                System.out.println("Operación cancelada.");
                return;
            }
            servicioPaciente.eliminarPaciente(id);
            System.out.println("Paciente eliminado correctamente");
        } catch (RuntimeException e){
            System.out.println("Error: "+e.getMessage());
        }
    }
}