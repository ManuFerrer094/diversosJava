/*
    SPACE INVADERS
*/

package spaceinvaders;

// IMPORTACION DE LIBRERIAS
import static com.sun.java.accessibility.util.AWTEventMonitor.addKeyListener;
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



@SuppressWarnings({ "unused", "serial" })
// CLASE PRINCIPAL
public class SpaceInvaders implements Runnable , KeyListener  {
    
    // Ancho y Alto del Canvas
    int WIDTH = 512;
    int HEIGHT = 512;
    
    // Variables de Imagenes
    private BufferedImage imgJugador;
    private BufferedImage imgEnemigo;
    private BufferedImage imgFondo;
    
    JFrame frame;                                                               // Creacion de un marco sobre el que trabajar
    Canvas canvas;                                                              // Creacion de un canvas
    BufferStrategy bufferStrategy;                                              // Creacion de una estrategia de buffer
    
    //CONDICIONES INICIALES Declaracion de Variables
    //Posicion del jugador 
    int miposx = 225;
    int miposy = 450;
    String movimientopersonaje = "cucu";

    //Balas
    int[] balaposx; //declaramos los array de las posiciones de las balas
    int[] balaposy;
    int balasmax = 0; // ponemos a 0 el contador de balas disparadas;
    int balasmin = 0; //contador de balas dentro del terreno de juego
    boolean[] balaviva; // variable bool para activar las balas
    int balastiempo = 30; // contador de tiempo para el lanzamiento de balas

    //Balas enemigos
    int[] balaenemigoposx; //declaramos los array de las posiciones de las balas
    int[] balaenemigoposy;
    int balaenemigomax = 0; // ponemos a 0 el contador de balas disparadas;
    int balaenemigomin = 0; //contador de balas dentro del terreno de juego
    boolean[] balaenemigoviva; // variable bool para activar las balas
    int balasenemigotiempo = 0; // contador de tiempo para el lanzamiento de balas
    
    //Enemigos
    public int[] enemigoposx; // almacenan la posicion x ,y de cada uno de los enemigos
    
    
    
    int enemigoposy = 25; // y común ya que se mueven en bloque
    int enemigos = 5; // número de enemigos con los que se inicia el nivel 
    int hasganado = (enemigos+1); // contador para saber cuando se han eliminado a todos los enemigos

    boolean[] enemigovivo; //almacena si el enemigo está vivo o no
    String movimientoenemigos = "derecha";  // se inicia la variable de la direccion de movimiento usada en movimientoEnemigo()
    
        
    

    // METODO CONSRUCTOR
    public SpaceInvaders (){
        
        // Creamos marco de ventana
        frame = new JFrame("Space Invaders");                                   // Creamos una ventana
        //frame.setFocusable(true);
        // Creamos panel
        JPanel panel = (JPanel) frame.getContentPane();                         // Creamos un nuevo panel dentro del marco
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));                   // Introducimos propiedades del panel
        panel.setLayout(null);                                                  // Inicializamos el layout
        panel.setFocusable(true);                                               // Permitimos que podamos hacer focus
       
        panel.requestFocusInWindow();
        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                System.out.println("Hola");
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                 int key;
                key = ke.getKeyCode();

            if(key == KeyEvent.VK_UP){System.out.println("arriba");};

            if(key == KeyEvent.VK_RIGHT){System.out.println("derecha");};

            if(key == KeyEvent.VK_LEFT){System.out.println("izquierda");};

            if(key == KeyEvent.VK_DOWN){System.out.println("derecha");};
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            
        });
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);                   // Cerrar el proceso al salir del programa
        frame.pack();                                                           // Empaquetamos
        frame.setResizable(false);                                              // No permitimos el reescalado
        frame.setVisible(true);                                                 // Hacemos que sea visible
        
        //Crear Canvas
        canvas = new Canvas();                                                  // Creamos un nuevo canvas
        canvas.setBounds(0, 0, WIDTH, HEIGHT);                                  // Especificamos las proporciones del canvas
        canvas.setIgnoreRepaint(true);                                        // Ignoramos la repintura
        panel.add(canvas);                                                      // Añadimos el canvas al panel
        
        canvas.createBufferStrategy(2);                                         // Creamos una estrategia de buffer
        bufferStrategy = canvas.getBufferStrategy();                            // Asignamos al canvas
        canvas.requestFocus();                                                  // Requerimos el foco del raton
        
        
        //Cargamos las imagenes
        String imagePath = "C:\\Users\\A1P9\\Downloads\\SpaceInvaders\\SpaceInvaders\\src\\spaceinvaders\\";
        try {
            imgJugador=ImageIO.read(new File(imagePath+"jugador.png"));
            imgEnemigo=ImageIO.read(new File(imagePath+"enemigo.png"));
            imgFondo=ImageIO.read(new File(imagePath+"fondo_estrellado.jpg"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        //Inicializamos las variables y arrays
        enemigoposx = new int[10]; 
        enemigoposx[0] = 25;
        enemigoposx[1] = 75;
        enemigoposx[2] = 125;
        enemigoposx[3] = 175;
        enemigoposx[4] = 225;
        enemigoposx[5] = 275;

        enemigovivo = new boolean[enemigos]; 
        for(int i=0; i<=(enemigos-1); i++){ //inicializa todos en true
           
            enemigovivo[i] = true;
        }
        // Inicializaciones
        balaenemigoviva = new boolean[1000];
        balaenemigoposx = new int[1000];
        balaenemigoposy = new int[1000];
        //enemigovivo = new boolean[enemigos];
    }
    
    long desiredFPS = 100;                                                      // Especificamos los fps
    long desiredDeltaLoop = (1000*1000*1000)/desiredFPS;                        // Calculamos los milisegundos de tasa de refresco
    boolean running = true;                                                     // Variable para comprobar si esta en funcionamiento
    
    
    //Funcion de LOOP
    public void run(){
        long beginLoopTime;
        long endLoopTime;
        long currentUpdateTime = System.nanoTime();
        long lastUpdateTime;
        long deltaLoop;
        while(running){                                                         // Mientras sea cierto que se esta ejecutando
            beginLoopTime = System.nanoTime();                                  // Asigna el nano tiempo del sistema
            render();                                                           // Llamada el metodo de render
            
            lastUpdateTime = currentUpdateTime;                                 // Actualizacion de la variable
            currentUpdateTime = System.nanoTime();                              // Asignación de un nuevo tiempo
            update((int) ((currentUpdateTime - lastUpdateTime)/(1000*1000)));   // Llamada al metodo de actualizacion

            endLoopTime = System.nanoTime();
            deltaLoop = endLoopTime - beginLoopTime;
           
            if(deltaLoop > desiredDeltaLoop) {                                  // Si lo que has tardado en computar es mayor que los FPS
                //System.out.println("No llego");
            }else {
                try{
                    Thread.sleep((desiredDeltaLoop - deltaLoop)/(1000*1000));   // Para la ejecicion (como el SetTimeout)
                }catch(InterruptedException e){
                   //Do nothing
                }
            }
        }
    }
    
    
    //Funcion de RENDER
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();           // Crea un nuevo grafico 2D
        g.clearRect(0, 0, WIDTH, HEIGHT);                                       // Borra el grafico
        render(g);                                                              // Llamada al metodo protegido render
        g.dispose();                                                            // Vacia la memoria
        bufferStrategy.show();                                                  // Muestra el buffer
       // System.out.println("Entro en render");
    }
    
    
    //Funcion de ACTUALIZACION
    private double x = 0;                                                       // Creamos una variable para el desplazamiento de las cajas
    protected void update(int deltaTime){                                       // Funcion para actualizar la posicion de las cajas
        x += deltaTime * 0.2;                                                   // Avance en x
        while(x > 500){                                                         // Siempre que la x sea mayor que 500
            x -= 500;                                                           // Reinicia la x
        }
    }
    
    
    //Funcion RENDER GRAPHICS
    protected void render(Graphics2D g){                                        // Metodo que realmente dibuja
        //Dibujar Fondo de Pantalla        
        
        
        //System.out.println("Entro en render(graphics2d g)");
        
        hasPerdido();
	movimientoEnemigo();
	gestionBalas(); 
	balasAliens();
	matarAlien();
        //System.out.println("El movimiento personaje es: "+movimientopersonaje);
	movimientoJugador(); // funcion para dibjugar el movimiento del jugador dentro de los límites
        g.drawImage(imgFondo, 0,0, null);
        g.drawImage(imgJugador, miposx, miposy, null);
        balastiempo++; // aumenta el contador de tiempo para el próximo lanzamiento
	balasenemigotiempo++; // aumenta el contador de tiempo para el próximo lanzamiento
    }
   
    public static void main(String [] args){                                    // Metodo principal
        SpaceInvaders ex = new SpaceInvaders();                                 // Instancia del metodo principal
        new Thread(ex).start();                                                 // Arranque de una uneva tarea
    }
    
    
    //MOVIMIENTO DEL JUGADOR
    public void movimientoJugador(){
            if(movimientopersonaje.equals("derecha")){
                miposx += 3;
            }
            if(movimientopersonaje.equals("izquierda")){
                miposx -= 3;
            }
            //Controlar limites de movimiento
            if(miposx > WIDTH-10){
                miposx = WIDTH-10;
            }
            if(miposx < 10){
                miposx = 10;
            }
            
            //Dibuja la imagen del jugador
            
    }

    //Balas Jugador
    public void gestionBalas(){
        //Comprobamos todas las balas creadas
        for(int i = balasmin; i<balasmax; i++){
            balaposy[i] -= 3;

            //Dibujamos las balas si estan dentro del canvas
            if(balaposy[i] > 0) {  
                if(balaviva[i]){
                    //DIBUJAR BALA
                    //g.setColor(Color.WHITE); 
                    //g.fillRect(balaposx[i], balaposy[i], 2, 2);
                }
            }else {
                balasmin++; // si no entra dentro del contenedor, no se vuelve a intentar dibujar ya que su posicion sera menor a 0
            }
        }
    }

    //Balas Enemigas
    public void balasAliens(){
         
        if(balasenemigotiempo == 35){
            int random = (int)Math.floor(Math.random()*(enemigos));           // Selecciona un enemigo al azar
            balasenemigotiempo = 0;                                             // Reinicia contador de tiempo de balas
            if(enemigovivo[random]){
                // Activa la bala y le damos posicion
                balaenemigoviva[balaenemigomax] = true;
                balaenemigoposx[balaenemigomax] = enemigoposx[random];
                balaenemigoposy[balaenemigomax] = enemigoposy + 50;
                balaenemigomax++;                                               // Aumenta en uno el contador de balas 
            }
        }
        for(int i=balaenemigomin; i<balaenemigomax; i++) {
            balaenemigoposy[i] += 3;
            if(balaenemigoposy[i]<470){
                if(balaenemigoviva[i]){
                    //g.setColor(Color.GREEN); 
                    //g.fillRect(balaenemigoposx[i], balaenemigoposy[i], 2, 2);
                } else {
                    balasmin++;
                }
            }
        }
    }
    
    //Movimiento Enemigo
    public void movimientoEnemigo(){
        //$("#enemigos").html(""); // limpia el html

        //Si los enemigos llegan a la izquierda cambian de direccion y bajan una linea
        if(enemigoposx[0] <= 10) {
            movimientoenemigos = "derecha";
            enemigoposy += 45;
        }
        //Si los enemigos llegan a la derecha cambian de direccion y bajan una linea
        if(enemigoposx[enemigos] >= 440) {
            movimientoenemigos = "izquierda";
            enemigoposy += 45;
        }

        if(movimientoenemigos.equals("derecha")){                                // Mueve todos los aliens a la derecha
            for(int i=0; i<=enemigos; i++){
                enemigoposx[i]+=3;
            }
        }
        if(movimientoenemigos.equals("izquierda")){                              // Mueve todos los aliens a la izquierda
            for(int i = 0;i <= enemigos;i++){
                enemigoposx[i]-=3;
            }
        }
        //Dibujamos a los enemigos
        for(int i=0; i<=enemigos-1; i++){
            if(enemigovivo[i]){
                //g.drawImage(imgEnemigo, enemigoposx[i], enemigoposy, null);
            }
        }
    }

    //Alien toca al jugador
    public void hasPerdido(){
        // Si el enemigo me pasa o me toca, muero
        for(int i=0; i<=enemigos-1; i++){
            if(enemigovivo[i]) {
                if(enemigoposy >= miposy){
                    enemigovivo[i] = false;
                    // Has perdido
                   // System.out.println("HAS PERDIDO");
                }
                // Si el enemigo choca conmigo muero
                if(Math.abs(enemigoposx[i]-(miposx+25)) < 25 && Math.abs(enemigoposy-miposy) < 25) {
                    enemigovivo[i] = false;
                    //Has perdido
                    //System.out.println("HAS PERDIDO");
                }
            }
        }
        // Si la bala disparada por el enemigo choca conmigo, muero
        for(int i=balaenemigomin; i<balaenemigomax; i++) {
            if(Math.abs((balaenemigoposx[i]+2)-(miposx+22))<25 && Math.abs((miposy+22)-(balaenemigoposy[i]+10))<25){
                if(balaenemigoviva[i]){
                    //System.out.println("HAS PERDIDO");
                    //Has perdido
                }
            }
        }
    }

    //Matar un Alien
    public void matarAlien(){
        for(int i=balasmin; i<balasmax; i++){                                   // Recorre todas las balas disparadas activas
            for(int j=0; j<=enemigos; j++){                                     // Recorre todos los enemigos
                if(Math.abs(balaposx[i]-(enemigoposx[j]+22))<25 && Math.abs((enemigoposy+22)-balaposy[i])<25){
                    // Si la bala choca con un enemigo y ambos estan activos...
                    if(enemigovivo[j] && balaviva[i]){
                        enemigovivo[j] = false;                                 // Muere el enemigo
                        balaviva[i] = false;                                    // muere la bala
                        hasganado--;                                            // resta uno al contador para saber si estan todos muertos
                        if(hasganado == 0){
                            //System.out.println("HAS GANADO");
                            //alert("Has ganado");                                // si todos han muerto he ganado
                        }
                    } else {
                        //console.log("El enemigo ya estaba muerto");
                        //System.out.println("El enemigo ya estaba muerto");
                    }
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
}

   
