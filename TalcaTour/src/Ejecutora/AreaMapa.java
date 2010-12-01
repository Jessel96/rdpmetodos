package Ejecutora;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.*;
import java.util.Scanner;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import java.util.regex.*;
import java.util.ArrayList;

public class AreaMapa extends javax.swing.JPanel{

    private Image imagenFondo;
    private Interseccion mapa[][] = new Interseccion[9][14];
    private Interseccion inter = new Interseccion();
    private Posicion p;
    private String[] lugares;
    private int [][] interLugares;
    private ArrayList <Posicion> recorrido=new ArrayList<Posicion>(0);
    private boolean flag=false;

    public AreaMapa(){
        this.cargarMapa();
        //System.out.println("NumLugares "+mapa[1][1].getLugaresInteres()+" Y "+mapa[0][0].pos.getY());
        this.calcularDistancias();
        this.cargarLugaresAvisitar();
        this.buscarInterLugares();
        this.ordenarNavegacion();
        this.buscarRuta();
        initComponents();
    }
    public void setflag(int i){
        if(i==1){
        this.flag=true;
        }
        else{
            if(i==0)
            this.flag=false;
        }
    }
    private void cargarMapa(){
        FileReader file= null;
        try {
            file = new FileReader("Mapeo.txt");
            Scanner lee = new Scanner(file);
            int i,j;
            for(i=0;i<9;i++){
                for(j=0;j<14;j++){
                //Lee la posicion x,y del mapa
                p = new Posicion(lee.nextInt(),lee.nextInt());
                this.inter.setPos(p);
                //System.out.print("Punto x: "+p.getX() +" Punto y:"+p.getY());

                //Lee si la calle está o no habilitada
                if(lee.nextInt()==1){
                    this.inter.setNorte(true);
                }
                else{
                    this.inter.setNorte(false);
                }
                if(lee.nextInt()==1){
                    this.inter.setSur(true);
                }
                else{
                    this.inter.setSur(false);
                }
                if(lee.nextInt()==1){
                    this.inter.setEste(true);
                }
                else{
                    this.inter.setEste(false);
                }
                if(lee.nextInt()==1){
                    this.inter.setOeste(true);
                }
                else{
                    this.inter.setOeste(false);
                }

                //Lee la cantidad de lugares de interes que hay en el nodo
                this.inter.setLugaresInteres(lee.nextInt());
                //System.out.print(" Lugares de interes: " + this.inter.getLugaresInteres());
                if(this.inter.getLugaresInteres()!=0){
                    while(!lee.hasNextInt()){
                        this.inter.setNombreLugar(lee.next());
                    }
                    //System.out.print(" Nombre: "+ this.inter.getNombreLugar());
                }
                mapa[i][j] = new Interseccion(this.inter);
                this.inter.setNombreLugar();
               // System.out.println("");
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AreaMapa.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(AreaMapa.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void cargarLugaresAvisitar(){
        try {
            FileReader file = null;
            int i;
            file = new FileReader("sitios.txt");
            Scanner lee = new Scanner(file);
            this.lugares = new String[lee.nextInt()];
            lee.nextLine();
            for (i = 0; i < this.lugares.length; i++) {
                this.lugares[i] = lee.nextLine();
            }
//            for (i = 0; i < this.lugares.length; i++) {
//                System.out.println(this.lugares[i]);
//            }
            lee.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AreaMapa.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void buscarInterLugares(){
        int i;
        this.interLugares = new int [this.lugares.length+1][2];

        for (i = 0; i < this.lugares.length; i++) {
            setLugar(i);
        }
    }
    
    public int setLugar(int i){
        int j,k;

        for(j=0;j<9;j++){
            for(k=0;k<14;k++){
                Pattern patron = Pattern.compile(this.lugares[i]);
                Matcher m = patron.matcher(this.mapa[j][k].getNombreLugar());
                //System.out.println("Ingreso: "+this.lugares[i]+" Buscando: "+this.mapa[j][k].getNombreLugar());
                if(m.find()){
                    this.interLugares[i][0]=j;
                    this.interLugares[i][1]=k;                   
                    return 0;
                }
            }
        }
        return 0;
    }

    public double distanciaEntre2pts(double x1,double y1,double x2,double y2){
        return Math.sqrt((Math.pow(x2-x1,2)+Math.pow(y2-y1,2)));
    }
    private void calcularDistancias(){
        int i,j;

        for(i=0;i<9;i++){
            for(j=0;j<14;j++){
                //preguntar por 4 direcciones de cada nodo

                //System.out.println("i:"+i+" j:"+j+" ");
                if(this.mapa[i][j].getNorte()){
                    //setea norte             calcula Distancia       pos x intersecion actual   pos y intersecion actual  ,pos x nodo al norte (arriba en mtrz), pos Y nodo al norte
                    this.mapa[i][j].setDnorte(this.distanciaEntre2pts(this.mapa[i][j].pos.getX(),this.mapa[i][j].pos.getY() ,this.mapa[i-1][j].pos.getX(),this.mapa[i-1][j].pos.getY()));
                }
                if(this.mapa[i][j].getEste()){
                     //setea este             calcula Distancia       pos x intersecion actual   pos y intersecion actual  ,pos x nodo al este (derecha en mtrz), pos Y nodo al este
                    this.mapa[i][j].setDeste(this.distanciaEntre2pts(this.mapa[i][j].pos.getX(),this.mapa[i][j].pos.getY() ,this.mapa[i][j+1].pos.getX(),this.mapa[i][j+1].pos.getY()));
                }
                if(this.mapa[i][j].getSur()){
                     //setea sur             calcula Distancia       pos x intersecion actual   pos y intersecion actual  ,pos x nodo al sur (abajo en mtrz), pos Y nodo al sur
                    this.mapa[i][j].setDsur(this.distanciaEntre2pts(this.mapa[i][j].pos.getX(),this.mapa[i][j].pos.getY() ,this.mapa[i+1][j].pos.getX(),this.mapa[i+1][j].pos.getY()));
                }
                if( this.mapa[i][j].getOeste()){
                     //setea oeste             calcula Distancia       pos x intersecion actual   pos y intersecion actual  ,pos x nodo al oest (izq en mtrz), pos Y nodo al oest
                    this.mapa[i][j].setDoeste(this.distanciaEntre2pts(this.mapa[i][j].pos.getX(),this.mapa[i][j].pos.getY() ,this.mapa[i][j-1].pos.getX(),this.mapa[i][j-1].pos.getY()));
                }
            }
        }
    }

    private void ordenarNavegacion(){
        int i,j,auxIndice=1,aux1,aux2;
        double dist1,dist2;
        
        for (i = 1; i < lugares.length; i++) {
            dist1=this.distanciaEntre2pts(
                        this.mapa[this.interLugares[i-1][0]][this.interLugares[i-1][1]].pos.getX(),
                        this.mapa[this.interLugares[i-1][0]][this.interLugares[i-1][1]].pos.getY(),
                        this.mapa[this.interLugares[i][0]][this.interLugares[i][1]].pos.getX(),
                        this.mapa[this.interLugares[i][0]][this.interLugares[i][1]].pos.getY()
                        );
            for (j = i+1; j < lugares.length-1; j++) {
                dist2=this.distanciaEntre2pts(
                        this.mapa[this.interLugares[i-1][0]][this.interLugares[i-1][1]].pos.getX(),
                        this.mapa[this.interLugares[i-1][0]][this.interLugares[i-1][1]].pos.getY(),
                        this.mapa[this.interLugares[j][0]][this.interLugares[j][1]].pos.getX(),
                        this.mapa[this.interLugares[j][0]][this.interLugares[j][1]].pos.getY()
                        );
                if(dist2<dist1){
                    auxIndice=j;
                    dist1=dist2;
                }
            }
            aux1=this.interLugares[i][0];
            aux2=this.interLugares[i][1];
            this.interLugares[i][0]=this.interLugares[auxIndice][0];
            this.interLugares[i][1]=this.interLugares[auxIndice][1];
            this.interLugares[auxIndice][0]=aux1;
            this.interLugares[auxIndice][1]=aux2;
        }
        this.interLugares[i][0]=this.interLugares[0][0];
        this.interLugares[i][1]=this.interLugares[0][1];
    }

    public void buscarRuta(){

        Posicion ptoInicio, ptoFin, ptoOrigen, ptoInter;
        int i;

        ptoOrigen = new Posicion(this.interLugares[0][0],this.interLugares[0][1]);
        this.recorrido.add(ptoOrigen);
        for(i=0;i<this.lugares.length;i++){
            ptoInicio = new Posicion(this.interLugares[i][0],this.interLugares[i][1]);
            ptoFin = new Posicion(this.interLugares[i+1][0],this.interLugares[i+1][1]);
            while(!this.verifica(ptoInicio, ptoFin)){
                if(ptoFin.getX()-ptoInicio.getX()<0){//Pregunta si debe avanzar arriba
                    if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getNorte()){//preg. si puede avanzar arriba.
                        ptoInter = new Posicion(ptoInicio.getX()-1,ptoInicio.getY());
                        this.recorrido.add(ptoInter);
                        ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                    }//Fin if
                    else{
                        if(ptoFin.getY()-ptoInicio.getY()<0){//preg. si debe ir a izquierda
                            if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getOeste()){//preg. si puede avanzar izq.
                                ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()-1);
                                this.recorrido.add(ptoInter);
                                ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                            }
                            else{
                                if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getEste()){//preg. si puede avanzar der
                                    ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()+1);
                                    this.recorrido.add(ptoInter);
                                    ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                }
                                else{
                                    if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getSur()){//preg. si puede avanzar abajo
                                        ptoInter = new Posicion(ptoInicio.getX()+1,ptoInicio.getY());
                                        this.recorrido.add(ptoInter);
                                        ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                    }
                                }
                            }
                        }
                        else{
                            if(ptoFin.getY()-ptoInicio.getY()>0){//Pregunta si debe avanzar derecha
                                if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getEste()){//preg. si puede avanzar derecha
                                    ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()+1);
                                    this.recorrido.add(ptoInter);
                                    ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                }
                                else{
                                    if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getSur()){//Avanza abajo
                                        ptoInter = new Posicion(ptoInicio.getX()+1,ptoInicio.getY());
                                        this.recorrido.add(ptoInter);
                                        ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                    }
                                     else{
                                        if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getOeste()){//Avanza izquierda
                                            ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()-1);
                                            this.recorrido.add(ptoInter);
                                            ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                        }
                                     }
                                }
                            }
                        }
                    }
                }//Fin if Avanza arriba
                else{
                    if(ptoFin.getX()-ptoInicio.getX()>0){//preguntamos si debemos Avanzar abajo
                        if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getSur()){
                            ptoInter = new Posicion(ptoInicio.getX()+1,ptoInicio.getY());
                            this.recorrido.add(ptoInter);
                            ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                        }
                        else{
                            if(ptoFin.getY()-ptoInicio.getY()>0){//preg. si debemos ir derecha
                                if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getEste()){
                                    ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()+1);
                                    this.recorrido.add(ptoInter);
                                    ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                }
                                else{//si no puede ir derecha ni arriba (donde debia ir)
                                    if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getNorte()){//Preg. si puede avanza arriba
                                        ptoInter = new Posicion(ptoInicio.getX()-1,ptoInicio.getY());
                                        this.recorrido.add(ptoInter);
                                        ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                    }
                                    else{
                                        if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getOeste()){//Avanza izquierda
                                            ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()-1);
                                            this.recorrido.add(ptoInter);
                                            ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                        }
                                    }
                                }
                            }
                            else{
                                if(ptoFin.getY()-ptoInicio.getY()<0){//preg. si debemos ir izquierda
                                    if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getOeste()){//Preg si puede Avanzar izquierda
                                        ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()-1);
                                        this.recorrido.add(ptoInter);
                                        ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                    }
                                    else{
                                        if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getEste()){//Preg si puede avanzar derecha
                                            ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()+1);
                                            this.recorrido.add(ptoInter);
                                            ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                        }
                                        else{
                                            if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getNorte()){//Preg si puede avanzar arriba
                                                ptoInter = new Posicion(ptoInicio.getX()-1,ptoInicio.getY());
                                                this.recorrido.add(ptoInter);
                                                ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }//Fin Arriba y abajo
                /*****************************************/
                if(ptoFin.getY()-ptoInicio.getY()>0){//si debe ir Derecha
                    if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getEste()){//si puedo ir a derecha (este)
                        ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()+1);
                        this.recorrido.add(ptoInter);
                        ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                    }
                    else{
                        if(ptoFin.getX()-ptoInicio.getX()<0){//si debe ir arriba(norte)
                            if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getNorte()){//preg. si puede avanzar arriba.
                                ptoInter = new Posicion(ptoInicio.getX()-1,ptoInicio.getY());
                                this.recorrido.add(ptoInter);
                                ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                            }//Fin if puede avanzar arriba
                            else{//si no puede ir arriba
                                if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getSur()){//si puede ir abajo?
                                    ptoInter = new Posicion(ptoInicio.getX()+1,ptoInicio.getY());
                                    this.recorrido.add(ptoInter);
                                    ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                }
                                else{//va a la izquierda
                                    if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getOeste()){//Preg si puede Avanzar izquierda
                                        ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()-1);
                                        this.recorrido.add(ptoInter);
                                        ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                    }
                                }
                            }
                        }
                        else{
                            if(ptoFin.getX()-ptoInicio.getX()>0){//preguntamos si debemos Avanzar abajo
                                if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getSur()){
                                    ptoInter = new Posicion(ptoInicio.getX()+1,ptoInicio.getY());
                                    this.recorrido.add(ptoInter);
                                    ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                }
                                else{//si no podemor ir abajo.
                                    if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getOeste()){//Preg si puede Avanzar izquierda
                                        ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()-1);
                                        this.recorrido.add(ptoInter);
                                        ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                    }
                                    else{//va arriba.
                                        if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getNorte()){//preg. si puede avanzar arriba.
                                            ptoInter = new Posicion(ptoInicio.getX()-1,ptoInicio.getY());
                                            this.recorrido.add(ptoInter);
                                            ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                        }//Fin if puede avanzar arriba
                                    }
                                }
                            }

                        }
                    }
                }
                else{//si no debemor ir derecha
                    if(ptoFin.getY()-ptoInicio.getY()<0){//preg. si debemos ir izquierda
                        if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getOeste()){//Preg si puede Avanzar izquierda
                            ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()-1);
                            this.recorrido.add(ptoInter);
                            ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                        }
                        else{
                             if(ptoFin.getX()-ptoInicio.getX()<0){//si debe ir arriba(norte)
                                if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getNorte()){//preg. si puede avanzar arriba.
                                    ptoInter = new Posicion(ptoInicio.getX()-1,ptoInicio.getY());
                                    this.recorrido.add(ptoInter);
                                    ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                }//Fin if puede avanzar arriba
                                else{
                                    if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getSur()){//si puede ir abajo?
                                        ptoInter = new Posicion(ptoInicio.getX()+1,ptoInicio.getY());
                                        this.recorrido.add(ptoInter);
                                        ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                    }
                                    else{
                                         if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getEste()){//si puedo ir a derecha (este)
                                            ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()+1);
                                            this.recorrido.add(ptoInter);
                                            ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                        }                                        
                                    }                                    
                                }
                            }
                            else{
                                 if(ptoFin.getX()-ptoInicio.getX()>0){//preguntamos si debemos Avanzar abajo
                                     if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getSur()){
                                         ptoInter = new Posicion(ptoInicio.getX()+1,ptoInicio.getY());
                                         this.recorrido.add(ptoInter);
                                         ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                     }
                                     else{
                                         if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getEste()){//si puedo ir a derecha (este)
                                            ptoInter = new Posicion(ptoInicio.getX(),ptoInicio.getY()+1);
                                            this.recorrido.add(ptoInter);
                                            ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                         }
                                         else{//si no puedo ir derecha.
                                             if(this.mapa[ptoInicio.getX()][ptoInicio.getY()].getNorte()){//preg. si puede avanzar arriba.
                                                ptoInter = new Posicion(ptoInicio.getX()-1,ptoInicio.getY());
                                                this.recorrido.add(ptoInter);
                                                ptoInicio = new Posicion(ptoInter.getX(),ptoInter.getY());
                                            }//Fin if puede avanzar arriba
                                         }
                                     }
                                 }
                            }
                        }
                    }

                }//Fin ELse si no debemos ir derecha.
            }//Fin while
        }//Fin For
    }

    private boolean verifica(Posicion i,Posicion f){
        if(i.getX()==f.getX() && i.getY()==f.getY()){
            return true;
        }
        else{
            return false;
        }
    }

    private void initComponents() {
        setPreferredSize(new java.awt.Dimension(967, 471));
        imagenFondo = new ImageIcon(getClass().getResource("mapa.png")).getImage();
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
            }
        });

        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
            }
            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });

    }

    private void formMouseMoved(MouseEvent evt) {
    }
    private void formMouseClicked(MouseEvent evt){
    }

    @Override //= esto quiere decir que has sobreescrito el método
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo,0,0,null);
        dibujaLugaresInteres(g);
        dibujar(g);

    }

    protected void dibujar(Graphics g){
        int i;
        if(flag){
            for (i=0;  i<this.recorrido.size()-1; i++) {
                double ang=0.0, angSep=0.0;
                double tx,ty;
                int dist=0;
                Point punto1=null,punto2=null;

                //defino dos puntos extremos
                punto1=new Point(this.mapa[this.recorrido.get(i).getX()][this.recorrido.get(i).getY()].pos.getX()
                        ,this.mapa[this.recorrido.get(i).getX()][this.recorrido.get(i).getY()].pos.getY());
                punto2=new Point(this.mapa[this.recorrido.get(i+1).getX()][this.recorrido.get(i+1).getY()].pos.getX()
                        ,this.mapa[this.recorrido.get(i+1).getX()][this.recorrido.get(i+1).getY()].pos.getY());

                //tamaño de la punta de la flecha
                dist=10;

                /* (la coordenadas de la ventana es al revez)
                    calculo de la variacion de "x" y "y" para hallar el angulo
                 **/

                ty=-(punto1.y-punto2.y)*1.0;
                tx=(punto1.x-punto2.x)*1.0;
                //angulo
                ang=Math.atan (ty/tx);

                if(tx<0)
                {// si tx es negativo aumentar 180 grados
                   ang+=Math.PI;
                }

                //puntos de control para la punta
                //p1 y p2 son los puntos de salida
                Point p1=new Point(),p2=new Point(),punto=punto2;

                //angulo de separacion
                angSep=25.0;

                p1.x=(int)(punto.x+dist*Math.cos (ang-Math.toRadians (angSep)));
                p1.y=(int)(punto.y-dist*Math.sin (ang-Math.toRadians (angSep)));
                p2.x=(int)(punto.x+dist*Math.cos (ang+Math.toRadians (angSep)));
                p2.y=(int)(punto.y-dist*Math.sin (ang+Math.toRadians (angSep)));

                Graphics2D g2D=(Graphics2D)g;

                //dale color a la linea
                g.setColor (Color.green);
                // grosor de la linea
                g2D.setStroke (new BasicStroke(2.2f));
                //dibuja la linea de extremo a extremo
                g.drawLine (punto1.x,punto1.y,punto.x,punto.y);
                //dibujar la punta
                g.drawLine (p1.x,p1.y,punto.x,punto.y);
                g.drawLine (p2.x,p2.y,punto.x,punto.y);

                g.setColor(Color.BLACK);
                g.drawString(""+(i+1), (punto1.x+punto2.x)/2, (punto1.y+punto2.y)/2);
            }
        }
    }

    protected void dibujaLugaresInteres(Graphics g){
        int i;
        g.setColor(Color.red);
        for (i = 0; i < this.lugares.length; i++) {
            g.fillOval(mapa[this.interLugares[i][0]][this.interLugares[i][1]].pos.getX(),
                    mapa[this.interLugares[i][0]][this.interLugares[i][1]].pos.getY(), 11, 11);        
        }
        

    }
}
