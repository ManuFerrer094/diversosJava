
package proyecto13;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.bind.DatatypeConverter;

public class Proyecto13 {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        System.out.println("Dime en que puerto quieres que ejecute el servidor:");
        Scanner puerto;
        puerto = new Scanner(System.in);
        String stringpuerto;
        stringpuerto = puerto.next();
        int numeropuerto = Integer.parseInt(stringpuerto);
         ServerSocket server = new ServerSocket(numeropuerto);
        System.out.println("Server has started on 127.0.0.1:"+numeropuerto+".\r\nWaiting for a connection...");
        Socket client = server.accept();
        System.out.println("A client connected.");
        InputStream in = client.getInputStream();
        OutputStream out = client.getOutputStream();
        
        String data = new Scanner(in,"UTF-8").useDelimiter("\\r\\n\\r\\n").next();
        Matcher get = Pattern.compile("^GET").matcher(data);

        if (get.find()) {
             Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
                match.find();
                byte[] response = ("HTTP/1.1 101 Switching Protocols\r\n"
                        + "Connection: Upgrade\r\n"
                        + "Upgrade: websocket\r\n"
                        + "Sec-WebSocket-Accept: "
                        + DatatypeConverter
                        .printBase64Binary(
                                MessageDigest
                                .getInstance("SHA-1")
                                .digest((match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
                                        .getBytes("UTF-8")))
                        + "\r\n\r\n")
                        .getBytes("UTF-8");

                out.write(response, 0, response.length);  
                System.out.println("El sistema dice: "+response.toString());
                
                byte[] decoded = new byte[6];
                byte[] encoded = response;
                
                byte[] key = new byte[4];
                key[0] = response[2];
                key[1] = response[3];
                key[2] = response[4];
                key[3] = response[5];

                for (int i = 0; i < 2; i++) {
                    decoded[i] = (byte)(encoded[i] ^ key[i & 0x3]);
                }
                System.out.println("y es: "+decoded.toString());
        } else {

        }
    }
    
}
