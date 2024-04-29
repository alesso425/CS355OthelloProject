package main;
//import Java's input/output and Java's network functions
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * OthelloServer functions as a server that host a game of Othello. The server functions out of port
 * #121345. Whenever a client socket connects to the port, the server launches a Thread that runs an
 * instance of an Othello object. The Server acts as the computer player and the client is a human
 * user.
 */
public class OthelloServer {
	public static void main(String[] args){
        try {
			Othello[] clientGames;
            ServerSocket serverSocket = new ServerSocket(9999); // Port number
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
    public void run()
    {
        String userName = "";
        String userPass = "";
        Othello game = null;
        ArrayList <int[]> moveList;
        String[] board;
        try {
			
			//Prompt if player is new, If so, ask for new name and pass. If not, ask for saved name and pass. 

            //
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            boolean condition = true;
            while(condition) {
                out.write("[SERVER] >>> Do you want to generate a new game?    Yes or No? ");
                out.newLine();
                out.flush();
                String clientResp = null;
                clientResp = in.readLine();
                if (clientResp.equalsIgnoreCase("yes")) {
                    System.out.println("Client said yes."); //For Troubleshooting. Will comment after Othello
                    //Create a new game (new Othello Object) prompt player for  name and personal pass.
                    out.write("[SERVER] >>> Enter desired username.  ");
                    out.newLine();
                    out.flush();
                    userName = in.readLine();
                    out.write("[SERVER] >>> Enter desired password.  ");
                    out.newLine();
                    out.flush();
                    userPass = in.readLine();
                    out.write("[SERVER] >>> GENERATING GAME... ");
                    out.newLine();
                    out.flush();
                    game = new Othello(userName, userPass);
                    condition = false;
                }
                //Prompt player for existing username and password to resume game.
                //If input name and password is invalid, throw an exception to handle.
                else if (clientResp.equalsIgnoreCase("no")) {
                    out.write("[SERVER] >>> Enter desired username.  ");
                    out.newLine();
                    out.flush();
                    userName = in.readLine();
                    out.write("[SERVER] >>> Enter desired password.  ");
                    out.newLine();
                    out.flush();
                    userPass = in.readLine();
                    Hashtable<String, Othello> hT = loadGame(userName + userPass);
                    game = hT.get(userName + userPass);
                    out.write("[SERVER] >>> Game retrieved. ");
                    out.newLine();
                    out.flush();
                    condition = false;
                } else {
                    out.write("Invalid input. Please try again.");
                    out.newLine();
                    out.flush();
                }
            }
                int row;
                int col;

                while(true)
                {
                    board = game.printBoard();
                    for(int i = 0; i < board.length; i++){
                        out.write(board[i]);
                        out.newLine();
                        out.flush();
                    }
                    moveList = game.allLegalMoves(true);
                    int[] showyamoves = new int[2];
                    String movesya = "";
                    for(int i = 0; i < moveList.size(); i++)
                    {
                        showyamoves = moveList.get(i);
                        movesya += "["+showyamoves[0]+ "," + showyamoves[1]+"]"+" , ";
                    }

                    out.write("[SERVER] >>> LEGAL MOVES: " + movesya);
                    out.newLine();
                    out.flush();
                    out.write("Please enter row: ");
                    out.newLine();
                    out.flush();
                    row = Integer.parseInt(in.readLine());
                    out.write("Please enter column: ");
                    out.newLine();
                    out.flush();
                    col = Integer.parseInt(in.readLine());
                    game.placePiecePlayer(row, col);
                    board = game.printBoard();
                    for(int i = 0; i < board.length; i++){
                        out.write(board[i]);
                        out.newLine();
                        out.flush();
                    }

                    game.placePieceCPU();
                    board = game.printBoard();
                    for(int i = 0; i < board.length; i++){
                        out.write(board[i]);
                        out.newLine();
                        out.flush();
                        System.out.println(board[i]);
                    }

                    int gameState = game.checkWin();
                    System.out.println(gameState);
                    if (gameState == 1)
                    {
                        out.write("[PLAYER WINS, YIPPEE!, GET REKT BOT. ]");
                        out.newLine();
                        out.flush();
                        break;
                    }
                    else if (gameState == 2)
                    {
                        out.write("[CPU WINS, YOU ARE ACTUALLY BAD, LIKE DON'T EVEN SAVE AGAIN. /exit now. ]");
                        out.newLine();
                        out.flush();
                        break;
                    } else if (gameState == 3)
                    {
                        out.write("[DRAW. EVERYONE LOSES, YA DON'T GOT IT, HOW DOES AI EVEN LOSE, LIKE WHAT????]");
                        out.newLine();
                        out.flush();
                        break;
                    }

                    out.write("[SERVER] >>> Do you want to save this board and quit? ");
                    out.newLine();
                    out.flush();
                    String quitGameQuery = in.readLine();
                    if (quitGameQuery.equalsIgnoreCase("yes"))
                    {
                        Hashtable<String, Othello> hT = new Hashtable<String, Othello>();
                        hT.put(userName + userPass, game);
                        saveGame(hT, userName+userPass);
                        out.write("[SERVER] >>> Game saved. ");
                        out.newLine();
                        out.flush();
                        break;
                    }
                }

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
            hT = (Hashtable<String, Othello>)ois.readObject();

        }

        catch (ClassNotFoundException  | IOException e)
        {
            throw new RuntimeException(e);
        }

        return hT;
    }
}
