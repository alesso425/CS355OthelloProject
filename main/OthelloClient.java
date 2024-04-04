package main;

public class OthelloClient {
	public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // Server address and port
            System.out.println("Connected to game server. ");
			//Game Setup.
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String servResponse = in.readLine();
            System.out.println("[SERVER] : " + servResponse);
			
			
			


			
            // Sends a request to save game state
            
			
            // out.println("SAVE_GAME");

            // Receive acknowledgment from the server
         
			
			
			
			

            //socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
