package Modelo;

public class Endodoncista extends Odontologo{
    public Endodoncista(String nombre, String apellido, String matricula){super(nombre, apellido, matricula);}
    public Endodoncista(){super();}
    @Override
    public int calcularDuracionTurno(){return 60;}

    @Override
    public String toString(){return super.toString()+"\nEspecialidad: Endodoncia (60 min)";}

}
