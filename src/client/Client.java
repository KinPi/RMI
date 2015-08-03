package client;
import remoteInterface.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;


public class Client {
	public static Scanner input = new Scanner(System.in);

	public static void main (String [] args)
	{
		try {
			Registry registry = LocateRegistry.getRegistry("localhost", Constant.portNumber);
			RemoteInterface myServer = (RemoteInterface) registry.lookup(Constant.RMIName);
			runClientCode(myServer);
		}//try
		catch (RemoteException e)
		{
			System.out.println("RemoteException!");
			e.printStackTrace();
		}//catch
		catch (NotBoundException e)
		{
			System.out.println("NotBoundException!");
			e.printStackTrace();
		}//catch
	}//main
	
	public static void runClientCode (RemoteInterface myServer) throws RemoteException
	{
		String username;
		String password;
		boolean isAdmin;
		int option;
		
		System.out.println("Welcome! Please enter your username and password!");
		System.out.print("Username: ");
		username = input.next();
		System.out.print("Password: ");
		password = input.next();
		
		if (!myServer.authenticateLogin(username, password))
		{	
			System.out.println("\nLogin unsuccessful! Your credentials are invalid.\n");
			return;	
		}//if
			
		isAdmin = myServer.isAdmin(username);
		
		String welcome = "\nWelcome ";
		welcome += (isAdmin) ? "Administrator!" : "User!";
		System.out.println(welcome + "\n");
		
		do {
			displayMenu(isAdmin);
			option = input.nextInt();
			if (option == 1)
				changePassword(myServer, username);
			else if (option == 2 && isAdmin)
				createUser(myServer);
		} while (option != 0);	
		
		System.out.println("\nYou have exited. Rerun the client to log back in.");
	}//runClientCode
	
	public static void displayMenu (boolean isAdmin)
	{
		System.out.println("(1) Change Password");
		if (isAdmin)
			System.out.println("(2) Create New User Account");
		System.out.println("(0) Exit");
		System.out.print("--> ");
	}//displayMenu
	
	public static void changePassword (RemoteInterface myServer, String username) throws RemoteException
	{
		String newPassword;
		System.out.print("\nEnter your new password: ");
		newPassword = input.next();
		if  (myServer.changePassword(username, newPassword))
			System.out.println("Password successfully changed.\n");
		else
			System.out.println("An error has occured. Your password has not been changed.\n");
	}//changePassword
	
	public static void createUser(RemoteInterface myServer) throws RemoteException
	{
		System.out.print("\nEnter the desired username: ");
		String username = input.next();
		System.out.print("Enter the desired password: ");
		String password = input.next();
		if (myServer.createUser(username, password))
			System.out.println("New user " + username + " created.\n");
		else
			System.out.println("Unable to create new user. Username has already been taken.\n");
	}//createUser
	
	
}//class
