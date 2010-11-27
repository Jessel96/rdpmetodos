package Ejecutora;
/**
 *
 * @author marza
 */
public class Interseccion {
    public Posicion pos;// posicion X Y en la ventana

    private boolean norte; // direcciones habilitadas o no para navegar
    private boolean sur;
    private boolean este;
    private boolean oeste;

    private double dnorte;//distancias a las cuales se encuentran las intersecciones proximas.
    private double dsur;
    private double deste;
    private double doeste;

    private int lugaresInteres;//es lugar de interes o no
    private String nombreLugar;// nombre del lugar

    public Interseccion(){
    }

    public Interseccion(Interseccion a){
        this.pos = new Posicion(a.pos.getX(),a.pos.getY());
        this.norte = a.getNorte();
        this.sur = a.getSur();
        this.este = a.getEste();
        this.oeste = a.getOeste();

        this.lugaresInteres= a.getLugaresInteres();
        this.nombreLugar= a.getNombreLugar();
    }
    public void setPos(Posicion pos) {
        this.pos = pos;
    }

    public void setNorte(boolean norte) {
        this.norte = norte;
    }

    public boolean getNorte(){
        return this.norte;
    }

    public void setSur(boolean sur) {
        this.sur = sur;
    }

    public boolean getSur(){
        return this.sur;
    }

    public void setEste(boolean este) {
        this.este = este;
    }

    public boolean getEste(){
        return this.este;
    }

    public void setOeste(boolean oeste) {
        this.oeste = oeste;
    }

    public boolean getOeste(){
        return this.oeste;
    }

    public void setDnorte(double dnorte) {
        this.dnorte = dnorte;
    }

    public double getDnorte() {
        return dnorte;
    }

    public void setDsur(double dsur) {
        this.dsur = dsur;
    }

    public double getDsur() {
        return dsur;
    }

    public void setDeste(double deste) {
        this.deste = deste;
    }

    public double getDeste() {
        return deste;
    }

    public void setDoeste(double doeste) {
        this.doeste = doeste;
    }

    public double getDoeste() {
        return doeste;
    }

    public void setLugaresInteres(int lugaresInteres) {
        this.lugaresInteres = lugaresInteres;
    }

    public int getLugaresInteres() {
        return lugaresInteres;
    }

    public void setNombreLugar(String nombreLugar) {
        if(this.nombreLugar!=null){
            this.nombreLugar = this.nombreLugar+ " "+nombreLugar;
        }
        else{
            this.nombreLugar = nombreLugar;
        }
    }
    public void setNombreLugar(){
        this.nombreLugar = null;
    }

    public String getNombreLugar() {
        if (nombreLugar!=null){
            return nombreLugar;
        }
        else{
            return "";
        }
    }

}//Fin Clase Interseccion.
