package Modelo;

public class OdontologoGeneral extends Odontologo{
    public OdontologoGeneral(String nombre, String apellido, String matricula){super(nombre, apellido, matricula);}
    public OdontologoGeneral(){super();}
    @Override
    public int calcularDuracionTurno(){return 30;}

    @Override
    public String toString(){return super.toString() + "\nEspecialidad: Odontologia general (30 min)";}
}
