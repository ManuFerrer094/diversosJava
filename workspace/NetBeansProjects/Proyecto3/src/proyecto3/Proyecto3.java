package proyecto3;

import javax.swing.*;

public class Proyecto3 extends JFrame{

    // Variables globales en la clase (propiedades)
    private JLabel rotulo;
    
    public Proyecto3(){
        rotulo = new JLabel("Hola mundo");
        rotulo.setBounds(30,50,150,20);
        add(rotulo);
    }
    
    public static void main(String[] args) {
        Proyecto3 ventana = new Proyecto3();
        ventana.setBounds(50, 50, 300, 400);
        ventana.setVisible(true);
    }
    
}
