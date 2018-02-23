package moviles2018itesm.quinelapp;

public class Historial {

    private String jornada;
    private String fecha;
    private String local;
    private String visita;
    private String voto;
    private String resultado;
    private int imagen;

    public Historial(String jornada, String fecha, String local, String visita, String voto, String resultado, int imagen) {
        super();
        this.jornada = jornada;
        this.fecha = fecha;
        this.local = local;
        this.visita = visita;
        this.voto = voto;
        this.resultado = resultado;
        this.imagen = imagen;
    }

    public String getJornada() {
        return this.jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public String getFecha() {
        return this.fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLocal() {
        return this.local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getVisita() {
        return this.visita;
    }

    public void setVisita(String visita) {
        this.visita = visita;
    }

    public String getVoto() {
        return this.voto;
    }

    public void setVoto(String voto) {
        this.voto = voto;
    }

    public String getResultado() {
        return this.resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public int getImagen() {
        return this.imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

}
