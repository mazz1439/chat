package com.example.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatGUI {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;
    private PrintWriter out;
    private ThreadRicevi riceviThread;
    private String username;

    public ChatGUI(Socket socket) throws IOException {
        out = new PrintWriter(socket.getOutputStream(), true);
        riceviThread = new ThreadRicevi(socket, this);
        new Thread(riceviThread).start();

        username = JOptionPane.showInputDialog("Inserisci il tuo nome utente:");
        if (username == null || username.trim().isEmpty()) {
            username = "Anonimo";
        }
        out.println(username);
    }

    public void createAndShowGUI() {
        frame = new JFrame("Chat Client - " + username);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        JPanel panel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        sendButton = new JButton("Invia");

        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.add(panel, BorderLayout.SOUTH);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });

        frame.setVisible(true);
    }

    private void sendMessage() {
        String message = inputField.getText().trim();
        if (!message.isEmpty()) {
            out.println(message);
            appendMessage("Tu: " + message);
            inputField.setText("");
        }
    }

    public void appendMessage(String message) {
        chatArea.append(message + "\n");
    }
}
