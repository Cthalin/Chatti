package chatti;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static int portNumber = 5000;
    private static PrintWriter writer;
    private static BufferedReader reader;

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket;
        ExecutorService executor = Executors.newFixedThreadPool(30);

        try {

            serverSocket = new ServerSocket(portNumber);
            System.out.println("Server started");

            while (true) {

                try {
                    Socket client = serverSocket.accept();

                    executor.execute(new ClientThread(client));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }catch (IOException e1){
            e1.printStackTrace();
        }

    }
}
