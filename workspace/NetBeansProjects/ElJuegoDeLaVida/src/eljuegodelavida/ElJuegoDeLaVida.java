
package eljuegodelavida;

import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import javax.swing.JFrame;
import javax.swing.JPanel;
import static oracle.jrockit.jfr.events.Bits.intValue;

public class ElJuegoDeLaVida  implements Runnable {
//   DECLARACION DE VARIABLES INICIALES
    
   int WIDTH = 2048;                 // Anchura del canvas
   int HEIGHT = 2048;                // Altura del canvas
   
   private BufferedImage im;        // Imagen qe vamos a cargar
   
   JFrame frame;                    // Creación de un marco sobre el que trabajar
   Canvas canvas;                   // Creación de un canvas
   BufferStrategy bufferStrategy;   // Creación de una estrategia de buffer
   
   int pikax = 200;
   int pikay = 200;
   
   int tamaniocelda = 8;
   int numerototal = (WIDTH/tamaniocelda)*(HEIGHT/tamaniocelda);
      int activo[] = new int[numerototal];
     
      
    public ElJuegoDeLaVida (){ // Metodo constructor
       
       int contador = 0;
       // Hago un bucle for inicial y digo que hay un umbral que en mi caso es de 
       for(int x = 0;x<(WIDTH/tamaniocelda*HEIGHT/tamaniocelda);x++){
            if(Math.random() > 0.9){
                activo[contador] = 1;
            }else{
                activo[contador] = 0;
            }
                
               contador++;
       }
      frame = new JFrame("El Juego de la Vida - Jose Vicente Carratala");       // Creamos una nueva instancia
      
      JPanel panel = (JPanel) frame.getContentPane();                           // Creamos un nuevo panel dentro del marco
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));                     // Introducimos propiedades del panel
      panel.setLayout(null);                                                    // Inicializamos el layout
      panel.setFocusable(true);                                                 // Permitimos que podamos hacer focus
      
      canvas = new Canvas();                                                    // Creamos un nuevo canvas
      canvas.setBounds(0, 0, WIDTH, HEIGHT);                                    // Especificamos las proporciones del canvas
      canvas.setIgnoreRepaint(true);                                            // Ignoramos la repintura
      
      panel.add(canvas);                                                        // Añadimos el canvas al panel
      
      
      
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar el proceso al salir del programa
      frame.pack();                                         // empaquetamos
      frame.setResizable(false);                            // Permitimos el reescalado
      frame.setVisible(true);                               // Hacemos que sea visible
      
      canvas.createBufferStrategy(2);                       // Creamos una estrategia de buffer
      bufferStrategy = canvas.getBufferStrategy();          // Asignamos al canvas
      
      canvas.requestFocus();                                // Requerimos el foco del raton
      // Sobre la variable anteriormente mencionada, cargamos la imagen
      try {
        im=ImageIO.read(new File("C:\\java\\canica.png"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
      
      
		
   }
    
    long desiredFPS = 100;                                    // Especificamos los fps
    long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;    // Calculamos los milisegundos de tasa de refresco
    
   boolean running = true;                                  // Variable para comprobar si esta en funcionamiento
   
   public void run(){                                       // Funcion de ejecucion de loop
      
      long beginLoopTime;                                                                                
      long endLoopTime;
      long currentUpdateTime = System.nanoTime();
      long lastUpdateTime;
      long deltaLoop;
      
      while(running){                                                           // Mientras sea cierto que se esta ejecutando
         beginLoopTime = System.nanoTime();                                     // Asigna el nano tiempo del sistema
         
         render();                                                              // Llamada el metodo de render
         
         lastUpdateTime = currentUpdateTime;                                    // Actualizacion de la variable
         currentUpdateTime = System.nanoTime();                                 // Asignación de un nuevo tiempo
         update((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));      // Llamada al metodo de actualizacion
         
         endLoopTime = System.nanoTime();
         deltaLoop = endLoopTime - beginLoopTime;
           
           if(deltaLoop > desiredDeltaLoop){ // Si lo que has tardado en computar es mayor que los FPS
               // En ese caso no hagas nada
               System.out.println("No llego");
           }else{   // En caso contrario
               try{
                   Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000)); // Para la ejecicion (como el SetTimeout)
               }catch(InterruptedException e){
                   //Do nothing
               }
           }
      }
   }
   
   private void render() {
      Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();             // Crea un nuevo grafico 2D
      g.clearRect(0, 0, WIDTH, HEIGHT);                                         // Borra el grafico
      render(g);                                                                // llamada al metodo protegido render
      g.dispose();                                                              // Vacia la memoria
      bufferStrategy.show();                                                    // Muestra el buffer
   }

   private double x = 0;                                                        // Creamos una variable para el desplazamiento de las cajas
  
   protected void update(int deltaTime){                                        // Funcion para actualizar la posicion de las cajas
      x += deltaTime * 0.2;                                                     // Avance en x
      while(x > 500){                                                           // Siempre que la x sea mayor que 500
         x -= 500;                                                              // Reinicia la x
      }
   }
   
   
   protected void render(Graphics2D g){                                         // Metodo que realmente dibuja
     //System.out.println("fotograma");
      int arribaizquierda;
                int arriba; 
                int arribaderecha;
                int izquierda;
                int derecha;
                int abajoizquierda;
                int abajo; 
                int abajoderecha;
                
                int contadortemporal = 0;
                
      int contador = 0;
      g.setColor(Color.BLACK); 
      // Me recorro todas las casillas del juego en X y en Y
       for(int x = 0;x<WIDTH/tamaniocelda;x++){
           for(int y = 0;y<HEIGHT/tamaniocelda;y++){
               // Si una particula no tiene a ninguna otra particula vecina, se muere de hambre
               // Si una particula esta rodeada por muchas particulas, se muere de sobrepoblacion
               
                arribaizquierda = contador-(WIDTH/tamaniocelda)-1;              // Vivo de arriba a la izquierda
                arriba = contador-(WIDTH/tamaniocelda);                         // Vivo de arriba 
                arribaderecha = contador-(WIDTH/tamaniocelda)+1;                // Vivo de arriba derecha
                izquierda = contador-1;                                         // Vivo de izquierda
                derecha = contador+1;                                           // Vivo de derecha
                abajoizquierda = contador+(WIDTH/tamaniocelda)-1;               // Vivo de arriba a la izquierda
                abajo = contador+(WIDTH/tamaniocelda);                          // Vivo de arriba 
                abajoderecha = contador+(WIDTH/tamaniocelda)+1;                 // Vivo de arriba derecha
               
                contadortemporal = 0;                                           // Cada celula empieza con un recuento de cero
                if(contador > WIDTH/tamaniocelda){                              // No me cuentes la primera fila
                    if(contador < ((WIDTH/tamaniocelda*HEIGHT/tamaniocelda)-WIDTH/tamaniocelda)-1){ // No me cuentes la fila de abajo
                        
                                if(activo[arribaizquierda] == 1){contadortemporal++;}
                                if(activo[arriba] == 1){contadortemporal++;}
                                if(activo[arribaderecha] == 1){contadortemporal++;}
                                if(activo[izquierda] == 1){contadortemporal++;}
                                if(activo[derecha] == 1){contadortemporal++;}
                                if(activo[abajoizquierda] == 1){contadortemporal++;}
                                if(activo[abajo] == 1){contadortemporal++;}
                                if(activo[abajoderecha] == 1){contadortemporal++;}   
                    }
                }
  
                // Trata de que segun las condiciones de vecindad de las particulas, viven o mueren
                
                
                if(activo[contador] == 1){
                    //g.setColor(Color.BLACK); 
                    //g.fillRect(x*tamaniocelda, y*tamaniocelda, tamaniocelda, tamaniocelda); 
                    
                    g.drawImage(im,x*tamaniocelda,y*tamaniocelda,frame);
                   if(contadortemporal < 2){activo[contador] =0;}
                   if(contadortemporal == 2 || contadortemporal == 3){activo[contador] = 1;}
                   if(contadortemporal > 3 ){activo[contador] =0;}
                } 
                else if(activo[contador] == 0){
                    g.setColor(Color.WHITE); 
                    if(contadortemporal == 3){activo[contador] = 1;}
                    g.fillRect(x*tamaniocelda, y*tamaniocelda, tamaniocelda, tamaniocelda); 
                } 
                

                contador++;

           }
       }
      
   }
   
   public static void main(String [] args){                                     // Metodo principal
      ElJuegoDeLaVida ex = new ElJuegoDeLaVida();                                                   // Instancia del metodo principal
      new Thread(ex).start();                                                   // Arranque de una uneva tarea
   }
    
    
   //System.out.println(suit[1]);
     
     
   
    
}
