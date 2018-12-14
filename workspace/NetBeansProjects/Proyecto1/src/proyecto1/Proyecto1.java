package proyecto1;

public class Proyecto1 {

    public static void main(String[] args) {
        System.out.println("Hola como estais esto es un programa en Java"); 
        int edad = 40;
        String nombre;
        nombre = "Jose Vicente";
        int dia;
        for(dia = 1;dia<31;dia++){
            System.out.println("Hoy es el dia "+dia+" del mes");
        }
        saluda();
        System.out.println(saludabien());
        
    }
    
    public static void saluda(){
        System.out.println("Yo te estoy saludando");
    }
    public static String saludabien(){
        return("Te estoy saludando pero desde un return");
    }
}
