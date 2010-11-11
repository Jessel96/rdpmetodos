package modelo;

import java.util.ArrayList;

public class RDP {

    final int ancho1=50,alto1=50,ancho2=50,alto2=60;
    private int pre[][]; //matriz pre plazas a transiciones(plazas x transiciones)
    private int post[][]; //matriz pre transiciones a plazas(plazas x transiciones)

    private ArrayList<Integer> plazas = new ArrayList<Integer>(0); //marcas de cada plaza

    //guarda la posicion x e y de la plaza
    private ArrayList <Posicion> posPlaza=new ArrayList<Posicion>(0); //posiciones de las plazas de n_plazas x 2
                                //(guarda posXYPlaza en cada posicion)    
    private ArrayList <Posicion>posTransicion=new ArrayList<Posicion>(0); //posiciones de las transiciones de n_transiciones x 2
                                    //(guarda posXYTransicion en cada posicion)
    private ArrayList <Arco> arcos=new ArrayList<Arco>(0);

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

    public void agregarArco(int indicePlaza,int indiceTransicion,boolean preOPost,int peso){
        Arco a=new Arco(indicePlaza,indiceTransicion,preOPost,peso);
        
        this.arcos.add(a);
    }

    public int numArcos(){
        return this.arcos.size();
    }

    public int getIndicePlaza(int nArco){
        return arcos.get(nArco).getPlaza();//desde el arraylist arcos rescato el arco correspondiente al que va i
                                          //retorna un tipo arco y a ese le extraigo el indice de la plaza a la que apunta
    }
    public int getIndiceTransicion(int ntrans){
        return arcos.get(ntrans).getTransicion();
    }
    public int getPesoArco(int inArco){
        return arcos.get(inArco).getPeso();//arcos.get(inArco) devuelve el arco
    }

    public boolean verificarPosicion(int x, int y){
        int i=0;
        Posicion p=new Posicion();

        while(i<this.posPlaza.size()){
            p=this.getPosPlaza(i);
            if(x>p.getX()-this.ancho1 && x<p.getX()+this.ancho1 && y>p.getY()-this.alto1 && y<p.getY()+this.alto1){
                return false;
            }
            else{
                i++;
            }
        }
        i=0;
        while(i<this.posTransicion.size()){
            p=this.getPosTransicion(i);
            if(x>p.getX()-this.ancho2 && x<p.getX()+this.ancho2 && y>p.getY()-this.alto2 && y<p.getY()+this.alto2){
                return false;
            }
            else{
                i++;
            }
        }
        return true;
    
    }

    public int verificaArcoPlaza(int x, int y){
        int i=0;
        Posicion p=new Posicion();

        while(i<this.posPlaza.size()){
            p=this.getPosPlaza(i);
            if(x>p.getX() && x<p.getX()+50 && y>p.getY() && y<p.getY()+50){
                return i;
            }
            else{
                i++;
            }
        }
        return -1;
    }

    public int verificaArcoTransicion(int x,int y){
        int i=0;
        Posicion p=new Posicion();
        while(i<this.posTransicion.size()){
            p=this.getPosTransicion(i);
            if(x>p.getX() && x<p.getX()+9 && y>p.getY() && y<p.getY()+30){
                return i;
            }
            else{
                i++;
            }
        }
        return -1;
    }

}//Fin Clase RDP
