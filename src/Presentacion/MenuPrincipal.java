package Presentacion;

public class MenuPrincipal {
    private MenuPaciente menuPaciente;
    private MenuOdontologo menuOdontologo;
    private MenuTurno menuTurno;

    public MenuPrincipal(MenuPaciente menuPaciente, MenuOdontologo menuOdontologo, MenuTurno menuTurno){
        this.menuPaciente = menuPaciente;
        this.menuOdontologo = menuOdontologo;
        this.menuTurno = menuTurno;
    }

    public void iniciar(){
        while (true) {
            System.out.println("\n=== CLÍNICA ODONTOLÓGICA SONRISA FELIZ ===");
            System.out.println("1. Gestión de Pacientes");
            System.out.println("2. Gestión de Odontólogos");
            System.out.println("3. Gestión de Turnos");
            System.out.println("0. Salir");

            int opcion = LectorConsola.leerInt("Opcion: ");

            switch (opcion){
                case 1 -> menuPaciente.mostrar();
                case 2 -> menuOdontologo.mostrar();
                case 3 -> menuTurno.mostrar();
                case 0 -> {
                    System.out.println("Volviendo al menu principal...");
                    return;
                }
                default -> System.out.println("Opcion invalida");
            }
        }
    }
}
