package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import Exception.DatoInvalidoException;

public abstract class Odontologo implements Comparable<Odontologo> {
    private static Long contadorId = 1L;

    private Long id;
    private String nombre;
    private String apellido;
    private String matricula;
    private List<Turno> turnos = new ArrayList<>();

    public Odontologo(String nombre, String apellido, String matricula ){
        this.id = contadorId++;
        this.nombre = nombre;
        this.apellido = apellido;
        setMatricula(matricula);
    }
    public Odontologo(){
        this.id = contadorId++;
        // constructor vacio como pide la consigna
        // lo voy a terminar usando para poder crear un odontologo sin necesidad de pasarle todos los datos, y luego setearlos uno por uno con inputs
    }

    public void agregarTurno(Turno turno) { // misma logica q en Paciente
        this.turnos.add(turno);
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    @Override
    public int compareTo(Odontologo otro) {
        int comparacionApellido = this.apellido.compareToIgnoreCase(otro.apellido);
        if (comparacionApellido != 0) {
            return comparacionApellido;
        }
        return this.nombre.compareToIgnoreCase(otro.nombre);
    }

    public String toString(){
        return "Datos del odontologo " + id + "\n"+
        "nombre: " + nombre + "\n"+
        "apellido: " + apellido + "\n"+
        "matricula: " + matricula;
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj){
            return true;
        }
        if (obj == null || getClass() != obj.getClass()){
            return false;
        }
        Odontologo other = (Odontologo) obj;
        return java.util.Objects.equals(this.matricula, other.matricula);
    }
    @Override
    public int hashCode() {
        return Objects.hash(matricula);
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
        // mismo caso q Paciente
    }

    public String getMatricula(){
        return this.matricula;
    }

    // setters

    // no estoy seguro si conviene dejar que el id del odontologo se pueda modificar, ya que es un dato unico que lo identifica, pero lo dejo por las dudas
    // al final si lo uso
     public void setId(Long id){
     this.id = id;
     }


    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setApellido(String apellido){
        this.apellido = apellido;
    }

    public void setMatricula(String matricula) {
        if (matricula == null || matricula.isBlank()) {
            throw new DatoInvalidoException("matricula", "no puede estar vacia");
        }
        this.matricula = matricula;
    }

    public abstract int calcularDuracionTurno();
}
