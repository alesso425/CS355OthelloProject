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
            boolean condition = true;
            String serverResp ="";
            String clientResp;
            while(condition) {
                System.out.println(in.readLine());
                clientResp = clientScan.nextLine();
                out.write(clientResp);
                out.newLine();
                out.flush();

                if (clientResp.equalsIgnoreCase("yes")) {
                    System.out.println(in.readLine());
                    clientResp = clientScan.nextLine();
                    out.write(clientResp);
                    out.newLine();
                    out.flush();
                    System.out.println(in.readLine());
                    clientResp = clientScan.nextLine();
                    out.write(clientResp);
                    out.newLine();
                    out.flush();
                    System.out.println(in.readLine());
                    condition = false;
                } else if (clientResp.equalsIgnoreCase("no")) {
                    System.out.println(in.readLine());
                    clientResp = clientScan.nextLine();
                    out.write(clientResp);
                    out.newLine();
                    out.flush();
                    System.out.println(in.readLine());
                    clientResp = clientScan.nextLine();
                    out.write(clientResp);
                    out.newLine();
                    out.flush();
                    System.out.println(in.readLine());
                    condition = false;
                } else {
                    System.out.println(in.readLine());
                }
            }

            while(true)
            {
                for(int i = 0; i < 11; i++){
                    System.out.println(in.readLine());
                }
                serverResp = in.readLine();
                if(!serverResp.equals("[SERVER] >>> No moves. Your turn is skipped.")) {
                    System.out.println(serverResp);
                    boolean control = true;
                    while (control) {
                        System.out.println(in.readLine());
                        clientResp = clientScan.nextLine();
                        out.write(clientResp);
                        out.newLine();
                        out.flush();
                        serverResp = in.readLine();
                        if (serverResp.equals("Done")) {
                            control = false;
                        } else {
                            System.out.println(serverResp);
                        }

                    }
                    control = true;
                    while (control) {
                        System.out.println(in.readLine());
                        clientResp = clientScan.nextLine();
                        out.write(clientResp);
                        out.newLine();
                        out.flush();
                        serverResp = in.readLine();
                        if (serverResp.equals("Done")) {
                            control = false;
                        } else {
                            System.out.println(serverResp);
                        }

                    }
                    System.out.println("[YOUR MOVE] ");
                    for (int i = 0; i < 11; i++) {
                        System.out.println(in.readLine());
                    }
                }else{
                    System.out.println(serverResp);
                }

                serverResp = in.readLine();
                System.out.println(serverResp);
                if(!serverResp.equals("[SERVER] >>> No CPU moves. Their turn is skipped.")) {
                    System.out.println("[CPU MOVE]");
                    for (int i = 0; i < 11; i++) {
                        System.out.println(in.readLine());
                    }
                }else{
                    System.out.println(serverResp);
                }
                serverResp = in.readLine();
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
                    out.newLine();
                    out.flush();
                    if(clientResp.equalsIgnoreCase("yes"))
                    {
                        System.out.println(in.readLine());
                        break;

                    }
                }
            }

            clientScan.close();
            out.close();
            in.close();
        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }
}
