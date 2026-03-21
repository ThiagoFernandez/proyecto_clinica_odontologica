package Modelo;

import java.time.LocalDate;
import java.util.Objects;

public class Paciente {
    // Atributos
    private static Long contadorId = 1L;

    private Long id; // unico
    private String nombre;
    private String apellido;
    private String dni; // unico
    private String email;
    private LocalDate fechaIngreso;
    private Domicilio domicilio;


    public Paciente(String nombre, String apellido, String dni, String email, LocalDate fechaIngreso, Domicilio domicilio){
        this.id = contadorId++;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.email = email;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
    }

    public Paciente(){
        this.id = contadorId++;
        // constructor vacio como pide la consigna
        // lo voy a terminar usando para poder crear un paciente sin necesidad de pasarle todos los datos, y luego setearlos uno por uno con inputs
    }

    @Override
    public String toString(){
        return "Datos del paciente " + id + "\n"+
        "nombre: " + nombre + "\n"+
        "apellido: " + apellido + "\n"+
        "dni: " + dni + "\n"+
        "email: " + email + "\n"+
        "fecha de ingreso: " + fechaIngreso + "\n"+
        "domicilio: " + domicilio.getCalle() + " " + domicilio.getNumero() + " " + domicilio.getLocalidad() + " " + domicilio.getProvincia();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Paciente other = (Paciente) obj;
        return this.dni != null && this.dni.equals(other.dni);
    }
    @Override
    public int hashCode() {
        return Objects.hash(dni);
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
