package fr.polytech.projectjava;

import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.utils.Log;
import javafx.util.Pair;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
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
				catch(IOException | ParseException exception)
				{
					Log.error("Error reading checking system message", exception);
				}
			}
			catch(IOException exception)
			{
				Log.warning("Error with checking socket", exception);
			}
		}
	}
	
	/**
	 * Used to read the stream of data received.
	 *
	 * @param inputStream The stream of data.
	 *
	 * @return The read string.
	 * @throws IOException If an error occurred reading the stream.
	 * @throws ParseException If the date could not be parsed.
	 */
	private Pair<Integer, CheckInOut> readClient(InputStream inputStream) throws IOException, ParseException
	{
		StringBuilder stringRead = new StringBuilder();
		int read;
		while((read = inputStream.read()) != -1)
			stringRead.append((char) read);
		
		return readMessage(stringRead.toString());
	}
	
	/**
	 * Convert a message received into checking object.
	 *
	 * @param string The message received.
	 *
	 * @return A pair having as key the employee ID and the checking object as value.
	 * @throws ParseException If the date could not be parsed.
	 */
	private Pair<Integer, CheckInOut> readMessage(String string) throws ParseException
	{
		String[] messageParts = string.split(";");
		
		int employeeID = Integer.valueOf(messageParts[0]);
		CheckInOut check = new CheckInOut(CheckInOut.CheckType.valueOf(messageParts[1].toUpperCase()), dateFormat.parse(messageParts[2]));
		
		return new Pair<>(employeeID, check);
	}
}
