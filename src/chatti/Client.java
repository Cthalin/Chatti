package chatti;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private JFrame clientFrame;
    private JPanel clientPanel;
    private JTextArea textArea_Messages;
    private JTextField textField_ClientMessage;
    private JButton button_SendMessage;
    private JTextField textField_Username;
    private JScrollPane scrollPane_Messages;

    private Socket client;
    private PrintWriter writer;
    private BufferedReader reader;

    private int portNumber = 5000;
    private String serverAddress = "127.0.0.1";

    public static void main(String[] args) {
        Client c = new Client();
        c.createGUI();
    }

    public void createGUI() {
        clientFrame = new JFrame("Chatti - Not Connected");
        clientFrame.setSize(800, 600);

        // Panel erzeugen, welches alle anderen Inhalte enthält
        clientPanel = new JPanel();

        textArea_Messages = new JTextArea();
        textArea_Messages.setEditable(false);

        textField_ClientMessage = new JTextField(38);
        textField_ClientMessage.addKeyListener(new SendPressEnterListener());

        button_SendMessage = new JButton("Send");
        button_SendMessage.addActionListener(new SendButtonListener());

        textField_Username = new JTextField(10);

        // Scrollbalken zur textArea hinzufügen
        scrollPane_Messages = new JScrollPane(textArea_Messages);
        scrollPane_Messages.setPreferredSize(new Dimension(700, 500));
        scrollPane_Messages.setMinimumSize(new Dimension(700, 500));
        scrollPane_Messages.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_Messages.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);


        if(connectToServer()) {
            clientFrame.setTitle("Chatti - Connected");
        }

        Thread t = new Thread(new MessagesFromServerListener());
        t.start();

        clientPanel.add(scrollPane_Messages);
        clientPanel.add(textField_Username);
        clientPanel.add(textField_ClientMessage);
        clientPanel.add(button_SendMessage);

        // Panel zum ContentPane (Inhaltsbereich) hinzufügen
        clientFrame.getContentPane().add(BorderLayout.CENTER, clientPanel);

        clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientFrame.setVisible(true);
    }

    public boolean connectToServer() {
        try {
            client = new Socket(serverAddress, portNumber);
            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            writer = new PrintWriter(client.getOutputStream());
            appendTextMessages("Connection established");

            return true;
        } catch(Exception e) {
            appendTextMessages("Could not connect to server");
            e.printStackTrace();

            return false;
        }
    }

    public void sendMessageToServer() {
        writer.println(textField_Username.getText() + ": " + textField_ClientMessage.getText());
        writer.flush();

        textField_ClientMessage.setText("");
        textField_ClientMessage.requestFocus();
    }

    public void appendTextMessages(String message) {
        textArea_Messages.append(message + "\n");
    }

    // Listener
    public class SendPressEnterListener implements KeyListener {

        @Override
        public void keyPressed(KeyEvent arg0) {
            if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                sendMessageToServer();
            }
        }

        @Override
        public void keyReleased(KeyEvent arg0) {}

        @Override
        public void keyTyped(KeyEvent arg0) {}

    }

    public class SendButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            sendMessageToServer();
        }

    }

    public class MessagesFromServerListener implements Runnable {

        @Override
        public void run() {
            String message;

            try {
                while((message = reader.readLine()) != null) {
                    appendTextMessages(message);
                    textArea_Messages.setCaretPosition(textArea_Messages.getText().length());
                }
            } catch (IOException e) {
                appendTextMessages("Message could not be received!");
                e.printStackTrace();
            }
        }

    }
}