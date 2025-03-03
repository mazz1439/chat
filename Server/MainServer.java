package com.example.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class MainServer {

    public static void main(String[] args) {
        final int PORT = 5500;

        try {
            // Creazione del server socket sulla porta specificata
            ServerSocket serverSocket = new ServerSocket(PORT);

            // Lista per mantenere traccia dei thread di connessione
            ArrayList<Thread> listaThreadConnessioni = new ArrayList<>();

            // Lista per gestire i client connessi
            ListaClient listaClient = new ListaClient();

            System.out.println("Server Aperto");
            System.out.println("In attesa di connessioni...");

            while (true) { // Ciclo infinito per accettare connessioni in arrivo

                // Accetta una nuova connessione dal client
                Socket nuovoClient = serverSocket.accept();

                // Aggiunge il nuovo client alla lista dei client connessi
                listaClient.addClient(nuovoClient);

                // Crea un nuovo thread per gestire la comunicazione con il client
                Thread connessioneThread = new Thread(new ThreadConnessione(nuovoClient, listaClient, null));

                // Aggiunge il thread alla lista e lo avvia
                listaThreadConnessioni.add(connessioneThread);
                listaThreadConnessioni.get(listaThreadConnessioni.size() - 1).start();
            }

        } catch (IOException e) {
            System.out.println("Errore di connessione"); // Messaggio di errore in caso di problemi con il server
        }
    }
}
