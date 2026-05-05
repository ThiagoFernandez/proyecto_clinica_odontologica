package Presentacion;

import Modelo.*;
import Servicio.ServicioOdontologo;

import java.util.List;

public class MenuOdontologo {
    private final ServicioOdontologo servicioOdontologo;

    public MenuOdontologo(ServicioOdontologo servicioOdontologo){
        this.servicioOdontologo = servicioOdontologo;
    }

    public void mostrar(){
        while (true) {
            System.out.println("\n--- ODONTOLOGOS ---");
            System.out.println("1. Registrar odontologo");
            System.out.println("2. Buscar odontologo por ID");
            System.out.println("3. Listar odontologos");
            System.out.println("4. Listar por especialidad");
            System.out.println("5. Ver turnos del odontologo");
            System.out.println("6. Modificar odontologo");
            System.out.println("7. Eliminar odontologo");
            System.out.println("0. Volver");

            int opcion = LectorConsola.leerInt("Opcion: ");
            switch (opcion){
                case 1 -> registrar();
                case 2 -> buscar();
                case 3 -> listar();
                case 4 -> listarPorEspecialidad();
                case 5 -> verTurnos();
                case 6 -> modificar();
                case 7 -> eliminar();
                case 0 -> {
                    System.out.println("Volviendo al menu principal...");
                    return;
                }
                default -> System.out.println("Opcion invalida.");
            }
        }
    }

    private void registrar(){
        String nombre = LectorConsola.leerString("Nombre: ");
        String apellido = LectorConsola.leerString("Apellido: ");
        String matricula = LectorConsola.leerString("Matricula: ");

        System.out.println("Especialidad:");
        System.out.println("1. Odontologia General (30 min)");
        System.out.println("2. Ortodoncia (45 min)");
        System.out.println("3. Endodoncia (60 min)");

        int opcion = LectorConsola.leerInt("Opcion: ");

        Odontologo odontologo = switch (opcion){
            case 1 -> new OdontologoGeneral(nombre, apellido, matricula);
            case 2 -> new Ortodoncista(nombre, apellido, matricula);
            case 3 -> new Endodoncista(nombre, apellido, matricula);
            default -> {
                System.out.println("Especialidad invalida. Se registra como general.");
                yield new OdontologoGeneral(nombre, apellido, matricula);
            }
        };
        try {
            servicioOdontologo.registrarOdontologo(odontologo);
            System.out.println("Odontologo registrado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscar(){
        Long id = LectorConsola.leerLong("ID: ");
        try{
            Odontologo odontologo = servicioOdontologo.buscarOdontologo(id);
            System.out.println(odontologo);
        } catch (RuntimeException e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listar(){
        List<Odontologo> odontologos = servicioOdontologo.listarOdontologos();
        System.out.println("Total odontologos: " + odontologos.size());
        for(Odontologo odontologo : odontologos){
            System.out.println("ID: " + odontologo.getId() + " | " + odontologo.getNombreCompleto());
        }
    }

    private void listarPorEspecialidad() {
        System.out.println("1. Odontologia General");
        System.out.println("2. Ortodoncia");
        System.out.println("3. Endodoncia");
        int op = LectorConsola.leerInt("Opcion: ");

        Class<?> clase = switch (op) {
            case 1 -> OdontologoGeneral.class;
            case 2 -> Ortodoncista.class;
            case 3 -> Endodoncista.class;
            default -> { System.out.println("Opcion invalida."); yield null; }
        };

        if (clase == null) return;

        List<Odontologo> resultado = servicioOdontologo.listarPorEspecialidad(clase);
        if (resultado.isEmpty()) {
            System.out.println("No hay odontologos de esa especialidad.");
            return;
        }
        for (Odontologo o : resultado) {
            System.out.println("ID: " + o.getId() + " | " + o.getNombreCompleto() + " | Matricula: " + o.getMatricula());
        }
    }

    private void verTurnos() {
        Long id = LectorConsola.leerLong("ID del odontologo: ");
        try {
            Odontologo odontologo = servicioOdontologo.buscarOdontologo(id);
            List<Turno> turnos = odontologo.getTurnos();
            if (turnos.isEmpty()) {
                System.out.println("El odontologo no tiene turnos.");
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

    private void modificar() {
        Long id = LectorConsola.leerLong("ID del odontologo a modificar: ");
        String nombre = LectorConsola.leerString("Nombre nuevo: ");
        String apellido = LectorConsola.leerString("Apellido nuevo: ");
        String matricula = LectorConsola.leerString("Matricula nueva: ");

        System.out.println("Especialidad:");
        System.out.println("1. Odontologia General (30 min)");
        System.out.println("2. Ortodoncia (45 min)");
        System.out.println("3. Endodoncia (60 min)");
        int especialidad = LectorConsola.leerInt("Opcion: ");

        Odontologo odontologo = switch (especialidad) {
            case 1 -> new OdontologoGeneral(nombre, apellido, matricula);
            case 2 -> new Ortodoncista(nombre, apellido, matricula);
            case 3 -> new Endodoncista(nombre, apellido, matricula);
            default -> {
                System.out.println("Especialidad invalida. Se mantiene como General.");
                yield new OdontologoGeneral(nombre, apellido, matricula);
            }
        };
        odontologo.setId(id);

        try {
            servicioOdontologo.modificarOdontologo(odontologo);
            System.out.println("Odontologo modificado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminar() {
        Long id = LectorConsola.leerLong("ID: ");
        String confirmar = LectorConsola.leerString("Confirmar eliminacion? (s/n): ").strip();
        if (!confirmar.equalsIgnoreCase("s")) {
            System.out.println("Operacion cancelada.");
            return;
        }
        try {
            servicioOdontologo.eliminarOdontologo(id);
            System.out.println("Odontologo eliminado correctamente.");
        } catch (RuntimeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
