package control;

import java.io.*;
import javax.swing.JTextArea;
import modelo.RDP;

public class AreaConsola extends javax.swing.JScrollPane{

    RDP redpetri;
    JTextArea jta = new JTextArea(25,25);

    public AreaConsola(RDP r){
        this.redpetri=r;
        jta.setEditable(false);
        setViewportView(jta);
    }

    public void escribeMatriz(){
        int i,j;
        jta.setText("");
        jta.append("Matriz Pre\n");
        this.redpetri.generarMatrizPre();
        this.redpetri.generarMatrizPost();
        for(i=0;i<this.redpetri.numPlazas();i++){
            for(j=0;j<this.redpetri.numTransiciones();j++){
                jta.append("["+Integer.toString(this.redpetri.retornaPre(i,j))+"]");
            }
            jta.append("\n");
        }

        jta.append("\nMatriz Post\n");
        for(i=0;i<this.redpetri.numPlazas();i++){
            for(j=0;j<this.redpetri.numTransiciones();j++){
                jta.append("["+Integer.toString(this.redpetri.retornaPost(i,j))+"]");
            }
            jta.append("\n");
        }
    }

}