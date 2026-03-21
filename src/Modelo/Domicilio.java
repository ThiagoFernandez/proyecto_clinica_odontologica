package Modelo;

public class Domicilio {
    private static Long contadorId = 1L;

    private Long id;
    private String calle;
    private String numero;
    private String localidad;
    private String provincia;

    public Domicilio(String calle, String numero, String localidad, String provincia){
        this.id = contadorId++;
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
    public Domicilio(){
        this.id = contadorId++;
        // constructor vacio como pide la consigna
        // lo voy a terminar usando para poder crear un domicilio sin necesidad de pasarle todos los datos, y luego setearlos uno por uno con inputs
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
