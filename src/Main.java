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
import Persistencia.Persistencia;

public class Main {

    public static void main(String[] args) {
        // repositorios
        RepositorioPaciente repoPaciente = new RepositorioPaciente();
        RepositorioOdontologo repoOdontologo = new RepositorioOdontologo();
        RepositorioTurno repoTurno = new RepositorioTurno();

        // CARGAR DATOS AL INICIAR
        System.out.println("Cargando datos...");
        Persistencia.cargarPacientes().forEach(repoPaciente::guardar);
        Persistencia.cargarOdontologos().forEach(repoOdontologo::guardar);
        Persistencia.cargarTurnos().forEach(repoTurno::guardar);
        System.out.println("Datos cargados.");

        // servicios
        ServicioPaciente servicioPaciente = new ServicioPaciente(repoPaciente, repoTurno);
        ServicioOdontologo servicioOdontologo = new ServicioOdontologo(repoOdontologo, repoTurno);
        ServicioTurno servicioTurno = new ServicioTurno(repoTurno, repoPaciente, repoOdontologo);

        // menus
        MenuPaciente menuPaciente = new MenuPaciente(servicioPaciente);
        MenuOdontologo menuOdontologo = new MenuOdontologo(servicioOdontologo);
        MenuTurno menuTurno = new MenuTurno(servicioTurno, servicioPaciente, servicioOdontologo);

        // inicio
        new MenuPrincipal(menuPaciente, menuOdontologo, menuTurno).iniciar();

        // GUARDAR DATOS AL CERRAR
        System.out.println("Guardando datos...");
        Persistencia.guardarPacientes(repoPaciente.listar());
        Persistencia.guardarOdontologos(repoOdontologo.listar());
        Persistencia.guardarTurnos(repoTurno.listar());
        System.out.println("Datos guardados.");
    }
}