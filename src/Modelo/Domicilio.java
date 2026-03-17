package Modelo;

public class Domicilio {
    private Long id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;

    public Domicilio(Long id, String calle, String numero, String localidad, String provincia){
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public String getCalle(){
        return calle;
    }

    public String getLocalidad(){
        return localidad;
    }

    public String getNumero(){
        return numero;
    }

    public String getProvincia(){
        return provincia;
    }
}
