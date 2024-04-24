package main;

//import Java input/output and network functions
import java.io.*;
import java.net.*;
import java.util.*;

public class OthelloClient {
	public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 9999); // Server address and port
            System.out.println("Connected to game server. ");
			//Game Setup.
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            Scanner clientScan = new Scanner(System.in);
            System.out.println(in.readLine());
            String clientResp = clientScan.nextLine();
            out.write(clientResp);
            out.flush();

            if (clientResp.equalsIgnoreCase("yes"))
            {
                System.out.println(in.readLine());
                clientResp = clientScan.nextLine();
                out.write(clientResp);
                out.flush();
                System.out.println(in.readLine());
                clientResp = clientScan.nextLine();
                out.write(clientResp);
                out.flush();
                System.out.println(in.readLine());
            }
            else
            {
                System.out.println(in.readLine());
                clientResp = clientScan.nextLine();
                out.write(clientResp);
                out.flush();
                System.out.println(in.readLine());
                clientResp = clientScan.nextLine();
                out.write(clientResp);
                out.flush();
                System.out.println(in.readLine());
            }
            System.out.println(in.readLine());
            while(true)
            {
                System.out.println(in.readLine());
                clientResp = clientScan.nextLine();
                out.write(clientResp);
                out.flush();
                System.out.println(in.readLine());
                clientResp = clientScan.nextLine();
                out.write(clientResp);
                out.flush();
                System.out.println("[ITS YOUR MOVE] ");
                System.out.println(in.readLine());
                System.out.println("[WAITING FOR CPU MOVE.]");
                System.out.println(in.readLine());
                String serverResp = in.readLine();
                if (!serverResp.equalsIgnoreCase("[SERVER] >>> Do you want to save this board and quit? "))
                {
                    System.out.println(serverResp);
                    break;


                }
                else
                {
                    System.out.println(serverResp);
                    clientResp = clientScan.nextLine();
                    out.write(clientResp);
                    out.flush();
                    if(clientResp.equalsIgnoreCase("yes"))
                    {
                        System.out.println(in.readLine());
                        break;

                    }
                }
            }



        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        //socket.close();
    }
}
