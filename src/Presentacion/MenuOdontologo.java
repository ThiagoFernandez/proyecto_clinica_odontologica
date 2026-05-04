package Presentacion;

import Modelo.Endodoncista;
import Modelo.Odontologo;
import Modelo.OdontologoGeneral;
import Modelo.Ortodoncista;
import Servicio.ServicioOdontologo;

import java.util.List;

public class MenuOdontologo {
    private ServicioOdontologo servicioOdontologo;

    public MenuOdontologo(ServicioOdontologo servicioOdontologo){
        this.servicioOdontologo = servicioOdontologo;
    }

    public void mostrar(){
        while (true) {
            System.out.println("\n--- ODONTÓLOGOS ---");
            System.out.println("1. Registrar odontólogo");
            System.out.println("2. Buscar odontólogo por ID");
            System.out.println("3. Listar odontólogos");
            System.out.println("4. Listar por especialidad");
            System.out.println("5. Modificar odontólogo");
            System.out.println("6. Eliminar odontólogo");
            System.out.println("0. Volver");

            int opcion = LectorConsola.leerInt("Opcion: ");
            switch (opcion){
                case 1 -> registrar();
                case 2 -> buscar();
                case 3 -> listar();
                case 4 -> listarPorEspecialidad();
                case 5 -> modificar();
                case 6 -> eliminar();
                case 0 -> {
                    System.out.println("Volviendo al menu principal...");
                    return;
                }
                default -> System.out.println("Opcion invalida");
            }
        }
    }

    private void registrar(){
        String nombre = LectorConsola.leerString("Nombre: ");
        String apellido = LectorConsola.leerString("Apellido: ");
        String matricula = LectorConsola.leerString("Matricula: ");

        System.out.println("Especialidad: ");
        System.out.println("1. Odontologia General (30 min)");
        System.out.println("2. Ortodoncia (45 min)");
        System.out.println("3. Endodoncia (60 min)");

        int opcion = LectorConsola.leerInt("Opcion: ");

        Odontologo odontologo = switch (opcion){
            case 1 -> new OdontologoGeneral(nombre, apellido, matricula);
            case 2 -> new Ortodoncista(nombre, apellido, matricula);
            case 3 -> new Endodoncista(nombre, apellido, matricula);
            default -> {
                System.out.println("Especialidad invalida. Se registra como general");
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
        Long id = LectorConsola.leerLong("Id: ");
        try{
            Odontologo odontologo = servicioOdontologo.buscarOdontologo(id);
            System.out.println(odontologo);
        } catch (RuntimeException e){
            System.out.println("Error: "+e.getMessage());
        }
    }

   private void listar(){
        List<Odontologo> odontologos = servicioOdontologo.listarOdontologos();
        System.out.println("Total odontologos: "+odontologos.size());

        for(Odontologo odontologo : odontologos){
            System.out.println("Odontologo id: "+odontologo.getId() +"\nNombre y apellido: "+ odontologo.getNombreCompleto());
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
            default -> { System.out.println("Invalido."); yield null; }
        };

        if (clase == null) return;

        List<Odontologo> resultado = servicioOdontologo.listarPorEspecialidad(clase);
        for (Odontologo o : resultado) {
            System.out.println("ID: " + o.getId() + " | " + o.getNombreCompleto());
        }
    }

    private void modificar() {
        Long id = LectorConsola.leerLong("ID del odontologo a modificar: ");
        String nombre = LectorConsola.leerString("Nombre nuevo: ");
        String apellido = LectorConsola.leerString("Apellido nuevo: ");
        String matricula = LectorConsola.leerString("Matrícula nueva: ");

        // para modificar necesitamos saber la especialidad actual o elegir una nueva
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
