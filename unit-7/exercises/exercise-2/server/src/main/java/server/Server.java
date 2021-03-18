package server;

import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static final int PORT = 8000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Listening on port " + PORT + "...");

            ExecutorService executorService = Executors.newCachedThreadPool();

            while (true) {
                executorService.execute(new ServerTask(serverSocket.accept()));
                System.out.println("Incoming connection accepted.");
            }
        } catch (Exception e) {
            System.out.println("Error listening on port " + PORT + ".");
        }
    }

}
