package foodserver;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import db.DB;

public class FoodServer {
  public static void main(String[] args) throws IOException {
        final int PORT = 4040;
        ServerSocket serverSocket = new ServerSocket(PORT);
        
        System.out.println("Server started...");
        System.out.println("Wating for clients...");
        System.out.println("----------------");
        
        while (true) {
            Socket clientSocket = serverSocket.accept();
            Thread t = new Thread() {
            	String currentuser = "";
                public void run() {
                    try (
                        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                        Scanner in = new Scanner(clientSocket.getInputStream());
                    ) {
                        while (in.hasNextLine()) {
                            String input = in.nextLine();
                            if (input.equalsIgnoreCase("exit")) {
                                break;
                            }
                            
                            if(input.equals("signup"))
                            {                            
	                            DB db = new DB();
	                            String username = in.nextLine();
	                            String pw = in.nextLine();
	                            if(db.userExists(username))
	                            {
	                            	out.println("no");
	                            	System.out.println("User exists");
	                            }
	                            else
	                            {
	                            	out.println("yes");
	                            	db.addUser(username, pw);
	                            	System.out.println("User added");
	                            }
                            }
                            else if(input.equals("login"))
                            {
                            	DB db = new DB();
	                            String username = in.nextLine();
	                            String pw = in.nextLine();
	                            if(db.userExists(username))
	                            {
	                            	if(db.checkpw(username,pw))
		                            {
		                            	out.println("yes");
		                            	System.out.println("Login successful");
		                            	currentuser = username;
		                            }
	                            	else
	                            	{
	                            		out.println("no");
	                            		System.out.println("User wrong password");
	                            	}
	                            }
	                            else
	                            {
	                            	out.println("nouser");
                            		System.out.println("User doesn't exist");
	                            }
                            }
                            else if(input.equals("order"))
                            {	
                            	input = in.nextLine();
	                            out.println("Received: " + input);
	                            
	                            System.out.println("Received Order from " + currentuser +": ");
	                            for(int i=0;i<6;i++)
	                            {
	                            	String foodname = in.nextLine();
	                            	String number = in.nextLine();
	                            	System.out.println(foodname + ": " + number);
	                            }
	                            
	                            System.out.println("Total Price: " + input);
	                            System.out.println("----------------");
                            }
                            
                        }
                    } catch (IOException e) { }
                }
            };
            t.start();
        }
    }   
}
