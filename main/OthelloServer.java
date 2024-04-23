package main;

//import Java's input/output and Java's network functions
import java.io.*;
import java.net.*;

/**
 * OthelloServer functions as a server that host a game of Othello. The server functions out of port
 * #121345. Whenever a client socket connects to the port, the server launches a Thread that runs an
 * instance of an Othello object. The Server acts as the computer player and the client is a human
 * user.
 */
public class OthelloServer {
	public static void main(String[] args) {
        try {
			Othello[] clientGames;
            ServerSocket serverSocket = new ServerSocket(12345); // Port number
            System.out.println("Game server is running...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Handle client requests in separate threads
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/**
 * The ClientHandler class functions as a direct inheritance of the Java Thread class, running an
 * instance of Othello, playing as a computer player. The thread handles each new client as they
 * connect to the Server. The thread features a save function, where the client can save their game
 * as a binary file that can be called if they want to reconnect to the server at another time. The
 * Othello object will be stored into a Hashtable with the key being a simple String value and the
 * value being the object. The Hashtable would then be serialized to a binary file under a name;
 * that being a concatenation of the specified username and password.
 */
class ClientHandler extends Thread {
    //declare a Socket object to assign a connected client to
    private final Socket clientSocket;

    //constructor for a client socket to be found and assigned to the object
    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    /**
     * Overridden method run() that tries to connect to a client when the input stream receives a
     * port call. Ask the connected client if they want a new game or want to call a previous one.
     *
     */
    @Override
    public void run() {
        try {
			
			//Prompt if player is new, If so, ask for new name and pass. If not, ask for saved name and pass. 

            //
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
          
            System.out.println("[SERVER] >>> Are you new to the game?    Yes or No? ");
			String clientResp = null;
			while (String.valueOf(in.readLine()) != null)
			{
				if (clientResp.equals("yes"))
				{
					System.out.println("Client said yes."); //For Troubleshooting. Will comment after Othello
					//Create a new game (new Othello Object) prompt player for  name and personal pass.
<<<<<<< Updated upstream
=======
                    out.write("[SERVER] >>> Enter desired username.  ");
                    userName = in.readLine();
                    out.write("[SERVER] >>> Enter desired password.  ");
                    userPass = in.readLine();
                    out.write("[SERVER] >>> GENERATING GAME... ");
                    Othello newgame = new Othello(userName, userPass);


>>>>>>> Stashed changes
				}
				else 
				{
					//Prompt player for existing username and password to resume game.
						//If input name and password is invalid, throw an exception to handle.
				
				
				}
			}

			
			
           //Save Game logic

		   
			
            // Read from/write to clientSocket's input/output streams
			

            // Send acknowledgment to the client

           // out.println("ACK: Game state saved successfully!");

            clientSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
