package com.example.Server;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ThreadConnessione implements Runnable {
    private final Socket client;
    private final BufferedReader in;
    private final ListaClient listaClient;
    private String nomeClient;

    public ThreadConnessione(Socket client, ListaClient listaClient, JTextArea nomeClient) throws IOException {
        this.client = client;
        this.listaClient = listaClient;
        this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        this.nomeClient = String.valueOf(nomeClient);
    }

    @Override
    public void run() {
        String messaggio;
        boolean primo = true; // Flag per identificare il primo messaggio ricevuto

        try {
            while (!Thread.interrupted()) { // Ciclo infinito finché il thread non viene interrotto
                messaggio = in.readLine(); // Legge un messaggio dal client

                if (primo) { // Se è il primo messaggio ricevuto, lo considera come nome del client
                    nomeClient = messaggio;
                    System.out.println("Client " + nomeClient + " connesso");
                    primo = false;
                } else { // Altrimenti, invia il messaggio a tutti gli altri client connessi
                    listaClient.sendAll(nomeClient + ": " + messaggio, client);
                }
            }
        } catch (IOException e) {
            System.out.println("Connessione interrotta con " + nomeClient); // Messaggio di errore in caso di disconnessione
        }
    }
}
