import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.net.SocketFactory;


public class Server {
    
    public static final int PORT = 7777;
    
    private static InitialDispatcher dispatcher;
    
    public static void main(String[] args) {
        
        EchoHandler handler = new EchoHandler();
        
        while (true) {
            dispatcher = InitialDispatcher.instance();
            dispatcher.handleEvents();
        }
        
        
        try {
            ServerSocket socket = new ServerSocket(PORT);
            Socket client = socket.accept();
            
            PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
            
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            client.getInputStream()));
            String line = null; 
            while ((line = reader.readLine()) != null) {
                writer.println("Server tells: " + line); // change on printline
            }
            client.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public interface EventHandler {
        public void handleEvent(String host, int port) throws IOException;
    }
    
    public class EchoHandler implements EventHandler {

        private SocketFactory factory;
        
        public EchoHandler() {
            InitialDispatcher.instance.registerEvent(this);
        }
        
        @Override
        public void handleEvent(String host, int port) throws IOException {
            Socket socket = factory.createSocket(host, port);
            // TODO process call
        }
        
    }
    
    public static class InitialDispatcher {
        
        private static InitialDispatcher instance;
        
        private ArrayList<EventHandler> events = new ArrayList<EventHandler>();
        
        private InitialDispatcher() {
            
        }
        
        public static InitialDispatcher instance() {
            return instance == null ? new InitialDispatcher() : instance;
        }
        
        public void registerEvent(EventHandler event) {
            events.add(event);
        }
        
        public void unregisterEvent(EventHandler event) {
            // TODO complete me
        }
        
        public int handleEvents() {
            return -1;
        }
    }

}
