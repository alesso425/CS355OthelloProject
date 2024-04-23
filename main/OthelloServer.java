package main;
import java.util.Hashtable;
import java.io.*;
import java.net.*;

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

class ClientHandler extends Thread {
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {


        String userName;
        String userPass;
        try {
			
			//Prompt if player is new, If so, ask for new name and pass. If not, ask for saved name and pass. 

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String clientResp = null;

            out.write("[SERVER] >>> Do you want to generate a new game?    Yes or No? ");
			while (String.valueOf(in.readLine()) != null)
			{
				if (clientResp.equals("yes"))
				{
					System.out.println("Client said yes."); //For Troubleshooting. Will comment after Othello

					//Create a new game (new Othello Object) prompt player for  name and personal pass.
                    out.write("[SERVER] >>> Enter desired username. ");
                    userName = in.readLine();
                    out.write("[SERVER] >>> Enter desired password.  ");
                    userPass = in.readLine();
                    out.write("[SERVER] >>> GENERATING GAME... ");
                    Othello newgame = new Othello(userName, userPass);

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
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static void saveGame(Hashtable <String, Othello> hT, String fileName)
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName)))
        {
            oos.writeObject(hT);
            System.out.println("[SERVER] >>> Hashtable has been serialized to " + fileName + ".");



        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Hashtable <String, Othello>  loadGame(String fileName)
    {
        Hashtable <String, Othello> hT = null;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName)))
        {
            hT = (Hashtable<String, Othello>) ois.readObject();

        }

         catch (ClassNotFoundException  | IOException e)
        {
            throw new RuntimeException(e);
        }

        return hT;
    }
}
