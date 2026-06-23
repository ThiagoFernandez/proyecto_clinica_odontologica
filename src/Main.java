import Modelo.Domicilio;
import Modelo.Odontologo;
import Modelo.Paciente;
import Modelo.Turno;
import Persistencia.Persistencia;
import Repositorio.RepositorioOdontologo;
import Repositorio.RepositorioPaciente;
import Repositorio.RepositorioTurno;
import Servicio.ServicioOdontologo;
import Servicio.ServicioPaciente;
import Servicio.ServicioTurno;
import Vista.VentanaPrincipal;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // crear repositorios
        RepositorioPaciente repoPaciente = new RepositorioPaciente();
        RepositorioOdontologo repoOdontologo = new RepositorioOdontologo();
        RepositorioTurno repoTurno = new RepositorioTurno();

        // cargar datos persistidos
        System.out.println("Cargando datos...");
        Persistencia.cargarPacientes().forEach(repoPaciente::guardar);
        Persistencia.cargarOdontologos().forEach(repoOdontologo::guardar);
        Persistencia.cargarTurnos().forEach(repoTurno::guardar);

        // actualizar contadores para evitar colision de IDs -- fixxed
        long maxPaciente = repoPaciente.listar().stream()
                .mapToLong(Paciente::getId).max().orElse(0L);
        Paciente.actualizarContadorId(maxPaciente + 1);

        long maxOdontologo = repoOdontologo.listar().stream()
                .mapToLong(Odontologo::getId).max().orElse(0L);
        Odontologo.actualizarContadorId(maxOdontologo + 1);

        long maxTurno = repoTurno.listar().stream()
                .mapToLong(Turno::getId).max().orElse(0L);
        Turno.actualizarContadorId(maxTurno + 1);

        long maxDomicilio = repoPaciente.listar().stream()
                .map(Paciente::getDomicilio)
                .filter(d -> d != null)
                .mapToLong(Domicilio::getId)
                .max().orElse(0L);
        Domicilio.actualizarContadorId(maxDomicilio + 1);

        System.out.println("Datos cargados.");

        // crear servicios
        ServicioPaciente servicioPaciente = new ServicioPaciente(repoPaciente, repoTurno);
        ServicioOdontologo servicioOdontologo = new ServicioOdontologo(repoOdontologo, repoTurno);
        ServicioTurno servicioTurno = new ServicioTurno(repoTurno, repoPaciente, repoOdontologo);

        // lanzar GUI en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal(
                    servicioPaciente, servicioOdontologo, servicioTurno,
                    repoPaciente, repoOdontologo, repoTurno);
            ventana.setVisible(true);
        });
    }
}