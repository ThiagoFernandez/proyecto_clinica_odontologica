package Presentacion;

public class MenuPrincipal {
    private final MenuPaciente menuPaciente;
    private final MenuOdontologo menuOdontologo;
    private final MenuTurno menuTurno;

    public MenuPrincipal(MenuPaciente menuPaciente, MenuOdontologo menuOdontologo, MenuTurno menuTurno){
        this.menuPaciente = menuPaciente;
        this.menuOdontologo = menuOdontologo;
        this.menuTurno = menuTurno;
    }

    public void iniciar(){
        while (true) {
            System.out.println("\n=== CLINICA ODONTOLOGICA SONRISA FELIZ ===");
            System.out.println("1. Gestion de Pacientes");
            System.out.println("2. Gestion de Odontologos");
            System.out.println("3. Gestion de Turnos");
            System.out.println("0. Salir");

            int opcion = LectorConsola.leerInt("Opcion: ");

            switch (opcion){
                case 1 -> menuPaciente.mostrar();
                case 2 -> menuOdontologo.mostrar();
                case 3 -> menuTurno.mostrar();
                case 0 -> {
                    System.out.println("Hasta luego.");
                    return;
                }
                default -> System.out.println("Opcion invalida");
            }
        }
    }
}
