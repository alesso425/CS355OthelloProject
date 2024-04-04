package main;

public class OthelloServer {
	public static void main(String[] args) {
        try {
			Othello[] clientGames;
            ServerSocket serverSocket = new ServerSocket(12345); // Port number
            System.out.println("Game server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();uikycv
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Handle client requests in separate threads
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
			
			//Prompt if player is new, If so, ask for new name and pass. If not, ask for saved name and pass. 
			
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("[SERVER] >>> Are you new to the game?    Yes or No? ");
			String clientResp; 
			while ((clientResp = in.readLine() != null)
			{
				if (clientResp.equals("yes"))
				{
					System.out.println("Client said yes.") //For Troubleshooting. Will comment after Othello 
					//Create a new game (new Othello Object prompt player for  name and personal pass. 
				}
				else 
				{
					//Prompt player for existing user name and password to resume game.
						//If input name and password is invalid, throw an exception to handle.
				
				
				}
			}
			
			
			
			
			
			
			
			
			
			
           //Save Game logic
		   
		   
		   
		   
		   
			
            // Read from/write to clientSocket's input/output streams
			

            // Send acknowledgment to the client

           // out.println("ACK: Game state saved successfully!");

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
