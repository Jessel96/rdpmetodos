package modelo;

import java.util.ArrayList;

public class RDP {

    private int pre[][]; //matriz pre plazas a transiciones(plazas x transiciones)
    private int post[][]; //matriz pre transiciones a plazas(plazas x transiciones)

    private ArrayList<Integer> plazas = new ArrayList<Integer>(0); //marcas de cada plaza

    //guarda la posicion x e y de la plaza
    private ArrayList <Posicion> posPlaza=new ArrayList<Posicion>(0); //posiciones de las plazas de n_plazas x 2
                                //(guarda posXYPlaza en cada posicion)    
    private ArrayList <Posicion>posTransicion=new ArrayList<Posicion>(0); //posiciones de las transiciones de n_transiciones x 2
                                    //(guarda posXYTransicion en cada posicion)

    public RDP(){
    }

    public void agregarPlaza(int marca,int x,int y){
        Posicion p = new Posicion(x,y);

        this.plazas.add(marca);
        this.posPlaza.add(p);
    }

    public int numPlazas(){
        return plazas.size();
    }

    public Posicion getPosPlaza(int pos){
        return((Posicion)this.posPlaza.get(pos));
    }

    public int getPlaza(int pos) {
        return (plazas.get(pos));
    }


    public void agregarTransicion(int x,int y){
        Posicion p = new Posicion(x,y);
        this.posTransicion.add(p);
    }

    public int numTransiciones(){
        return posTransicion.size();
    }
    public Posicion getPosTransicion(int pos){
        return (Posicion)this.posTransicion.get(pos);
    }


}
