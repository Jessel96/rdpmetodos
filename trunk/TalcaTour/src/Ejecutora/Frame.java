package Ejecutora;
/**
 *
 * @author marza
 */
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Frame extends JFrame{

    public AreaMapa am = new AreaMapa();
    JButton recorrido = new JButton("Trazar Ruta");
    JButton limpiar = new JButton("Limpiar Mapa");

    public Frame(){

        getContentPane().setLayout(new FlowLayout());

        recorrido.setFocusable(false);
        recorrido.setName("Trazar Ruta");
        recorrido.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        recorrido.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        recorrido.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                am.setflag(1);
                repaint();
            }
        });
        limpiar.setFocusable(false);
        limpiar.setName("Trazar Ruta");
        limpiar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        limpiar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        limpiar.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                am.setflag(0);
                repaint();
            }
        });

        add(am);
        add(recorrido);
        add(limpiar);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	setTitle("Talca Tour");
        setResizable(true);
    }


}
