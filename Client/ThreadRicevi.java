package com.example.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import javax.swing.*;

public class ThreadRicevi implements Runnable {
    private final Socket socket;
    private final BufferedReader in;
    private final JTextArea chatArea;

    public ThreadRicevi(Socket socket, JTextArea chatArea) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.chatArea = chatArea;
    }

    @Override
    public void run() {
        String messaggio;

        try {
            while ((messaggio = in.readLine()) != null) { // Legge i messaggi in arrivo finché il server è attivo
                String finalMessaggio = messaggio;
                SwingUtilities.invokeLater(() -> chatArea.append(finalMessaggio + "\n")); // Aggiorna l'area di testo della chat nel thread della GUI
            }

            // Se il ciclo termina, significa che il server è stato chiuso
            JOptionPane.showMessageDialog(null, "Server chiuso", "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
            socket.close(); // Chiude il socket

        } catch (IOException e) {
            // Messaggio di errore in caso di problemi di connessione
            JOptionPane.showMessageDialog(null, "Errore di connessione", "Errore", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
