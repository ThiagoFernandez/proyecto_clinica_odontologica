import java.util.Scanner;
import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Bienvenido a la Clinica Odontologica 'SONRISA FELIZ'");

        Modelo.Paciente paciente1 = new Modelo.Paciente("Thiago", "Procacci", "46871211", "thiagofprocacci@gmail.com", LocalDate.now(), new Modelo.Domicilio("Calle Falsa", "123", "Localidad falsa", "Provincia falsa"));
        Modelo.Odontologo odontologo1 = new Modelo.Odontologo("Agustin", "Fernandez", "12345");
        Modelo.Turno turno1 = new Modelo.Turno(paciente1, odontologo1, LocalDate.now(), java.time.LocalTime.now(), Modelo.EstadoTurno.PENDIENTE);

        System.out.println("--- Los datos del paciente han sido cargados exitosamente:");
        System.out.println(paciente1);

        System.out.println("--- Los datos del odontologo han sido cargados exitosamente:");
        System.out.println(odontologo1);

        System.out.println("--- Los datos del turno han sido cargados exitosamente:");
        System.out.println(turno1);

        int opcion;

        while (true) {
            System.out.println("\n¿Desea crear un nuevo paciente u odontologo? (1 para paciente, 2 para odontologo, 0 para salir): ");
            opcion = sc.nextInt();
            sc.nextLine();

            if (opcion == 0) {
                System.out.println("Cerrando el programa...");
                break;
            } else if (opcion == 1) {
                Modelo.Paciente p = crearPaciente(sc);
                System.out.println(p);
            } else if (opcion == 2) {
                Modelo.Odontologo o = crearOdontologo(sc);
                System.out.println(o.toString());
            } else {
                System.out.println("Opción no válida. Por favor, ingrese 1, 2 o 0.");
            }
        }
        sc.close();
    }

    public static Modelo.Paciente crearPaciente(Scanner sc) {
        System.out.println("---- CREAR PACIENTE ----");

        System.out.print("DNI: ");
        String dniPaciente = sc.nextLine();
        if (!dniPaciente.matches("\\d{8}")) {
            System.out.println("DNI inválido. Debe tener exactamente 8 caracteres.");
            return null;
        }

        System.out.print("Nombre: ");
        String nombrePaciente = sc.nextLine();

        System.out.print("Apellido: ");
        String apellidoPaciente = sc.nextLine();

        System.out.print("Email: ");
        String emailPaciente = sc.nextLine();

        System.out.print("Fecha ingreso (yyyy-MM-dd): ");
        String fechaIngresoPaciente = sc.nextLine();

        System.out.println("---- DOMICILIO ----");

        return new Modelo.Paciente(
                nombrePaciente,
                apellidoPaciente,
                dniPaciente,
                emailPaciente,
                LocalDate.parse(fechaIngresoPaciente),
                crearDomicilio(sc)
        );
    }

    public static Modelo.Domicilio crearDomicilio(Scanner sc){
        System.out.print("Calle: ");
        String callePaciente = sc.nextLine();

        System.out.print("Número: ");
        String numeroPaciente = sc.nextLine();

        System.out.print("Localidad: ");
        String localidadPaciente = sc.nextLine();

        System.out.print("Provincia: ");
        String provinciaPaciente = sc.nextLine();

        return new Modelo.Domicilio(callePaciente, numeroPaciente, localidadPaciente, provinciaPaciente);
    }

    public static Modelo.Odontologo crearOdontologo(Scanner sc ){
        System.out.println("---- CREAR ODONTOLOGO ----");

        System.out.print("Nombre: ");
        String nombreOdontologo = sc.nextLine();

        System.out.print("Apellido: ");
        String apellidoOdontologo = sc.nextLine();

        System.out.println("Matricula: ");
        String matriculaOdontologo = sc.nextLine();

        return new Modelo.Odontologo(
            nombreOdontologo,
            apellidoOdontologo,
            matriculaOdontologo
        );
    }
}