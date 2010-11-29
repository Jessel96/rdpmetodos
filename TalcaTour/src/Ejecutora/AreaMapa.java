package Ejecutora;

import java.awt.Color;
import java.io.*;
import java.util.Scanner;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import java.util.regex.*;

public class AreaMapa extends javax.swing.JPanel{

    private Image imagenFondo;
    private Interseccion mapa[][] = new Interseccion[9][14];
    private Interseccion inter = new Interseccion();
    private Posicion p;
    private String[] lugares;
    private int [][] interLugares;


    public AreaMapa(){
        this.cargarMapa();
        //System.out.println("NumLugares "+mapa[1][1].getLugaresInteres()+" Y "+mapa[0][0].pos.getY());
        this.calcularDistancias();
        this.cargarLugaresAvisitar();
        this.buscarInterLugares();
        initComponents();
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
                //Calcular las distancias

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
        this.interLugares = new int [this.lugares.length][2];

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
                System.out.println("Ingreso: "+this.lugares[i]+" Buscando: "+this.mapa[j][k].getNombreLugar());
                if(m.find()){
                    this.interLugares[i][0]=j;
                    this.interLugares[i][1]=k;
                    System.out.println("J: "+j+" K: "+k);
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
        
    }

    protected void dibujaLugaresInteres(Graphics g){
        int i;
        g.setColor(Color.RED);
        for (i = 0; i < this.lugares.length; i++) {
            g.drawString("PEO",mapa[this.interLugares[i][0]][this.interLugares[i][1]].pos.getX() ,
                    mapa[this.interLugares[i][0]][this.interLugares[i][1]].pos.getY());
        //System.out.println(mapa[this.interLugares[i][0]][this.interLugares[i][1]].pos.getX()+" "+mapa[this.interLugares[i][0]][this.interLugares[i][1]].pos.getY());
        }
        

    }



}
