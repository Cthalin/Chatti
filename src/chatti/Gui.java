package chatti;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class Gui {

    private JFrame frame;

    public static void main(String[] args){

        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {

                try{
                    Gui window = new Gui();
                    window.frame.setVisible(true);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        });

    }

    public Gui(){

        initialize();

    }

    private void initialize() {

        frame = new JFrame();
        frame.getContentPane().setFont(new Font("Calibri", Font.PLAIN, 12));
        frame.setFont(new Font("Calibri", Font.PLAIN, 12));
        frame.getContentPane().setBackground(new Color(255, 248, 220));
        frame.setBounds(100, 100, 800, 601);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JScrollPane scrollPane_IncomingMessages = new JScrollPane();
        scrollPane_IncomingMessages.setBorder(new LineBorder(new Color(128, 0, 0)));
        scrollPane_IncomingMessages.setBounds(6, 6, 627, 497);
        frame.getContentPane().add(scrollPane_IncomingMessages);

        JTextArea txtrEingehendeNachrichten = new JTextArea();
        txtrEingehendeNachrichten.setFont(new Font("Calibri", Font.PLAIN, 12));
        txtrEingehendeNachrichten.setText("Incoming Messages");
        txtrEingehendeNachrichten.setBorder(null);
        txtrEingehendeNachrichten.setLineWrap(true);
        scrollPane_IncomingMessages.setViewportView(txtrEingehendeNachrichten);

        JList listUser = new JList();
        listUser.setFont(new Font("Calibri", Font.PLAIN, 12));
        listUser.setToolTipText("Userlist");
        listUser.setBorder(new LineBorder(new Color(139, 0 ,0)));
        listUser.setBounds(647, 6, 131, 497);
        frame.getContentPane().add(listUser);

        JButton btnAbschicken = new JButton("Send");
        btnAbschicken.setBorder(new LineBorder(new Color(128, 0 , 0)));
        btnAbschicken.setToolTipText("Send Message");
        btnAbschicken.setBounds(647, 515, 131, 42);
        frame.getContentPane().add(btnAbschicken);

        JScrollPane scrollPane_Messages = new JScrollPane();
        scrollPane_Messages.setBorder(new LineBorder(new Color(128, 0, 0)));
        scrollPane_Messages.setBounds(6, 517, 627, 40);
        frame.getContentPane().add(scrollPane_Messages);

        JTextArea txtrDeineNachricht = new JTextArea();
        txtrDeineNachricht.setFont(new Font("Calibri", Font.PLAIN, 12));
        txtrDeineNachricht.setText("Your Message");
        txtrDeineNachricht.setBorder(null);
        txtrDeineNachricht.setToolTipText("Put in your message here...");
        txtrDeineNachricht.setLineWrap(true);
        scrollPane_Messages.setViewportView(txtrDeineNachricht);

    }

}