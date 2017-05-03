package fr.polytech.projectjava;

import fr.polytech.projectjava.company.checking.CheckInOut;
import fr.polytech.projectjava.company.staff.Employee;
import fr.polytech.projectjava.jfx.main.MainController;
import fr.polytech.projectjava.utils.Log;
import javafx.util.Pair;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
	private final int PORT_NUMBER = 9842;
	private final MainController controller;
	
	/**
	 * Constructor.
	 *
	 * @param controller The controller of the main application
	 */
	public ThreadCheckingReceiver(MainController controller)
	{
		this.controller = controller;
		setName("ThreadCheckingReceiver");
		setDaemon(true);
	}
	
	@Override
	public void run()
	{
		ServerSocket serverSocket = null;
		try
		{
			serverSocket = new ServerSocket(PORT_NUMBER);
			serverSocket.setSoTimeout(1000);
		}
		catch(IOException e)
		{
			Log.error("Couldn't create socket", e);
		}
		
		while(!isInterrupted())
		{
			
			try(Socket clientSocket = serverSocket.accept())
			{
				sendEmployees(clientSocket.getOutputStream());
				controller.addChecking(readClient(clientSocket.getInputStream()));
			}
			catch(SocketTimeoutException ignored)
			{
			}
			catch(IOException | ParseException exception)
			{
				Log.error("Error reading checking system message", exception);
			}
		}
	}
	
	private void sendEmployees(OutputStream outputStream) throws IOException
	{
		for(Employee employee : controller.listEmployees())
			sendEmployee(outputStream, employee);
		outputStream.close();
	}
	
	private void sendEmployee(OutputStream outputStream, Employee employee) throws IOException
	{
		outputStream.write((byte) employee.getID());
		outputStream.write((byte) '=');
		for(char c : employee.getFirstName().toCharArray())
			outputStream.write((byte) c);
		outputStream.write((byte) ' ');
		for(char c : employee.getLastName().toCharArray())
			outputStream.write((byte) c);
		outputStream.write((byte) ';');
	}
	
	/**
	 * Used to read the stream of data received.
	 *
	 * @param inputStream The stream of data.
	 *
	 * @return The read string.
	 *
	 * @throws IOException    If an error occurred reading the stream.
	 * @throws ParseException If the date could not be parsed.
	 */
	private Pair<Integer, CheckInOut> readClient(InputStream inputStream) throws IOException, ParseException
	{
		StringBuilder stringRead = new StringBuilder();
		int read;
		while((read = inputStream.read()) != -1)
			stringRead.append((char) read);
		
		inputStream.close();
		return readMessage(stringRead.toString());
	}
	
	/**
	 * Convert a message received into checking object.
	 *
	 * @param string The message received.
	 *
	 * @return A pair having as key the employee ID and the checking object as value.
	 *
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
