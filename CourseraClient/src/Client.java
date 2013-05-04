import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Client {
    
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 7777);
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            BufferedReader users = new BufferedReader(
                    new InputStreamReader(
                            System.in));
            String line = null;
            while((line = users.readLine()) != null) { 
                out.println(line);
                System.out.println(in.readLine());
            }
            users.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
