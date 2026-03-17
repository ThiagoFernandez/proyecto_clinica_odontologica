package Modelo;

import java.time.LocalDate;

public class Paciente {
    // Atributos
    private Long id; // unico
    private String nombre;
    private String apellido;
    private String dni; // unico
    private String email;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;


    public Paciente(Long id, String nombre, String apellido, String dni, String email, LocalDate fechaIngreso, Domicilio domicilio){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    public void mostrarDatosDelPaciente(){
        System.out.println("Datos del paciente " + id + "\n"+
        "nombre: " + nombre + "\n"+
        "apellido: " + apellido + "\n"+
        "dni: " + dni + "\n"+
        "email: " + email + "\n"+
        "fecha de ingreso: " + fechaIngreso + "\n"+
        "domicilio: " + domicilio.getCalle() + " " + domicilio.getNumero() + " " + domicilio.getLocalidad() + " " + domicilio.getProvincia());
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getApellido(){
        return this.apellido;
    }
}
