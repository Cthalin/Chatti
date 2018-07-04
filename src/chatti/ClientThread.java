package chatti;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {

    private Socket client;
    private static PrintWriter writer;
    private static BufferedReader reader;

    public ClientThread(Socket client){
        this.client = client;
    }

    @Override
    public void run() {
        try {
            //Streams
            OutputStream out = client.getOutputStream();
            writer = new PrintWriter(out);

            InputStream in = client.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            //-------------

            String s = null;

            while ((s = reader.readLine()) != null) {
                writer.write(s + "\n");
                writer.flush();
                System.out.println("Received: " + s);
            }

            writer.close();
            reader.close();

            client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
