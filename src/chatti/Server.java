package chatti;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Server {

    private static int portNumber = 5000;
    private static PrintWriter writer;
    private static BufferedReader reader;

    ServerSocket server;
    ArrayList<PrintWriter> list_clientWriter;

    final int LEVEL_ERROR = 1;
    final int LEVEL_NORMAL = 0;

    private JFrame serverFrame;
    private JPanel serverPanel;
    private JTextArea textArea_status;

    public static void main(String[] args) {

        Server s = new Server();
        s.createGui();
        if(s.runServer()){
            s.listenToClients();
        } else {
            //do nothing
        }
    }

    public class ClientHandler implements Runnable {

        Socket client;
        BufferedReader reader;

        public ClientHandler(Socket client) {
            try {
                this.client = client;
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            String message;

            try {
                while ((message = reader.readLine()) != null) {
                    appendTextToConsole("From Client: \n" + message, LEVEL_NORMAL);
                    sendToAllClients(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

        public void listenToClients(){
            while(true){
                try{
                    Socket client = server.accept();

                    PrintWriter writer = new PrintWriter(client.getOutputStream());
                    list_clientWriter.add(writer);

                    Thread clientThread = new Thread(new ClientHandler(client));
                    clientThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public boolean runServer(){
            try{
                server = new ServerSocket(portNumber);
                appendTextToConsole("Server started on port "+portNumber, LEVEL_ERROR);

                list_clientWriter = new ArrayList<PrintWriter>();
                return true;
            } catch (IOException e) {
                appendTextToConsole("Server couldnt be started!", LEVEL_ERROR);
                e.printStackTrace();
                return false;
            }
        }

        public void appendTextToConsole(String message, int level){
            if(level == LEVEL_ERROR) {
                System.err.println(message+"\n");
            }
            else {
                System.out.println(message+"\n");
            }
        }

        public void sendToAllClients(String message){
            Iterator it = list_clientWriter.iterator();

            while (it.hasNext()){
                PrintWriter writer = (PrintWriter) it.next();
                writer.print(message+"\n");
                writer.flush();
            }
        }

        public void createGui(){
            serverFrame = new JFrame("Chatti Server");
            serverFrame.setSize(300,80);
            serverPanel = new JPanel();
            textArea_status = new JTextArea();
            textArea_status.setEditable(false);
            textArea_status.append("Server running on port "+portNumber);
            serverPanel.add(textArea_status);

            //if(runServer()){serverFrame.setTitle("Chatti Server - running...");}

            serverFrame.getContentPane().add(BorderLayout.CENTER,serverPanel);
            serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            serverFrame.setVisible(true);
        }
}
