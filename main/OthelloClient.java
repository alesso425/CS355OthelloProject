package main;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class OthelloClient {
	public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // Server address and port
            System.out.println("Connected to game server. ");
			//Game Setup.
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            while (String.valueOf(in.readLine()) != null)
            {

            }

            
			
            // Sends a request to save game state
            
			
            // out.println("SAVE_GAME");

            // Receive acknowledgment from the server

        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //socket.close();
    }
}
