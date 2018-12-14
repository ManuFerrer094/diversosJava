
package proyecto4;
import java.io.*;           // Lo quiero todo de la libreria de entrada y salida
import java.util.*;         // Lo quiero todo de la librería de útiles

public class Proyecto4 {
    
    public static void main(String[] args) {
        try {
            System.out.print("Enter the file name with extension : ");

            Scanner input = new Scanner("C://java//miarchivo.txt");
            File file = new File(input.nextLine());
            input = new Scanner(file);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(line);
            }
            input.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        
    }
    
}
