package serverChat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ListaClient {
	private ArrayList<Socket> listaSockets;
	private ArrayList<PrintWriter> listaPrintWriters;
	
	public ListaClient() {
		listaSockets = new ArrayList<Socket>();
		listaPrintWriters = new ArrayList<PrintWriter>();
	}
	
	public synchronized void addClient(Socket c) throws IOException {
		listaSockets.add(c);
		listaPrintWriters.add( new PrintWriter(c.getOutputStream()));
	}
	
	public synchronized void removeClient(int i) throws IOException {
		listaPrintWriters.get(i).close();
		listaPrintWriters.remove(i);
		listaSockets.get(i).close();
		listaSockets.remove(i);
	}
	
	public synchronized void sendAll(String message) {
		for (PrintWriter printWriter : listaPrintWriters) {
			
			printWriter.println(message);
			printWriter.flush();
		}
		
	}
	
}
