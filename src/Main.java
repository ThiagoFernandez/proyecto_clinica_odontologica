import Presentacion.MenuOdontologo;
import Presentacion.MenuPaciente;
import Presentacion.MenuPrincipal;
import Presentacion.MenuTurno;
import Repositorio.RepositorioOdontologo;
import Repositorio.RepositorioPaciente;
import Repositorio.RepositorioTurno;
import Servicio.ServicioOdontologo;
import Servicio.ServicioPaciente;
import Servicio.ServicioTurno;

public class Main {

    public static void main(String[] args) {
        // repositorios
        RepositorioPaciente repoPaciente = new RepositorioPaciente();
        RepositorioOdontologo repoOdontologo = new RepositorioOdontologo();
        RepositorioTurno repoTurno = new RepositorioTurno();

        // servicios
        ServicioPaciente servicioPaciente = new ServicioPaciente(repoPaciente, repoTurno);
        ServicioOdontologo servicioOdontologo = new ServicioOdontologo(repoOdontologo, repoTurno);
        ServicioTurno servicioTurno = new ServicioTurno(repoTurno, repoPaciente, repoOdontologo);

        // menús
        MenuPaciente menuPaciente = new MenuPaciente(servicioPaciente);
        MenuOdontologo menuOdontologo = new MenuOdontologo(servicioOdontologo);
        MenuTurno menuTurno = new MenuTurno(servicioTurno, servicioPaciente, servicioOdontologo);

        // arrancar
        new MenuPrincipal(menuPaciente, menuOdontologo, menuTurno).iniciar();
    }
}