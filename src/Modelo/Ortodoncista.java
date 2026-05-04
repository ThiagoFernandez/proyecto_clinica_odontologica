package Modelo;

public class Ortodoncista extends Odontologo{
    public Ortodoncista(String nombre, String apellido, String matricula){super(nombre, apellido, matricula);}
    public Ortodoncista(){super();}
    @Override
    public int calcularDuracionTurno(){return 45;}

    @Override
    public String toString(){return super.toString()+"\nEspecialidad: Ortodoncia (45 min)";}
}
