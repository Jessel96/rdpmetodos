package control;

import control.AreaDibujo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Frame extends JFrame{

    public AreaDibujo  ad= new AreaDibujo();
    static JPanel p1=new JPanel();
    JButton plaza = new JButton("Plaza");
    JButton transicion = new JButton("Transición");
    JButton arco = new JButton("Arco");
    JButton paso = new JButton("Paso a Paso");
    JButton matrices = new JButton("Generar Matrices");

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
        paso.setName("Paso a Paso");
        paso.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        paso.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        paso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ad.opcion_click(4);
            }
        });

        matrices.setFocusable(false);
        matrices.setName("Generar Matrices");
        matrices.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        matrices.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        matrices.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ad.opcion_click(5);
            }
        });

        p1.setLayout(new FlowLayout());

        p1.add(plaza);
        p1.add(transicion);
        p1.add(arco);
        p1.add(paso);
        p1.add(matrices);
        add(p1);
        add(ad);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	setTitle("Red de Petri");
        setResizable(false);
    }
    
}
