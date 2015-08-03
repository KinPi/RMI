package remoteInterface;
import java.rmi.Remote;
import java.rmi.RemoteException;


public interface RemoteInterface extends Remote {
	
	public boolean authenticateLogin (String username, String password) throws RemoteException;
	
	public boolean createUser (String username, String password) throws RemoteException;
	
	public boolean changePassword (String username, String password) throws RemoteException;
	
	public boolean isAdmin (String username) throws RemoteException;
	
}
