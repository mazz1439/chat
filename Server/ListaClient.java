package com.example.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ListaClient {
    private ArrayList<Socket> listaSockets; // Lista dei socket dei client connessi

    public ListaClient() {
        this.listaSockets = new ArrayList<>(); // Inizializza la lista dei client
    }

    public synchronized void addClient(Socket c) throws IOException {
        // Aggiunge un nuovo client alla lista
        this.listaSockets.add(c);
    }

    public synchronized void removeClient(int i) throws IOException {
        // Chiude la connessione del client e lo rimuove dalla lista
        listaSockets.get(i).close();
        listaSockets.remove(i);
    }

    public synchronized void sendAll(String message, Socket client) throws IOException {
        // Invia un messaggio a tutti i client connessi, tranne il mittente
        for (Socket socket : listaSockets) {
            if (socket != client) { // Esclude il mittente
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                out.println(message);
                out.flush(); // Forza l'invio del messaggio
            }
        }
    }
}
