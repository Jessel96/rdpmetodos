package control;

import java.io.*;
import javax.swing.JTextArea;
import modelo.RDP;

public class AreaConsola extends javax.swing.JScrollPane{

    RDP redpetri;
    JTextArea jta = new JTextArea(25,25);
    private int click=-1;

    public AreaConsola(RDP r){
        this.redpetri=r;
        jta.setEditable(false);
        setViewportView(jta);
    }

    public void escribeMatriz(){
        jta.setText("");
        jta.append("Hola Presionando Boton Matriz\n");
    }

}