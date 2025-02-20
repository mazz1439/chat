package com.example.Client;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class ClientMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Socket clientSocket = new Socket("localhost", 5500);
                ChatGUI gui = new ChatGUI(clientSocket);
                gui.createAndShowGUI();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Impossibile connettersi al server", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}