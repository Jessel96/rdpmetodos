package control;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.JOptionPane;
import modelo.Posicion;
import modelo.RDP;

public class AreaDibujo extends javax.swing.JPanel{

    RDP redpetri = new RDP();
    private int click=-1;
    private int punto_x, punto_y,inicioX,inicioY;
    private boolean confirm=false;
    private int numPlazaInicio=-1,numTransicionInicio=-1;
    private int numPlazaFinal=-1,numTransicionFinal=-1;

    public AreaDibujo(){
        initComponents();
    }

    public void opcion_click(int a){
        this.click=a;
    }

    public RDP retornarRed(){
        return this.redpetri;
    }


    private void initComponents() {
        setPreferredSize(new java.awt.Dimension(700, 500));
        setBackground(Color.white);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            @Override
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
            @Override
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });
    }

    public void formMouseDragged(MouseEvent e){
        if(this.click==3){
            this.punto_x=e.getX();
            this.punto_y=e.getY();
            this.confirm=true;
        }
        repaint();
    }
    private void formMouseMoved(MouseEvent e) {
    }
    public void formMouseClicked(MouseEvent e){
        if(this.click==0){
            this.punto_x=e.getX();
            this.punto_y=e.getY();
            if(this.redpetri.verificarPosicion(this.punto_x, this.punto_y)){
                String respuesta=(String)JOptionPane.showInputDialog(this,"Ingrese el numero de marcas:");
                if(respuesta!=null){
                    this.confirm=true;
                    this.redpetri.agregarPlaza(Integer.parseInt(respuesta),this.punto_x,this.punto_y);
                }
                else{
                    JOptionPane.showMessageDialog(this, "Error en el ingreso\nCreacion de la plaza cancelada");
                    this.opcion_click(-1);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, "Presione sobre un área de dibujo descubierta");
                this.confirm=false;
                this.opcion_click(-1);
            }
        }else{
            if(this.click==1){
                this.punto_x=e.getX();
                this.punto_y=e.getY();
                if(this.redpetri.verificarPosicion(this.punto_x, this.punto_y)){
                    this.confirm=true;
                    this.redpetri.agregarTransicion(this.punto_x,this.punto_y);
                }
                else{
                    JOptionPane.showMessageDialog(this, "Presione sobre un área de dibujo descubierta");
                    this.confirm=false;
                    this.opcion_click(-1);
                }
            }
        }
        repaint();
    }
    private void formMousePressed(MouseEvent e) {
        if(this.click==2){
            this.punto_x=e.getX();
            this.punto_y=e.getY();
            this.inicioX=this.punto_x;
            this.inicioY=this.punto_y;
            if(this.redpetri.verificaArcoPlaza(this.punto_x, this.punto_y)!=-1){
                this.numPlazaInicio=this.redpetri.verificaArcoPlaza(this.punto_x, this.punto_y);
                this.opcion_click(3);
            }
            else{
                if(this.redpetri.verificaArcoTransicion(this.punto_x, this.punto_y)!=-1){
                    this.numTransicionInicio=this.redpetri.verificaArcoTransicion(this.punto_x, this.punto_y);
                    this.opcion_click(3);
                }
                else{
                    JOptionPane.showMessageDialog(this, "Presione sobre una Plaza o una Transición");
                    this.confirm=false;
                    this.opcion_click(-1);
                }
            }
        }
        repaint();
    }

    private void formMouseReleased(MouseEvent e) {
        if(this.click==3){
            this.opcion_click(-1);
            this.punto_x=e.getX();
            this.punto_y=e.getY();
            if(this.numPlazaInicio!=-1){
                   this.numTransicionFinal=this.redpetri.verificaArcoTransicion(this.punto_x, this.punto_y);
                   if(this.numTransicionFinal!=-1){
                        String respuesta=(String)JOptionPane.showInputDialog(this,"Ingrese el peso de la transición:");
                        if(respuesta!=null){
                            this.opcion_click(-1);
                            this.confirm=false;
                            this.redpetri.agregarArco(this.numPlazaInicio, this.numTransicionFinal,true,Integer.parseInt(respuesta));
                            this.numPlazaInicio=-1;
                            this.numTransicionFinal=-1;
                        }
                        else{
                            JOptionPane.showMessageDialog(this, "Error en el ingreso. Creacion de arco cancelada");
                            this.numPlazaInicio=-1;
                            this.numTransicionFinal=-1;
                            this.confirm=false;
                            this.opcion_click(-1);
                        }
                   }
                   else{
                       JOptionPane.showMessageDialog(this, "Libere el click sobre una Transición");
                       this.numPlazaInicio=-1;
                       this.numTransicionFinal=-1;
                       this.confirm=false;
                       this.opcion_click(-1);
                   }
            }
            else{
                if(this.numTransicionInicio!=-1){
                    this.numPlazaFinal=this.redpetri.verificaArcoPlaza(this.punto_x, this.punto_y);
                    if(this.numPlazaFinal!=-1){
                        String respuesta=(String)JOptionPane.showInputDialog(this,"Ingrese el peso de la transición:");
                        if(respuesta!=null){
                            this.opcion_click(-1);
                            this.redpetri.agregarArco(this.numPlazaFinal, this.numTransicionInicio,false,Integer.parseInt(respuesta));
                            this.numPlazaInicio=-1;
                            this.numTransicionFinal=-1;
                        }
                        else{
                            JOptionPane.showMessageDialog(this, "Error en el ingreso. Creacion de arco cancelada");
                            this.opcion_click(-1);
                            this.numTransicionInicio=-1;
                            this.numPlazaFinal=-1;
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Libere el click sobre una Plaza");
                        this.numTransicionInicio=-1;
                        this.numPlazaFinal=-1;
                        this.confirm=false;
                        this.opcion_click(-1);
                    }
                }
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        volverADibujar(g);

        if(this.click==0 && this.confirm){

            g.setColor(Color.blue);
            g.drawOval(this.punto_x,this.punto_y,50,50);
            g.drawString("P"+this.redpetri.numPlazas(), this.punto_x, this.punto_y);
            g.drawString(""+this.redpetri.getPlaza(this.redpetri.numPlazas()-1), this.punto_x+20, this.punto_y+30);
            confirm=false;
            opcion_click(-1);
        }
        if(this.click==1 && this.confirm){
            g.setColor(Color.black);
            g.drawRect(this.punto_x, this.punto_y, 9, 30);
            g.drawString("t"+this.redpetri.numTransiciones(),this.punto_x, this.punto_y-2);
            confirm=false;
            opcion_click(-1);
        }
        if(this.click==3 && this.confirm){
            double ang=0.0, angSep=0.0;
            double tx,ty;
            int dist=0;
            Point punto1=null,punto2=null;

            //defino dos puntos extremos
            punto1=new Point(this.inicioX,this.inicioY);
            punto2=new Point(this.punto_x,this.punto_y);

            //tamaño de la punta de la flecha
            dist=15;

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
            g.setColor (Color.red);
            // grosor de la linea
            g2D.setStroke (new BasicStroke(1.2f));
            //dibuja la linea de extremo a extremo
            g.drawLine (punto1.x,punto1.y,punto.x,punto.y);
            //dibujar la punta
            g.drawLine (p1.x,p1.y,punto.x,punto.y);
            g.drawLine (p2.x,p2.y,punto.x,punto.y);

            this.confirm=false;
        }
    }

    public void volverADibujar(Graphics g){
        int i, inPlaza, inTrans;

        Posicion pos =new Posicion();
        Posicion pos2 =new Posicion();
        for(i=0;i<this.redpetri.numPlazas();i++){
            pos=this.redpetri.getPosPlaza(i);
            g.setColor(Color.blue);
            g.drawOval(pos.getX(),pos.getY(),50,50);
            g.drawString("P"+(i+1), pos.getX(), pos.getY());
            g.drawString(""+this.redpetri.getPlaza(i), pos.getX()+20, pos.getY()+30);
        }
        for(i=0;i<this.redpetri.numTransiciones();i++){
            pos=this.redpetri.getPosTransicion(i);
            g.setColor(Color.black);
            g.drawRect(pos.getX(), pos.getY(), 9, 30);
            g.drawString("t"+(i+1),pos.getX(), pos.getY()-2);
        }
        for(i=0;i<this.redpetri.numArcos(); i++){
            inPlaza=this.redpetri.getIndicePlaza(i);
            inTrans=this.redpetri.getIndiceTransicion(i);

            pos=this.redpetri.getPosPlaza(inPlaza); //posicion de la plaza
            pos2=this.redpetri.getPosTransicion(inTrans); //posicion de la transicion
            g.setColor(Color.red);
            g.drawLine(pos.getX(), pos.getY(), pos2.getX(), pos2.getY());
            g.setColor(Color.black);
            g.drawString(""+this.redpetri.getPesoArco(i),((pos.getX()+pos2.getX())/2),((pos.getY()+pos2.getY())/2));
        }
    }



}
