package control;

import control.AreaDibujo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Frame extends JFrame{

    public AreaDibujo  ad= new AreaDibujo();
    JButton plaza = new JButton("Plaza");
    JButton transicion = new JButton("Transición");
    JButton arco = new JButton("Arco");
    JButton paso = new JButton("Paso a Paso");

     public Frame() {

        getContentPane().setLayout(new FlowLayout());

        plaza.setFocusable(false);
        plaza.setName("Agregar Estados");
        plaza.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        plaza.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        plaza.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ad.opcion_click(0);
            }
        });

        transicion.setFocusable(false);
        transicion.setName("Agregar Estados");
        transicion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        transicion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        transicion.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ad.opcion_click(1);
            }
        });
        
        arco.setFocusable(false);
        arco.setName("arco");
        arco.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        arco.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        arco.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ad.opcion_click(2);
            }
        });
        paso.setFocusable(false);
        paso.setName("Correr Maquina");
        paso.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        paso.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        paso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ad.opcion_click(3);
            }
        });


        add(plaza);
        add(transicion);
        add(arco);
        add(paso);
        add(ad);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	setTitle("Red de Petri");
        setResizable(false);
    }
    
}