package client;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class Client {
	
	private final String host = "127.0.0.1";
    private final int port = 4040;
    Socket socket;
    Scanner in;
    PrintWriter out;
    
	public Client() throws IOException{
		socket = new Socket(host, port);
		in = new Scanner(socket.getInputStream());
		out = new PrintWriter(socket.getOutputStream(), true);
	}
	
	public String checkout(Double input, Integer[] order, ArrayList<String> foodlist) throws IOException
	{
		out.println("order");
		out.println(input);
		String massage = in.nextLine();
		
		for(int i=0;i<order.length;i++)
		{
			out.println(foodlist.get(i));
			out.println(order[i]);
		}

		return massage;
	}
	
	public String signup(String username, String pw)
	{
		out.println("signup");
		out.println(username);
		out.println(pw);
		String massage = in.nextLine();

		return massage;
		
	}
	
	public String login(String username, String pw)
	{
		out.println("login");
		out.println(username);
		out.println(pw);
		String massage = in.nextLine();
		
		return massage;
	}
	
    public static void main(String[] args) throws IOException {
        final String HOST = "127.0.0.1";
        final int PORT = 4040;
        
        System.out.println("Client started.");
        
        try (
            Socket socket = new Socket(HOST, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner in = new Scanner(socket.getInputStream());
            Scanner s = new Scanner(System.in);
        ) {
            while (true) {
                System.out.print("Input: ");
                String input = s.nextLine();
                out.println(input);
                if (input.equalsIgnoreCase("exit")) break;
                System.out.println("Echoed from server: " + in.nextLine());
            }
        }
    }
 
}