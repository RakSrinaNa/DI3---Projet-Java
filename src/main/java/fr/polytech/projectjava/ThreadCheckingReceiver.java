package fr.polytech.projectjava;

import fr.polytech.projectjava.company.CheckInOut;
import javafx.util.Pair;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Thread used to receive the data from the checking machine.
 * <p>
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 28/03/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-03-28
 */
public class ThreadCheckingReceiver extends Thread
{
	private final Consumer<Pair<Integer, CheckInOut>> onNewChecking;
	private final int PORT_NUMBER = 9842;
	
	/**
	 * Constructor.
	 *
	 * @param onNewChecking The action to do when a new check arrived.
	 */
	public ThreadCheckingReceiver(Consumer<Pair<Integer, CheckInOut>> onNewChecking)
	{
		this.onNewChecking = onNewChecking;
		setName("ThreadCheckingReceiver");
		setDaemon(true);
	}
	
	@Override
	public void run()
	{
		while(!isInterrupted())
		{
			try
			{
				ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
				
				try(Socket clientSocket = serverSocket.accept())
				{
					onNewChecking.accept(readClient(clientSocket.getInputStream()));
				}
				catch(IOException exception)
				{
					exception.printStackTrace();
				}
			}
			catch(IOException exception)
			{
				exception.printStackTrace();
			}
		}
	}
	
	/**
	 * Used to read the stream of data received.
	 *
	 * @param inputStream The stream of data.
	 *
	 * @return The read string.
	 */
	private Pair<Integer, CheckInOut> readClient(InputStream inputStream) throws IOException
	{
		StringBuilder stringRead = new StringBuilder();
		int read;
		while((read = inputStream.read()) != -1)
			stringRead.append((char) read);
		
		return readMessage();
	}
	
	private Pair<Integer, CheckInOut> readMessage()
	{
		int employeeID = 0;
		CheckInOut check = new CheckInOut(CheckInOut.CheckType.IN);
		
		return new Pair<>(employeeID, check);
	}
}
