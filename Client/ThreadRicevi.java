package com.example.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadRicevi implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private ChatGUI gui;

    public ThreadRicevi(Socket socket, ChatGUI gui) throws IOException {
        this.socket = socket;
        this.gui = gui;
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        String messaggio;
        try {
            while ((messaggio = in.readLine()) != null) {
                gui.appendMessage(messaggio);
            }
            gui.appendMessage("Server chiuso");
            socket.close();
        } catch (IOException e) {
            gui.appendMessage("Errore di connessione");
        }
    }
}