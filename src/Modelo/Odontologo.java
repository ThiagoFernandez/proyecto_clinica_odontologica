package Modelo;

public class Odontologo {
    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;

    public Odontologo(Long id, String nombre, String apellido, String matricula ){
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    public void mostrarDatosDelOdontologo(){
        System.out.println("Datos del odontologo " + id + "\n"+
        "nombre: " + nombre + "\n"+
        "apellido: " + apellido + "\n"+
        "matricula: " + matricula);
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getApellido(){
        return this.apellido;
    }
}
