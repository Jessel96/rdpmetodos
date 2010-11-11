package control;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.*;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
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


    private void initComponents() {
        setPreferredSize(new java.awt.Dimension(750, 500));
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
            g.setColor(Color.red);
            g.drawLine(this.inicioX,this.inicioY,this.punto_x,this.punto_y);
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
