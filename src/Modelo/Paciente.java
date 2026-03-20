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

    public String toString(){
        return "Datos del paciente " + id + "\n"+
        "nombre: " + nombre + "\n"+
        "apellido: " + apellido + "\n"+
        "dni: " + dni + "\n"+
        "email: " + email + "\n"+
        "fecha de ingreso: " + fechaIngreso + "\n"+
        "domicilio: " + domicilio.getCalle() + " " + domicilio.getNumero() + " " + domicilio.getLocalidad() + " " + domicilio.getProvincia();
    }


    // getters
    public Long getId(){
        return this.id;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getApellido(){
        return this.apellido;
    }

    public String getNombreCompleto(){
        return getNombre() + " " + getApellido();
        // uso el metodo getNombre y getApellido para en lugar de this.atributo para que si en algun momento se cambia la logica de como se obtiene el nombre o el apellido, el metodo getNombreCompleto siga funcionando correctamente
    }

    public String getDni(){
        return this.dni;
    }

    public String getEmail(){
        return this.email;
    }

    public LocalDate getFechaIngreso(){
        return this.fechaIngreso;
    }

    public Domicilio getDomicilio(){
        return this.domicilio;
    }

    // setters

     /** no estoy seguro si conviene dejar que el id del paciente se pueda modificar, ya que es un dato unico que lo identifica, pero lo dejo por las dudas
     * public void setId(Long id){
        this.id = id;
     }
     */
    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public void setDni(String dni){
        this.dni = dni;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setFechaIngreso(LocalDate fechaIngreso){
        this.fechaIngreso = fechaIngreso;
    }

    public void setDomicilio(Domicilio domicilio){
        this.domicilio = domicilio;
    }

}
