import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        System.out.println("Bienvenido a la Clinica Odontologica 'SONRISA FELIZ'");

        // usando constructores con parametros para cargar datos de prueba
        System.out.println("Ejemplo con constructores con parametros:");
        Modelo.Domicilio domicilio1 = new Modelo.Domicilio(
                "Calle Falsa", 123, "Flores", "CABA"
        );

        Modelo.Paciente paciente1 = new Modelo.Paciente(
                "Thiago", "Procacci", "46871211",
                "thiago@email.com",
                LocalDate.now(),
                domicilio1
        );

        Modelo.Odontologo odontologo1 = new Modelo.Odontologo(
                "Agustin", "Fernandez", "12345"
        );

        Modelo.Turno turno1 = new Modelo.Turno(
                paciente1,
                odontologo1,
                LocalDate.now(),
                java.time.LocalTime.now(),
                Modelo.EstadoTurno.PENDIENTE
        );

        System.out.println(paciente1);
        System.out.println(odontologo1);
        System.out.println(turno1);

        // usando constructor vacio + setters
        System.out.println("Ejemplo con constructor vacio");
        Modelo.Domicilio domicilio2 = new Modelo.Domicilio();
        domicilio2.setCalle("Av Rivadavia");
        domicilio2.setNumero(1234);
        domicilio2.setLocalidad("Flores");
        domicilio2.setProvincia("CABA");

        Modelo.Paciente paciente2 = new Modelo.Paciente();
        paciente2.setNombre("Pepe");
        paciente2.setApellido("Grillo");
        paciente2.setDni("32323232");
        paciente2.setEmail("pepegrillo@email.com");
        paciente2.setDomicilio(domicilio2);

        Modelo.Odontologo odontologo2 = new Modelo.Odontologo();
        odontologo2.setNombre("Facundo");
        odontologo2.setApellido("Cravero");
        odontologo2.setMatricula("67890");

        Modelo.Turno turno2 = new Modelo.Turno();
        turno2.setPaciente(paciente2);
        turno2.setOdontologo(odontologo2);
        turno2.setLocalDate(LocalDate.now());
        turno2.setLocalTime(java.time.LocalTime.now());
        turno2.setEstado(Modelo.EstadoTurno.PENDIENTE);

        System.out.println(paciente2);
        System.out.println(odontologo2);
        System.out.println(turno2);

        // prueba de metodos de paciente
        System.out.println("Prueba de metodos de paciente:");
        System.out.println("getId: " + paciente1.getId());
        System.out.println("getNombre: " + paciente1.getNombre());
        System.out.println("getApellido: " + paciente1.getApellido());
        System.out.println("getNombreCompleto: " + paciente1.getNombreCompleto());
        System.out.println("getDni: " + paciente1.getDni());
        System.out.println("getEmail: " + paciente1.getEmail());
        System.out.println("getFechaIngreso: " + paciente1.getFechaIngreso());
        System.out.println("getDomicilio: " + paciente1.getDomicilio());
        System.out.println("hashCode: " + paciente1.hashCode());
        System.out.println("equals con paciente2: " + paciente1.equals(paciente2));
        System.out.println("equals con paciente1: " + paciente1.equals(paciente1));
        System.out.println("toString: " + paciente1.toString());

        // prueba de metodos de domicilio
        System.out.println("Prueba de metodos de domicilio:");
        System.out.println("getId: " + domicilio1.getId());
        System.out.println("getCalle: " + domicilio1.getCalle());
        System.out.println("getNumero: " + domicilio1.getNumero());
        System.out.println("getLocalidad: " + domicilio1.getLocalidad());
        System.out.println("getProvincia: " + domicilio1.getProvincia());
        System.out.println("toString: " + domicilio1.toString());

        // prueba de metodos de odontologo
        System.out.println("Prueba de metodos de odontologo:");
        System.out.println("getId: " + odontologo1.getId());
        System.out.println("getNombre: " + odontologo1.getNombre());
        System.out.println("getApellido: " + odontologo1.getApellido());
        System.out.println("getNombreCompleto: " + odontologo1.getNombreCompleto());
        System.out.println("getMatricula: " + odontologo1.getMatricula());
        System.out.println("hashCode: " + odontologo1.hashCode());
        System.out.println("equals con odontologo2: " + odontologo1.equals(odontologo2));
        System.out.println("equals con odontologo1: " + odontologo1.equals(odontologo1));
        System.out.println("toString: " + odontologo1.toString());

        // prueba de metodos de turno
        System.out.println("Prueba de metodos de turno:");
        System.out.println("getPaciente: " + turno1.getPaciente());
        System.out.println("getOdontologo: " + turno1.getOdontologo());
        System.out.println("getFecha: " + turno1.getFecha());
        System.out.println("getHora: " + turno1.getHora());
        System.out.println("getEstado: " + turno1.getEstado());
        System.out.println("estaDisponible: " + turno1.estaDisponible());
        System.out.println("esFuturo: " + turno1.esFuturo());
        System.out.println("toString: " + turno1.toString());
    }
}