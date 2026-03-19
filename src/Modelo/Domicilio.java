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

    public String toString(){
        return "Domicilio{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", numero='" + numero + '\'' +
                ", localidad='" + localidad + '\'' +
                ", provincia='" + provincia + '\'' +
                '}';
    }

    // getters

    public Long getId(){
        return id;
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

    // setters
    /** no estoy seguro si conviene dejar que el id del domicilio se pueda modificar, ya que es un dato unico que lo identifica, pero lo dejo por las dudas
     * public void setId(Long id){
     this.id = id;
     }
     */

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setLocalidad(String localidad){
        this.localidad = localidad;
    }

    public void setNumero(String numero){
        this.numero = numero;
    }

    public void setProvincia(String provincia){
        this.provincia = provincia;
    }
}
