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
    private int punto_x, punto_y;
    private boolean confirm=false;

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
    }
    private void formMouseMoved(MouseEvent e) {
    }
    public void formMouseClicked(MouseEvent e){
        if(this.click==0){
            this.punto_x=e.getX();
            this.punto_y=e.getY();
            String respuesta=(String)JOptionPane.showInputDialog(this,"Ingrese el numero de marcas:",JOptionPane.QUESTION_MESSAGE);
            if(respuesta!=null){
                this.confirm=true;
                this.redpetri.agregarPlaza(Integer.parseInt(respuesta),this.punto_x,this.punto_y);
            }
        }else{
            if(this.click==1){
                this.punto_x=e.getX();
                this.punto_y=e.getY();
                this.confirm=true;
                this.redpetri.agregarTransicion(this.punto_x,this.punto_y);
            }
            else{
                if(this.click==2){
                }
            }
        }

        repaint();
    }
    private void formMousePressed(MouseEvent e) {
        if(this.click==3){
            this.opcion_click(-1);//boton no presionado
        }
    }

    private void formMouseReleased(MouseEvent e) {
        
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
        if(this.click==2){
            g.setColor(Color.red);
            g.drawRect(this.punto_x,this.punto_y,50,50);
//            g.drawString("QF"+this.contEstado, this.punto_x+15, this.punto_y+30);
            opcion_click(-1);
        }
        if(this.click==3){
            while(this.click==3){
                g.setColor(Color.black);
                g.drawLine(0,0,40,40);

            }
        }
    }

    public void volverADibujar(Graphics g){
        int i;
        Posicion pos =new Posicion();
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
    }



}
