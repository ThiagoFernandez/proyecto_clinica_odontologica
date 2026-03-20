public class Main {
    public static void main(String[] args) {
        Modelo.Paciente paciente1 = new Modelo.Paciente(01L, "Thiago", "Procacci", "46871211", "thiagofprocacci@gmail.com", java.time.LocalDate.now(), new Modelo.Domicilio(01L, "Calle Falsa", "123", "Localidad falsa", "Provincia falsa"));
        Modelo.Odontologo odontologo1 = new Modelo.Odontologo(01L, "Agustin", "Fernandez", "12345");
        Modelo.Turno turno1 = new Modelo.Turno(01L, paciente1, odontologo1, java.time.LocalDate.now(), java.time.LocalTime.now(), Modelo.EstadoTurno.PENDIENTE);
        System.out.println("--- Los datos del paciente han sido cargados exitosamente:");
        System.out.println(paciente1);
        System.out.println("--- Los datos del odontologo han sido cargados exitosamente:");
        System.out.println(odontologo1);
        System.out.println("--- Los datos del turno han sido cargados exitosamente:");
        System.out.println(turno1);
        System.out.println("Nombre completo del paciente: " + paciente1.getNombreCompleto());

    }


}
