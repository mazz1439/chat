package com.example.Client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ClientMain {
    private static JTextArea chatArea;
    private static JTextField inputField;
    private static PrintWriter out;

    public static void main(String[] args) {
        Socket clientSocket; // Socket per la connessione al server

        // Creazione della finestra principale
        JFrame frame = new JFrame("Chat Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Creazione dell'area della chat (non modificabile dall'utente)
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        frame.add(new JScrollPane(chatArea), BorderLayout.CENTER); // Aggiunta di una barra di scorrimento

        // Creazione del pannello inferiore con campo di input e pulsante di invio
        JPanel panel = new JPanel(new BorderLayout());
        inputField = new JTextField();
        JButton sendButton = new JButton("Invia");

        panel.add(inputField, BorderLayout.CENTER);
        panel.add(sendButton, BorderLayout.EAST);

        frame.add(panel, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        try {
            // Connessione al server in esecuzione su localhost sulla porta 5500
            clientSocket = new Socket("localhost", 5500);
            out = new PrintWriter(clientSocket.getOutputStream(), true); // Creazione del flusso di output

            // Richiesta del nome utente tramite finestra di dialogo
            String username = JOptionPane.showInputDialog("Inserisci il tuo nome utente:");
            if (username == null || username.trim().isEmpty()) {
                username = "Anonimo";
            }
            frame.setTitle("Chat Client - " + username);
            out.println(username);

            // Avvia un thread per ricevere i messaggi dal server
            Thread riceviThread = new Thread(new ThreadRicevi(clientSocket, chatArea));
            riceviThread.start();

            // Definizione dell'azione per l'invio del messaggio
            ActionListener sendAction = e -> {
                String message = inputField.getText().trim();
                if (!message.isEmpty()) {
                    out.println(message);
                    chatArea.append("Tu: " + message + "\n");
                    inputField.setText("");
                }
            };

            sendButton.addActionListener(sendAction);
            inputField.addActionListener(sendAction);

        } catch (IOException e) {
            // Messaggio di errore se la connessione al server fallisce
            JOptionPane.showMessageDialog(null, "Impossibile connettersi al server", "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
