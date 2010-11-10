package modelo;

public class Arco {

    private int plaza;
    private int transicion;
    private boolean prepost; //si es true es pre, si es false es post
    private int peso;

    public Arco() {
    }

    public Arco(int plaza, int transicion, boolean prepost, int peso) {
        this.plaza = plaza;
        this.transicion = transicion;
        this.prepost = prepost;
        this.peso = peso;
    }

    public int getPlaza() {
        return plaza;
    }

    public int getTransicion() {
        return transicion;
    }

    public int getPeso() {
        return peso;
    }

    public String prepost(){
        if(this.prepost){
            return "pre";
        }
        else{
            return "post";
        }
    }
}
