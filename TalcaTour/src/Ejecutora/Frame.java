package Ejecutora;
/**
 *
 * @author marza
 */
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

public class Frame extends JFrame{

    public AreaMapa am = new AreaMapa();



    public Frame(){

        getContentPane().setLayout(new FlowLayout());

        add(am);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
	setTitle("Talca Tour");
        setResizable(true);
    }


}
