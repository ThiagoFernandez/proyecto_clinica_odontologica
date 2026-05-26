package Persistencia;

import Modelo.Odontologo;
import Modelo.Paciente;
import Modelo.Turno;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Persistencia {
    private static final String ARCHIVO_PACIENTES = "pacientes.dat";
    private static final String ARCHIVO_ODONTOLOGOS = "odontologos.dat";
    private static final String ARCHIVO_TURNOS = "turnos.dat";

    public static void guardarPacientes(List<Paciente> pacientes) {
        guardar(pacientes, ARCHIVO_PACIENTES);
    }

    public static void guardarOdontologos(List<Odontologo> odontologos) {
        guardar(odontologos, ARCHIVO_ODONTOLOGOS);
    }

    public static void guardarTurnos(List<Turno> turnos) {
        guardar(turnos, ARCHIVO_TURNOS);
    }

    @SuppressWarnings("unchecked")
    public static List<Paciente> cargarPacientes() {
        return (List<Paciente>) cargar(ARCHIVO_PACIENTES);
    }

    @SuppressWarnings("unchecked")
    public static List<Odontologo> cargarOdontologos() {
        return (List<Odontologo>) cargar(ARCHIVO_ODONTOLOGOS);
    }

    @SuppressWarnings("unchecked")
    public static List<Turno> cargarTurnos() {
        return (List<Turno>) cargar(ARCHIVO_TURNOS);
    }

    private static void guardar(Object datos, String archivo) {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream(archivo));
            out.writeObject(datos);
        } catch (IOException e) {
            System.out.println("Error al guardar en " + archivo + ": " + e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar " + archivo);
                }
            }
        }
    }

    private static Object cargar(String archivo) {
        File f = new File(archivo);
        if (!f.exists()) {
            return new ArrayList<>();
        }

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(archivo));
            return in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar " + archivo + ": " + e.getMessage());
            return new ArrayList<>();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    System.out.println("Error al cerrar " + archivo);
                }
            }
        }
    }
}