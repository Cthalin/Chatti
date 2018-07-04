package chatti;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{

    private static int portNumber=5000;
    private static PrintWriter writer;
    private static BufferedReader reader;
    private static int a;

    public static void main(String[] args) throws IOException {

        a=1;

        new Thread(new Client()).start();
        new Thread(new Client()).start();
        new Thread(new Client()).start();
        new Thread(new Client()).start();
        new Thread(new Client()).start();

    }

    @Override
    public void run() {

        //Scanner input = new Scanner(System.in);

        try {
            Socket client = new Socket("localhost",portNumber);
            System.out.println("Client started");

            //Streams
            OutputStream out = client.getOutputStream();
            writer = new PrintWriter(out);

            InputStream in = client.getInputStream();
            reader = new BufferedReader(new InputStreamReader(in));
            //------------

            //System.out.print("Input: ");
            //String userInput = input.nextLine();

            writer.write("Hallo vom "+a+". Client"+"\n");
            writer.flush();
            a++;

            String s = null;

            while((s = reader.readLine())!=null){
                System.out.println("Received from server: "+s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            writer.close();
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
