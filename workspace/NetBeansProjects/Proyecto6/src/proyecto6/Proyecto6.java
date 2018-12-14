package proyecto6;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Proyecto6 extends JFrame implements ActionListener{
    JPanel jPanel3;
    JButton jButton6;
    JTextField jTextField7;
    public Proyecto6(){ // Este es el constructor y en el se suelen poner los elementos de la interfaz
        jPanel3 = new JPanel();
        jButton6 = new JButton();
        jTextField7 = new JTextField();
        setTitle("Directorio");
        jButton6.setText("jButton6");
        jTextField7.setText("jTextField7");
        /*/////////////////////////////////////*/
        add(jPanel3);           // A la ventana le añado el panel
        jPanel3.add(jButton6);  // Al panel le añado el boton
        jPanel3.add(jTextField7);   // Al panel le añado el campo de texto
        ActionListener al = null;
        /*/////////////////////////////////////*/
        jButton6.addActionListener(this); // Añado un escuchar al boton
    }
    public void ActionPerformed(ActionEvent e){
        //if(e.getSource() == jButton6){
            //setTitle("ok");
        //}
    }
    public static void main(String[] args) {  
        
        Proyecto6 ventana = new Proyecto6();
        ventana.setBounds(50, 50, 300, 400);
        ventana.setVisible(true);
        
    }  

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
