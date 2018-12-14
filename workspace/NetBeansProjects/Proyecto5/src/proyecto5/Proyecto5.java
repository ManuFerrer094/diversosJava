
package proyecto5;

import java.io.File;

public class Proyecto5 {
    public static void main(String args[]) {
        File folder = new File("C://java//");                       // Esto es una instancia de un file que en este caso se usa como folder
        File[] listOfFiles = folder.listFiles();                    // Creo un array de un listado de archivos
        for (File file : listOfFiles) {                             // Recorro el listado de archivos
            if (file.isFile()) {                                    // Si lo que me encuentro es un archivo
                System.out.println(file.getName());                 // Sacamelo por pantalla
            }
        }  
    } 
}
